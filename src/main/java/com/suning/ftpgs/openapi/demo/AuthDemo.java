package com.suning.ftpgs.openapi.demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;
import com.suning.ftpgs.openapi.config.AuthConfig;
import com.suning.ftpgs.openapi.util.CryptoUtil;
import com.suning.ftpgs.openapi.util.FtpgsUtils;
import org.apache.commons.codec.binary.StringUtils;

import java.io.IOException;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *
 * Date:2022-08-15
 * Time:16:26
 * 类描述: 新版鉴权模式demo(加签+加密)
 * <p>
 * 参数构造及调用步骤：
 * 1.将【业务参数】转成JSON字符串放到【params】字段中，和【开放鉴权参数】一起构造请求参数。
 * 2.将请求参数的【Key】按照【ASCII码】排序,排除不需要参与加签的字段(signType、signkeyIndex)。
 * 3.将排序完的参数按照 【Key1=Value1&Key2=Value2&...】 格式，拼接成明文字符串(value如果是个集合、数组、或对象，转成JSON字符串)。
 * 4.将拼接完的明文字符串进行【MD5】摘要。
 * 5.使用提供的加签工具类，传入【摘要】和【私钥】，进行RSA加签，生成【sign】字符串。
 * 6.将加签后的【sign】放入请求参数中。
 * 7.生成16位AES密钥字符串,对业务参数【params】进行AES加密后，将加密后的字符串替换【params】字段。
 * 8.使用提供的易付宝公钥对AES字符串进行RSA加密，将加密后的字符串放到请求参数【key】中。
 * 9.构造HTTP请求，注意HTTP头部类型【Content-Type:application/json】，发送网络请求得到响应结果。
 * 10.如需对响应结果数据验签，取出响应结果中的【gsSign】字段，进行加签，对比签名是否一致。
 * 11.如果响应结果中包含【encrypt】并且为true,则取出【params】字段,使用请求时的AES密钥进行解密。将解密后的字符串，转成JSON对象，即为返回数据。
 */
public class AuthDemo {

    public static final String KEY_ALGORITHM = "RSA";

    public static void main(String[] args) throws IOException {
        /**
         * 主接口调用顺序：签约申请（可根据业务需求选择签约模式），签约确认，支付，退款
         */


        //签约：银行卡模式
        String msgId = sign();
        System.out.println("签约申请成功，返回的msgId为，签约确认接口中需要回传：" + msgId);

        //  签约：无卡模式
        String signForm = signNoCard();
//        System.out.println("signForm：" + signForm);


        //签约确认
        String contractNo = confirm(msgId);
        System.out.println("签约确认成功，返回的contractNo为，支付接口中需要用到：" + contractNo);

        //有卡签约查询，查询的协议对应的签约模式为：银行卡签约
       querySignAgreements();

        //无卡签约查询，查询的协议对应的签约模式为：无卡模式
//        querySignAgreementsNoCard();

        // 共享协议查询接口
//        queryMerSharaProtocol();

//        支付
//         String contractNo = "202307312257833";
         Map<String, Object> orderMap = pay(contractNo);

//        String contractNo = "202307312257833";
//        aggrementPay(contractNo);

        //查询商户订单
//        queryMerchantOrder(orderMap);

        //退款
        createRefundOrder(orderMap);

        //退款查询
//        queryRefundOrder();

        //解约
//        cancelContract(contractNo);
    }


    /**
     * 退款接口
     */
    private static void createRefundOrder(Map<String, Object> orderMap ) throws IOException{
        // 构造业务请求参数的Map
        Map<String, Object> bizMap = new HashMap<> ();

        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        // 提交时间，yyyyMMddHHmmss
        bizMap.put("submitTime", format.format(new Date()));
        // 异步通知地址，用来接收易付宝的退款结果
        bizMap.put("notifyUrl", "https://testrefund/notify.htm");
        // 业务版本号
        bizMap.put("version", "1.1");
        // 商户网站生成的退款单号，同一天退款单号必须唯一
        bizMap.put("refundOrderNo", UUID.randomUUID().toString().replace("-", ""));
        // 原商户订单号,值同正向下单时的商品订单号
        bizMap.put("origMerchantOrderNo", orderMap.get("outOrderNo"));
        // 原商品订单号,值同正向下单时的商品订单号
        bizMap.put("origOutOrderNo", orderMap.get("outOrderNo"));
        // 原订单创建时间,值同正向下单时的商户下单时间orderTime
        bizMap.put("origOrderTime", orderMap.get("orderTime"));
        // 退款订单创建时间，格式：yyyyMMddHHmmss,该时间在退款查询接口中会用到
        bizMap.put("refundOrderTime", format.format(new Date()));
        // 退款金额，单位为 分,不能超过原正向订单的金额
        bizMap.put("refundAmount", Long.valueOf((String) orderMap.get("orderAmount")));
        /**
         * 可选参数，按商户需求传即可
         */
        // 退款操作人
//        bizMap.put("operator", "");
        // 退款理由
//        bizMap.put("refundReason", "");
        // 备注
//        bizMap.put("remark", "");
        // 隧道字段，1、用来和业务方协商的业务扩展字段(最大容量1024字节)，格式是Json字符串，不会回传商户。 2、该参数需要做Base64(字符集UTF-8)转码
//        bizMap.put("tunnelData", "");
        // 是否保证退款成功,01：不保证退款成功，不传或传其他值都为保证退款成功
//        bizMap.put("guaranteeRefundSucc", "");

        Map<String, Object> requestBodyMap = new HashMap<>();
        requestBodyMap.put("requestBody", JSON.toJSONString(bizMap));

        String url = "https://ftpgspre.cnsuning.com/ftpgs/trade/queryOrder/createRefundOrder";
        //加签与验签
        Map<String, Object> responseMap = signVerify(requestBodyMap, url);
        System.out.println("退款接口响应参数：" + responseMap);
        String responseCode = (String) responseMap.get("responseCode");
        if ("XY_0000".equals(responseCode)) {
            System.out.println("退款受理成功");
            // 商户对httpResponse 其他业务处理
            // ...
        }else if("XY_8888".equals(responseCode)){
            System.out.println("退款受理异常，需要通过退款接口查询结果");
        }else {
            System.out.println("退款受理失败，可重新发起退款");
        }
    }


    /**
     * 查询商户订单
     */
    private static void queryMerchantOrder(Map<String, Object> orderMap ) throws IOException{
        // 构造业务请求参数的Map
        Map<String, Object> bizMap = new HashMap<> ();

        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        //提交时间，yyyyMMddHHmmss
        bizMap.put("submitTime", format.format(new Date()));
        //业务版本号
        bizMap.put("version", "2.2");
        //商户唯一订单号，取下单接口中的outOrderNo
        bizMap.put("outOrderNo", orderMap.get("outOrderNo"));
        //商户下单时间，yyyyMMddHHmmss，取下单接口中的orderTime
        bizMap.put("orderTime", orderMap.get("orderTime"));

        Map<String, Object> requestBodyMap = new HashMap<>();
        requestBodyMap.put("requestBody", JSON.toJSONString(bizMap));

        String url = "https://ftpgspre.cnsuning.com/ftpgs/trade/queryOrder/queryMerchantOrder";
        //加签与验签
        Map<String, Object> responseMap = signVerify(requestBodyMap, url);
        System.out.println("支付接口响应参数：" + responseMap);
        String responseCode = (String) responseMap.get("responseCode");
        String payResult = (String) responseMap.get("payResult");
        if ("XY_0000".equals(responseCode) && "S".equals(payResult)) {
            System.out.println("支付成功");
            // 商户对httpResponse 其他业务处理
            // ...
        }else if("F".equals(payResult) || "C".equals(payResult)){
            System.out.println("支付失败或订单关闭");
        }else {
            System.out.println("支付异常，需要等异步通知，或再次调订单查询接口查询支付结果");
        }
    }

    /**
     * 查询退款订单
     */
    private static void queryRefundOrder() throws IOException{
        // 构造业务请求参数的Map
        Map<String, Object> bizMap = new HashMap<> ();

        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        //提交时间，yyyyMMddHHmmss
        bizMap.put("submitTime", format.format(new Date()));
        // 退款单号,取值同退款接口中的退款单号refundOrderNo
        bizMap.put("refundOrderNo", "87343f26ce504ce3b87940870a915cdf");
        //退款订单创建时间，yyyyMMddHHmmss，取值同退款接口中的refundOrderTime
        bizMap.put("refundOrderTime", "20230728110151");

        Map<String, Object> requestBodyMap = new HashMap<>();
        requestBodyMap.put("requestBody", JSON.toJSONString(bizMap));

        String url = "https://ftpgspre.cnsuning.com/ftpgs/trade/queryOrder/queryRefundOrder";
        //加签与验签
        Map<String, Object> responseMap = signVerify(requestBodyMap, url);
        System.out.println("支付接口响应参数：" + responseMap);
        String responseCode = (String) responseMap.get("responseCode");
        String status = (String) responseMap.get("status");
        if ("XY_0000".equals(responseCode) && "01".equals(status)) {
            System.out.println("退款成功");
            // 商户对httpResponse 其他业务处理
            // ...
        }else if("02".equals(status)){
            System.out.println("退款失败");
        }else {
            System.out.println("退款处理中，需要再次查询退款结果或等待系统异步通知");
        }
    }


    /**
     * 协议支付还款通接口，多笔订单合并支付
     */
    private static Map<String, Object> aggrementPay(String contractNo) throws IOException{
        // 构造业务请求参数的Map
        Map<String, Object> bizMap = new HashMap<> ();
        //商户接入ip
        bizMap.put("clientIp", "10.25.123.110");
        //签约协议号，签约确认接口返回参数
        bizMap.put("contractNo", contractNo);
        //支付结果通知地址，商户接收支付结果的地址，如果不需要，可以不传
        bizMap.put("notifyUrl", "https://test.htm");
        //产品编码
        bizMap.put("productCode", "00010000674");
        //商户批次号
        bizMap.put("merchantOrderNo", UUID.randomUUID().toString().replace("-", ""));
        //系统名称
        bizMap.put("sysName", "demotest");
        //业务版本号
        bizMap.put("version", "2.2");
        //展示商户号,用户银行账单优先展示的商户号
        bizMap.put("showMerchantNo", "70056575");
        //商品类型，业务分配
        bizMap.put("goodsType", "012345");

        List<Map<String,Object>> orderList = new ArrayList<>();

        for (int i=0; i<2; i++){
            Map<String,Object> order = new HashMap<>();
            //卖家商户号
            order.put("salerMerchantNo", "70056575");
            //商户唯一订单号
            order.put("outOrderNo", UUID.randomUUID().toString().replace("-", ""));
            //订单金额，单位：分
            order.put("orderAmount", "1");
            //订单有效期，为空则默认24小时，不为空以传的为准，yyyyMMddHHmmss
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MINUTE, 60);// 例子中取当前时间后1小时
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
            order.put("orderExpireTime", format.format(calendar.getTime()));

            // 订单名称做Base64编码
            byte[] bytes = org.apache.commons.codec.binary.Base64.encodeBase64(StringUtils.getBytesUtf8("协议支付demo测试"));
            order.put("orderName", StringUtils.newStringUtf8(bytes));

            //商户下单时间，yyyyMMddHHmmss
            order.put("orderTime", format.format(new Date()));
            //币种
            order.put("currency", "CNY");
            orderList.add(order);
        }
        bizMap.put("orderList" , orderList);

        Map<String, Object> requestBodyMap = new HashMap<>();
        requestBodyMap.put("requestBody", JSON.toJSONString(bizMap));

        String url = "https://ftpgspre.cnsuning.com/ftpgs/trade/agreementPayGateway/agreementPay";
        //加签与验签
        Map<String, Object> responseMap = signVerify(requestBodyMap, url);
        System.out.println("支付接口响应参数：" + responseMap);
        String responseCode = (String) responseMap.get("responseCode");
        String statusCode = (String) responseMap.get("statusCode");
        if ("XY_0000".equals(responseCode) && "S".equals(statusCode)) {
            System.out.println("支付成功");
            // 商户对httpResponse 其他业务处理
            // ...
        }else if("F".equals(statusCode)){
            System.out.println("支付失败");
        }else {
            System.out.println("支付异常，需要等异步通知，或调订单查询接口查询支付结果");
        }
        return bizMap;
    }


    /**
     * 支付
     */
    private static Map<String, Object> pay(String contractNo) throws IOException{
        // 构造业务请求参数的Map
        Map<String, Object> bizMap = new HashMap<> ();
        //商户接入ip
        bizMap.put("clientIp", "10.25.123.110");
        //签约协议号，签约确认接口返回参数
        bizMap.put("contractNo", contractNo);
        //币种
        bizMap.put("currency", "CNY");
        //商品类型，业务分配
        bizMap.put("goodsType", "012345");
        //支付结果通知地址，商户接收支付结果的地址，如果不需要，可以不传
        bizMap.put("notifyUrl", "https://test.htm");
        //订单金额，单位：分
        bizMap.put("orderAmount", "1");
        //订单有效期，为空则默认24小时，不为空以传的为准，yyyyMMddHHmmss
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 60);// 例子中取当前时间后1小时
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        bizMap.put("orderExpireTime", format.format(calendar.getTime()));

        // 订单名称做Base64编码
        byte[] bytes = org.apache.commons.codec.binary.Base64.encodeBase64(StringUtils.getBytesUtf8("协议支付demo测试"));
        bizMap.put("orderName", StringUtils.newStringUtf8(bytes));

        //商户下单时间，yyyyMMddHHmmss
        bizMap.put("orderTime", format.format(new Date()));
        //商户唯一订单号
        bizMap.put("outOrderNo", UUID.randomUUID().toString().replace("-", ""));
        //产品编码
        bizMap.put("productCode", "00010000674");
        //请求号
        bizMap.put("requestNo", UUID.randomUUID().toString().replace("-", ""));
        //系统名称
        bizMap.put("sysName", "demotest");
        //业务版本号
        bizMap.put("version", "2.2");

        /**
         * 可选参数，根据业务场景，跟对接人确认
         */
        //卖家商户号，不传的话，默认取系统接入方商户号作为卖家商户号
//        bizMap.put("salerMerchantNo", "");
        // 商品名称
//        bizMap.put("goodsName", "");

        Map<String, Object> requestBodyMap = new HashMap<>();
        requestBodyMap.put("requestBody", JSON.toJSONString(bizMap));

        String url = "https://ftpgspre.cnsuning.com/ftpgs/trade/agreementPayGateway/pay";
        //加签与验签
        Map<String, Object> responseMap = signVerify(requestBodyMap, url);
        System.out.println("支付接口响应参数：" + responseMap);
        String responseCode = (String) responseMap.get("responseCode");
        String statusCode = (String) responseMap.get("statusCode");
        if ("XY_0000".equals(responseCode) && "S".equals(statusCode)) {
            System.out.println("支付成功");
            // 商户对httpResponse 其他业务处理
            // ...
        }else if("F".equals(statusCode)){
            System.out.println("支付失败");
        }else {
            System.out.println("支付异常，需要等异步通知，或调订单查询接口查询支付结果");
        }
        return bizMap;
    }

    /**
     * 解约
     * @throws IOException
     */
    private static void cancelContract(String contractNo) throws IOException {
        // 构造业务请求参数的Map
        Map<String, Object> bizMap = new HashMap<> ();
        //产品编码
        bizMap.put("productCode", "00010000674");
        //签约协议号
        bizMap.put("contractNo", contractNo);

        Map<String, Object> requestBodyMap = new HashMap<>();
        requestBodyMap.put("requestBody", JSON.toJSONString(bizMap));

        String url = "https://ftpgspre.cnsuning.com/ftpgs/trade/agreementPayGateway/cancelContract";
        //加签与验签
        Map<String, Object> responseMap = signVerify(requestBodyMap, url);
        String responseCode = (String) responseMap.get("responseCode");
        if ("XY_0000".equals(responseCode)) {
            System.out.println("解约成功");
        }
    }

    /**
     * 共享协议查询
     * @throws IOException
     */
    private static void queryMerSharaProtocol() throws IOException {
        String encryptCardInfo = "";
        try{
            String publicKey ="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA9tFLvKROtlkSmIv/5iXRLFgDYKhWtn06hF1jZNc7ZNtsPibL8Sf2g8I8eH5Z2qBpoWQVhlyBYl9I/dUsAVgrfcluwVMOvLkpP/xMxm0MJ2HWOB+MxQmOeDCthGi/2WOit91nsdAyBOCB4Zgi8h9FGF/NE65/WvmTGoOgNodsWyL54v6HZ0///n50kIWdfljeAXyoBSwRB0GBNGA1yMSxvzH06BmlpWWj6vi3OhoVvk2Opp6ks946udVG8Tn8qLcyRbTUvZk5pmFzf8eKvf1XKJ72/vhLY+Vun8iuIhn9+tFIsoLSxhwttrXLeG0CENW+bHZ80qEHNgsklARo3n+yiwIDAQAB";
            // 对密钥解密
//            byte[] keyBytes = Base64.decode(publicKey);
            // 取得公钥
            byte[] keyBytes = Base64.getDecoder().decode(publicKey);
            X509EncodedKeySpec pkcs8KeySpec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            PublicKey pubKey = keyFactory.generatePublic(pkcs8KeySpec);
            String  plainXML = "{\"cardHolderName\":\"周测试一\",\"certType\":\"01\",\"certNo\":\"110101198001110011\",\"mobileNo\":\"13512514514\",\"cardNo\":\"9000000000000000555\"}";
            byte[] encryptedData = CryptoUtil.encrypt(plainXML.getBytes("UTF-8"), pubKey, 2048, 11, "RSA");// 加密
            encryptCardInfo = CryptoUtil.bcdhexToAschex(encryptedData);
        }catch (Exception e){
            System.out.println("加密异常：" + e);
        }
        // 构造业务请求参数的Map
        Map<String, Object> bizMap = new HashMap<> ();
        //加密卡信息
        bizMap.put("encryptCardInfo", encryptCardInfo);
        //请求号
        bizMap.put("requestNo", UUID.randomUUID().toString().replace("-", ""));
        //调用方系统名称
        bizMap.put("sysName", "demotest");
        //API版本号
        bizMap.put("version", "2.0");

        Map<String, Object> requestBodyMap = new HashMap<>();
        requestBodyMap.put("requestBody", JSON.toJSONString(bizMap));

        //pre测试环境https://ftpgspre.cnsuning.com/ftpgs/trade/agreementPayGateway/queryMerSharaProtocol
        String url = "https://ftpgspre.cnsuning.com/ftpgs/trade/agreementPayGateway/queryMerSharaProtocol";
        //加签与验签
        Map<String, Object> responseMap = signVerify(requestBodyMap, url);
        String responseCode = (String) responseMap.get("responseCode");
        String contractNo = (String) responseMap.get("contractNo");
        if ("XY_0000".equals(responseCode)) {
            System.out.println("查询共享协议成功，协议号为：" + contractNo);
        }
    }


    /**
     * 签约查询-无卡模式
     * @throws IOException
     */
    private static void querySignAgreementsNoCard() throws IOException {
        // 构造业务请求参数的Map
        Map<String, Object> bizMap = new HashMap<> ();
        //用户商户会员ID，取签约接口(sign)中的merchantUserNo值
        bizMap.put("msgId", "2307310000020585");
        //请求号
        bizMap.put("sysName", "demotest");
        //请求流水号
        bizMap.put("requestNo", UUID.randomUUID().toString().replace("-", ""));

        Map<String, Object> requestBodyMap = new HashMap<>();
        requestBodyMap.put("requestBody", JSON.toJSONString(bizMap));

        String url = "https://ftpgspre.cnsuning.com/ftpgs/trade/agreementPayGateway/querySignStatus";
        //加签与验签
        Map<String, Object> responseMap = signVerify(requestBodyMap, url);
        String responseCode = (String) responseMap.get("responseCode");
        String data = (String) responseMap.get("data");
        if("0000".equals(responseCode) && data != null && data.length() > 0){
            Map<String, String> dataMap = JSON.parseObject(data, Map.class);
            String contractNo = dataMap.get("contractNo");
            System.out.println("查询协议号为：" + contractNo);
        }

        // 商户对httpResponse 其他业务处理
        // ...
    }

    /**
     * 签约查询-有卡模式
     * @throws IOException
     */
    private static void querySignAgreements() throws IOException {
        // 构造业务请求参数的Map
        Map<String, Object> bizMap = new HashMap<> ();
        //用户商户会员ID，取签约接口(sign)中的merchantUserNo值
        bizMap.put("merchantUserNo", "demotest202308");
        //商品类型
        bizMap.put("goodsType", "012345");
        //请求流水号
        bizMap.put("serialNo", UUID.randomUUID().toString().replace("-", ""));

        //pre测试环境https://ftpgspre.cnsuning.com/ftpgs/trade/agreementPayGateway/querySignAgreements
        String url = "https://ftpgspre.cnsuning.com/ftpgs/trade/agreementPayGateway/querySignAgreements";
        //加签与验签
        signVerify(bizMap, url);
        // 商户对httpResponse 其他业务处理
        // ...
    }

    /**
     * 签约确认
     */
    private static String confirm(String msgId) throws IOException{
        // 构造业务请求参数的Map
        Map<String, Object> bizMap = new HashMap<> ();
        //短信序列号
        bizMap.put("msgId", msgId);
        //产品编码
        bizMap.put("productCode", "00010000674");
        //请求号
        bizMap.put("requestNo", UUID.randomUUID().toString().replace("-", ""));
        //系统名称
        bizMap.put("sysName", "demotest");
        //短信验证码，测试环境不真实发短信，可以填任意的6位数字
        bizMap.put("verificationCode", "123456");
        //业务版本号
        bizMap.put("version", "2.2");
        //商户接入ip
        bizMap.put("clientIp", "10.25.123.110");

        Map<String, Object> requestBodyMap = new HashMap<>();
        requestBodyMap.put("requestBody", JSON.toJSONString(bizMap));

        /**
         * 可选参数，根据业务场景，需要跟对接人确认
         */
        //签约方商户
//        bizMap.put("salerMerchantNo", "");

        //pre测试环境https://ftpgspre.cnsuning.com/ftpgs/trade/agreementPayGateway/confirm
        String url = "https://ftpgspre.cnsuning.com/ftpgs/trade/agreementPayGateway/confirm";
        //加签与验签
        Map<String, Object> responseMap = signVerify(requestBodyMap, url);
        System.out.println("签约确认响应参数：" + responseMap);
        String responseCode = (String) responseMap.get("responseCode");
        String contractNo = "";
        if ("XY_0000".equals(responseCode)) {
            JSONObject data = (JSONObject) responseMap.get("data");
            contractNo = data.getString("contractNo");
            System.out.println("签约确认成功，返回协议号：" + contractNo);
            // 商户对httpResponse 其他业务处理
            // ...
        }
        return contractNo;
    }


    /**
     * 签约：非银行卡模式
     */
    private static String signNoCard() throws IOException{

        String encryptCardInfo = "";
        try{
            //易付宝公钥
            String publicKey ="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA9tFLvKROtlkSmIv/5iXRLFgDYKhWtn06hF1jZNc7ZNtsPibL8Sf2g8I8eH5Z2qBpoWQVhlyBYl9I/dUsAVgrfcluwVMOvLkpP/xMxm0MJ2HWOB+MxQmOeDCthGi/2WOit91nsdAyBOCB4Zgi8h9FGF/NE65/WvmTGoOgNodsWyL54v6HZ0///n50kIWdfljeAXyoBSwRB0GBNGA1yMSxvzH06BmlpWWj6vi3OhoVvk2Opp6ks946udVG8Tn8qLcyRbTUvZk5pmFzf8eKvf1XKJ72/vhLY+Vun8iuIhn9+tFIsoLSxhwttrXLeG0CENW+bHZ80qEHNgsklARo3n+yiwIDAQAB";

            // 取得公钥
            X509EncodedKeySpec pkcs8KeySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKey));
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            PublicKey pubKey = keyFactory.generatePublic(pkcs8KeySpec);
            String  plainXML = "{\"cardHolderName\":\"吴杨\",\"certType\":\"01\",\"certNo\":\"320111198603062158\",\"bankCode\":\"CMB\" ,\"cardType\":\"DEBIT\"}";
            byte[] encryptedData = CryptoUtil.encrypt(plainXML.getBytes("UTF-8"), pubKey, 2048, 11, "RSA");// 加密
            System.out.println("加密串:" + CryptoUtil.bcdhexToAschex(encryptedData));
            encryptCardInfo = CryptoUtil.bcdhexToAschex(encryptedData);
        }catch (Exception e){
            System.out.println("加密异常：" + e);
        }
        // 构造业务请求参数的Map
        Map<String, Object> bizMap = new HashMap<> ();
        //加密卡信息
        bizMap.put("encryptCardInfo", encryptCardInfo);
        //用户商户会员ID
        bizMap.put("merchantUserNo", "demotest2023072801");
        //客户端请求IP
        bizMap.put("clientIp", "10.25.123.100");
        //商品类型
        bizMap.put("goodsType", "012345");
        //调用方系统名称
        bizMap.put("sysName", "demotest");
        //产品编码
        bizMap.put("productCode", "00010000674");
        //API版本号
        bizMap.put("version", "2.3");
        //请求号
        bizMap.put("requestNo", UUID.randomUUID().toString().replace("-", ""));
        // 签约模式，
        bizMap.put("signTactics", "02");
        // 接收签约结果的服务端地址, 无银行卡模式需传入
        bizMap.put("notifyUrl", "http://test/notify.htm");
        // 签约完成的跳转页面, 无银行卡模式需传入
        bizMap.put("returnUrl", "http://test/return.htm");

        Map<String, Object> requestBodyMap = new HashMap<>();
        requestBodyMap.put("requestBody", JSON.toJSONString(bizMap));

        //pre测试环境 https://ftpgspre.cnsuning.com/ftpgs/trade/agreementPayGateway/sign
        String url = "https://ftpgspre.cnsuning.com/ftpgs/trade/agreementPayGateway/sign";
        //加签与验签
        Map<String, Object> responseMap = signVerify(requestBodyMap, url);
        System.out.println("签约申请返回报文：" + responseMap);
        String responseCode = (String) responseMap.get("responseCode");
        StringBuffer buffer = new StringBuffer();
        if ("XY_0000".equals(responseCode)){
            JSONObject data = (JSONObject) responseMap.get("data");

            String msgId = data.getString("msgId");
            System.out.println("无卡签约模式短信流水ID，用来查询签约结果："  + msgId);

            String entrustSignUrl = data.getString("entrustSignUrl");
            String name = data.getString("secFieldName");
            String value = data.getString("secFieldValue");
            // entrustSignUrl，name，value拼装form表单，提交form表单，打开银行签约页面即可
            /**
             * <form action="https://opentesting31.nucc.com:14443/serviceNode/ftl/mock/bankSignGW" method="post">

             <input type="text" name="epccGwMsg" value="PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiPz48cm9vdCB4bWxucz0ibmFtZXNwYWNlX3N0cmluZyI+PE1zZ0hlYWRlcj48U25kRHQ+MjAyMy0wNy0yOFQxNTo1ODoxMjwvU25kRHQ+PE1zZ1RwPmVwY2MuMTA1LjAwMS4wMTwvTXNnVHA+PElzc3JJZD5HNDAwMDMxMTAwMDAxODwvSXNzcklkPjxEcmN0bj4yMTwvRHJjdG4+PFNpZ25TTj40MDMyMDY5MTM1PC9TaWduU04+PE5jcnB0blNOPjQwMDc1NTc5OTg8L05jcnB0blNOPjxEZ3RsRW52bHA+QnRvUm9pTnhWb3VQT21ubXdjUG0rZ0t4bHpCa2Y3YU5qZFh0YXhPZFF5eS9hdGFRdjZUY04vWk1vU2dsK0oxd2tCOElmL040Vi94b0U5NnZYMkNKT0Y2SitSWitWamhKNEtLbzlGV25KaExrckY2b1BUWWZvb0R6c3BCVkVDeFNwcmdvV0hod2FNWkNaWVh0YW1iVFVyNkxGOTNnaXFUN2xJWGhlWlpjOXZzdzFCRGVNRzg5czVkNW9xLzBZc081UUdjdmZxM1dsQ1NUWWk0Q2l6QUhKdmNDbTIrT2t3OUFzZGp4cUx4ekRiK2IvKzdDUXpyL0lOVWpLUWZMU2RDcENublJxSXZRc0hCck5CVEJwRDZUVWlhK0FSUEVPZUp6TDZpK0FLdDBlQXM3S0RTa0lLWkVzaVZReUdkOC85MmZNSnFhSjc1d2ErRXMvMFF1MVI4QnZRPT08L0RndGxFbnZscD48L01zZ0hlYWRlcj48TXNnQm9keT48U3lzUnRuSW5mPjxTeXNSdG5DZD4wMDAwMDAwMDwvU3lzUnRuQ2Q+PFN5c1J0bkRlc2M+5oiQ5YqfPC9TeXNSdG5EZXNjPjxTeXNSdG5UbT4yMDIzLTA3LTI4VDE1OjU4OjEyPC9TeXNSdG5UbT48L1N5c1J0bkluZj48Qml6SW5mPjxUcnhDdGd5PjAyMDA8L1RyeEN0Z3k+PFRyeElkPjIwMjMwNzI4NDM0ODgwMDAwODMxMTA3MzE3NzAxMDM8L1RyeElkPjxCaXpTdHNDZD4wMDAwMDAwMDwvQml6U3RzQ2Q+PEJpelN0c0Rlc2M+5oiQ5YqfPC9CaXpTdHNEZXNjPjxSZHJjdFVybD5odHRwczovL29wZW50ZXN0aW5nMzEubnVjYy5jb206MTQ0NDMvc2VydmljZU5vZGUvZnRsL21vY2svYmFua1NpZ25HVzwvUmRyY3RVcmw+PC9CaXpJbmY+PFNlYz5MdzJFNHdjU2dLT2FhY2VvaFZldHlWZTNzU09FdFZwN0kvWHY3WXJxd3dTTFlVVjdxWkErSUV1WkhHZzlrSVRtTzBBbkJLenZIUEZKMWNNWUYvV0R5UFg1SXQwQStVS2tzeHBYOUtkMzhoUXR0K2ZEYlpOd2F0SHNla0dCdEI3VEdlQzB1bDBvZW9iT0dBdzFKZUxXUm5aa21BbmNFOFppN3owTVo3WmhBalVwWnRkby90SVIxMm9oUTQ1SWZ3S0lxclNQNityanhHVVhrT2o0MWF6Skx2N0pRNVFoQU01bUF1dTRCTmVzd2VjbnJBRDBzYnJEeTF5K28xWmZQZ2F6VkNBWHdTa3hncEx5S3VYR0pmTjg5bGtyWmpwK3hnZVcrSHAyclRxMFBwblJPMWlyK2RMNjIyRGJSc1EwZGxtOVJJNllVREt0NUlSMmFOblNJa0k5OTkxQW9VOUZEY0o5Z2g0dnVqak9lOU4rNTd6MDgzUGJPRThUK3lYU2tBMzl2anhVRStTQk5xNGpvS2tVL3JXays5WGVlQndoRElNWklWOHpSUWF6U3g5YWRYa3ZKcSsxcHRUUmk0Wmt4cUUwVkN2T2xKRzVCb2ZyYjAyZEErT0VtRE9zcEVqbWhVU1l4OSt0WkFPZDVxMTBGemNaVmp6WnRiUDRicHR0anlUYzFhd0dmcmg5WUJ2OVFjb0F6T20vMDVzbHA5cjJDOUJkQ3Q0S21yUlhwNll4N1pUWUsvZEwvRW1VS2ErM1ZIZTlrVHpRSDhxUHdkc3ZvZ1c0dDZCSm1RPT08L1NlYz48L01zZ0JvZHk+PC9yb290Pg0Ke1M6T3JudXNST1ZqOExuRTFoRmQrVFJVYUFHTXhiblQxdDJGWmEwazIyTGFvZzFhdU1kbWdTWHZ0eXBhT2R2QzdoQmM4aU4wVm5nMWh6L0NiVU1JL2g1MTE4MlJUUloyWTBFNWhBSS9SRWpoYmNJajFmN0E5YWlDSU1qSEo4SVlBTVBLeHFwQk9ySjltejlTTFVFMnhMQXM3bmVCYmJSVGRzSXBvcU8xNURpenBKMnpFK29JVDRyN1BOb3p4R1JrbDg1ZE1WbmxDSjE0NUMvVHo1YWZvaE54K0lLZDVJczhmdnZRMzRZaHlqNm42TWwvQTdRQlJuUE5Kam0xQWYwS0cwZm5EZFJqNUxvUDFaaVVVa01ueVJaSjRWb3EwaUtpNXdxb3dzTGJZdGk0bU1ubmhhcFZTQys3YkdKTTYxcXFKZnZKSUNXdlRlb2V0eDRiejcxN3BnYmx3PT19" />
             <input type="submit" name="提交" />

             </form>
             **/
            buffer.append("<form action=\"").append(entrustSignUrl).append("\"").append(" method=\"post\">")
                    .append("<input type=\"text\" name=\"").append(name).append("\"").append(" value=").append("\"").append(value).append("\" />")
                    .append("<input type=\"submit\" name=\"提交\" />");
        }
        return buffer.toString();
    }

    /**
     * 签约：银行卡模式
     */
    private static String sign() throws IOException{

        String encryptCardInfo = "";
        try{
            String publicKey ="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA9tFLvKROtlkSmIv/5iXRLFgDYKhWtn06hF1jZNc7ZNtsPibL8Sf2g8I8eH5Z2qBpoWQVhlyBYl9I/dUsAVgrfcluwVMOvLkpP/xMxm0MJ2HWOB+MxQmOeDCthGi/2WOit91nsdAyBOCB4Zgi8h9FGF/NE65/WvmTGoOgNodsWyL54v6HZ0///n50kIWdfljeAXyoBSwRB0GBNGA1yMSxvzH06BmlpWWj6vi3OhoVvk2Opp6ks946udVG8Tn8qLcyRbTUvZk5pmFzf8eKvf1XKJ72/vhLY+Vun8iuIhn9+tFIsoLSxhwttrXLeG0CENW+bHZ80qEHNgsklARo3n+yiwIDAQAB";
            // 对密钥解密
            byte[] keyBytes = Base64.getDecoder().decode(publicKey);
            // 取得公钥
            X509EncodedKeySpec pkcs8KeySpec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            PublicKey pubKey = keyFactory.generatePublic(pkcs8KeySpec);
            String  plainXML = "{\"cardHolderName\":\"吴杨\",\"certType\":\"01\",\"certNo\":\"320111198603062158\",\"mobileNo\":\"15250900353\",\"cardNo\":\"6200400013702778456\"}";
            byte[] encryptedData = CryptoUtil.encrypt(plainXML.getBytes("UTF-8"), pubKey, 2048, 11, "RSA");// 加密
            System.out.println("加密串:" + CryptoUtil.bcdhexToAschex(encryptedData));
            encryptCardInfo = CryptoUtil.bcdhexToAschex(encryptedData);
        }catch (Exception e){
            System.out.println("加密异常：" + e);
        }
        // 构造业务请求参数的Map
        Map<String, Object> bizMap = new HashMap<> ();
        //加密卡信息
        bizMap.put("encryptCardInfo", encryptCardInfo);
        //用户商户会员ID
        bizMap.put("merchantUserNo", "demotest202308");
        //客户端请求IP
        bizMap.put("clientIp", "10.25.123.100");
        //商品类型
        bizMap.put("goodsType", "012345");
        //调用方系统名称
        bizMap.put("sysName", "demotest");
        //产品编码
        bizMap.put("productCode", "00010000674");
        //API版本号
        bizMap.put("version", "2.2");
        //请求号
        bizMap.put("requestNo", UUID.randomUUID().toString().replace("-", ""));

        /**
         * 可选参数，根据业务需求，需要与对接人确认
         */
//        bizMap.put("salerMerchantNo", ""); //签约方商户号,不走商委通道无需传值

        Map<String, Object> requestBodyMap = new HashMap<>();
        requestBodyMap.put("requestBody", JSON.toJSONString(bizMap));

       //pre测试环境 https://ftpgspre.cnsuning.com/ftpgs/trade/agreementPayGateway/sign
        String url = "https://ftpgspre.cnsuning.com/ftpgs/trade/agreementPayGateway/sign";
        //加签与验签
        Map<String, Object> responseMap = signVerify(requestBodyMap, url);
        System.out.println("签约申请返回报文：" + responseMap);
        String responseCode = (String) responseMap.get("responseCode");
        String msgId = "";
        if ("XY_0000".equals(responseCode)){
            JSONObject data = (JSONObject) responseMap.get("data");
            msgId = data.getString("msgId");
            // 商户对httpResponse 其他业务处理
        }
        return msgId;
    }

    /**
     * 加签与验签
     */
    private static Map<String, Object> signVerify(Map<String, Object> bizMap, String url) throws IOException{
        // 构造鉴权参数对象，设置对应的配置信息。pre测试环境
        FtpgsUtils.AuthParam authParam = AuthConfig.getAuthConfig (bizMap, url);
        //1. 获取鉴权参数的JSON格式字符串
        String authParamString = FtpgsUtils.makeDefaultAuthParam(authParam);
        System.out.println ("请求报文：" + JSON.toJSONString (authParamString));
        //2.发送HTTP网络请求，得到响应结果(此处可自行替换成自己的HTTP工具类，如httpClinet)
        String httpResponse = FtpgsUtils.post (authParam.getUrl (), authParamString, 5000, 5000);
        System.out.println ("http响应结果为：" + httpResponse);
        //3.验签返回结果签名是否一致(如果不需要对返回结果验签，可跳过此步骤)
        boolean verifySign = FtpgsUtils.verifySign (httpResponse, authParam);
        System.out.println ("验证结果为：" + verifySign);
        //4.取出返回结果中的params 字段，即为业务返回结果
        Map<String, Object> resultMap = JSONObject.parseObject (httpResponse, new TypeReference<Map<String, Object>> () {
        }, Feature.OrderedField);
        return resultMap;
    }
}
