package com.rent.service.components.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rent.common.constant.SuningOpenAPIConstant;
import com.rent.common.dto.components.response.AliPayTradePayResponse;
import com.rent.common.enums.components.EnumAliPayStatus;
import com.rent.common.enums.components.EnumPayType;
import com.rent.common.enums.components.EnumTradeResult;
import com.rent.common.enums.components.EnumTradeType;
import com.rent.common.properties.SuningProperties;
import com.rent.common.util.GsonUtil;
import com.rent.common.util.StringUtil;
import com.rent.config.outside.OutsideConfig;
import com.rent.dao.components.AlipayTradeCreateDao;
import com.rent.dao.components.YfbTradePayDao;
import com.rent.dao.components.YfbTradeRefundDao;
import com.rent.dao.components.YfbTradeSerialDao;
import com.rent.dao.order.UserOrdersDao;
import com.rent.dao.user.UserBankCardDao;
import com.rent.exception.HzsxBizException;
import com.rent.model.components.YfbTradePay;
import com.rent.model.components.YfbTradeRefund;
import com.rent.model.components.YfbTradeSerial;
import com.rent.model.order.OrderByStages;
import com.rent.model.order.UserOrders;
import com.rent.model.product.Product;
import com.rent.model.user.UserBankCard;
import com.rent.service.components.SuningOpenApiService;
import com.rent.service.product.ProductService;
import com.suning.ftpgs.openapi.util.CryptoUtil;
import com.suning.ftpgs.openapi.util.DateFormat;
import com.suning.ftpgs.openapi.util.FtpgsUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.StringUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class SuningOpenApiServiceImpl implements SuningOpenApiService {

    private final SuningProperties suningProperties;
    private final UserOrdersDao userOrdersDao;
    private final ProductService productService;
    private final UserBankCardDao userBankCardDao;

    private final YfbTradeSerialDao yfbTradeSerialDao;
    private final YfbTradePayDao yfbTradePayDao;
    private final YfbTradeRefundDao yfbTradeRefundDao;
    private final AlipayTradeCreateDao alipayTradeCreateDao;


    @Override
    public AliPayTradePayResponse payWithYfb(String orderId, String subject,
                                             BigDecimal totalAmount, List<String> periodList, EnumTradeType tradeType) throws IOException {

        String randomNo = String.valueOf((int) ((Math.random() * 9 + 1) * 1000));
        String outTradeNo = "STP" + orderId + "_" + String.join("", periodList);
        String tradeNo = outTradeNo + "_" + randomNo;

        UserOrders userOrders = userOrdersDao.selectOneByOrderId(orderId);
        Product product = productService.getByProductId(userOrders.getProductId());
        Date now = new Date();

        YfbTradeSerial tradeSerial = new YfbTradeSerial();
        tradeSerial.setOrderId(orderId);
        tradeSerial.setPeriod(GsonUtil.objectToJsonString(periodList));
        tradeSerial.setUid(userOrders.getUid());
        tradeSerial.setOutOrderNo(outTradeNo);
        tradeSerial.setAmount(totalAmount);
        tradeSerial.setPayType(EnumPayType.YFB_PAY);
        tradeSerial.setTradeType(EnumTradeType.BILL_WITHHOLD);
        tradeSerial.setStatus(EnumAliPayStatus.PAYING);
        tradeSerial.setChannelId(userOrders.getChannelId());
        tradeSerial.setCreateTime(now);
        tradeSerial.setUpdateTime(now);
        //插入流水
        YfbTradePay yfbTradePay = new YfbTradePay();
        yfbTradePay.setOrderId(orderId);
        yfbTradePay.setPeriod(GsonUtil.objectToJsonString(periodList));
//        alipayTradePay.setAuthCode(authCode);
        yfbTradePay.setOutTradeNo(outTradeNo);
        yfbTradePay.setSubject(subject);
        yfbTradePay.setAmount(totalAmount);
        yfbTradePay.setStatus(EnumAliPayStatus.PAYING);
        yfbTradePay.setCreateTime(now);
        yfbTradePay.setUpdateTime(now);
        yfbTradePay.setRequest(null);


        List<UserBankCard> userBankCards = userBankCardDao.getUserBankCardByUid(userOrders.getUid());
        if (userBankCards.size() == 0) {
            throw new HzsxBizException("-1", "用户未签约易付宝代扣");
        }
        yfbTradePay.setTradeNo(tradeNo);
        tradeSerial.setSerialNo(tradeNo);

        Map<String, Object> responseMap = null;
        EnumTradeResult tradeResult = EnumTradeResult.FAILED;
        for (UserBankCard card : userBankCards) {
            yfbTradePay.setAuthCode(card.getCardNo()); // 签约合同号

            String amount = String.valueOf(totalAmount.multiply(new BigDecimal(100)).toBigIntegerExact());
            String orderTime = new SimpleDateFormat("yyyyMMddHHmmss").format(now);
            String goodsName = product.getName();

            Map<String, Object> bizMap = new HashMap<>();// 构造业务请求参数的Map
            bizMap.put("clientIp", "10.25.123.110");//商户接入ip
            bizMap.put("contractNo", card.getContractNo());//签约协议号，签约确认接口返回参数
            bizMap.put("currency", "CNY");//币种
            bizMap.put("goodsType", "012345");//商品类型，业务分配
            bizMap.put("notifyUrl", OutsideConfig.DOMAIN + "/zyj-component/callBack/yfbNotify/stageOrderCallback"); //支付结果通知地址，商户接收支付结果的地址，如果不需要，可以不传
            bizMap.put("orderAmount", amount);//订单金额，单位：分
            Calendar calendar = Calendar.getInstance();//订单有效期，为空则默认24小时，不为空以传的为准，yyyyMMddHHmmss
            calendar.add(Calendar.MINUTE, 60);// 例子中取当前时间后1小时
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
            bizMap.put("orderExpireTime", format.format(calendar.getTime()));

            // 订单名称做Base64编码
            bizMap.put("orderName", Base64.getEncoder().encodeToString(StringUtils.getBytesUtf8(subject)));

            bizMap.put("orderTime", orderTime);//商户下单时间，yyyyMMddHHmmss
            bizMap.put("outOrderNo", tradeNo);////商户唯一订单号

            bizMap.put("productCode", "00010000674");//产品编码
            bizMap.put("requestNo", UUID.randomUUID().toString().replace("-", ""));//请求号
            bizMap.put("sysName", OutsideConfig.APP_CODE + "_API");//系统名称
            bizMap.put("version", "2.2");//业务版本号

            bizMap.put("salerMerchantNo", suningProperties.getSalerMerchantNo());//卖家商户号，不传的话，默认取系统接入方商户号作为卖家商户号
            bizMap.put("goodsName", Base64.getEncoder().encodeToString(StringUtils.getBytesUtf8(goodsName)));// 商品名称
            Map<String, Object> requestBodyMap = new HashMap<>();
            requestBodyMap.put("requestBody", JSON.toJSONString(bizMap));
            yfbTradePay.setRequest(JSON.toJSONString(bizMap)); // 吧请求参数存储到数据库
            String url = SuningOpenAPIConstant.getUrlByEnvName(suningProperties.getEnvName(), "SIGN");
            //加签与验签
            responseMap = this.signVerify(requestBodyMap, url);
            System.out.println("支付接口响应参数：" + responseMap);
            String responseCode = (String) responseMap.get("responseCode");
            String statusCode = (String) responseMap.get("statusCode");
            if ("XY_0000".equals(responseCode) && "S".equals(statusCode)) {
                System.out.println("YFB支付成功");
                yfbTradePay.setStatus(EnumAliPayStatus.PAYING);
                tradeSerial.setStatus(EnumAliPayStatus.PAYING);
                tradeResult = EnumTradeResult.SUCCESS;
                break;
            } else if ("F".equals(statusCode)) {
                System.out.println("支付失败");
                yfbTradePay.setStatus(EnumAliPayStatus.FAILED);
                tradeSerial.setStatus(EnumAliPayStatus.FAILED);
                tradeResult = EnumTradeResult.FAILED;
            } else {
                System.out.println("支付异常，需要等异步通知，或调订单查询接口查询支付结果");
                yfbTradePay.setStatus(EnumAliPayStatus.PAYING);
                tradeSerial.setStatus(EnumAliPayStatus.PAYING);
                tradeResult = EnumTradeResult.LIMIT_AMOUNT;
            }
        }
        yfbTradePayDao.saveOrUpdate(yfbTradePay, new QueryWrapper<>(YfbTradePay.builder()
                .outTradeNo(outTradeNo)
                .status(EnumAliPayStatus.PAYING)
                .build()));
        if (StringUtil.isNotEmpty(tradeNo)) {
            yfbTradeSerialDao.saveOrUpdate(tradeSerial, new QueryWrapper<>(YfbTradeSerial.builder()
                    .serialNo(tradeNo)
                    .status(EnumAliPayStatus.PAYING)
                    .build()));
        } else {
            yfbTradeSerialDao.save(tradeSerial);
        }
        return AliPayTradePayResponse.builder()
                .resultMessage((String) responseMap.get("responseCode"))
                .tradeResult(tradeResult)
                .tradeNo(yfbTradePay.getTradeNo())
                .outTradeNo(yfbTradePay.getOutTradeNo())
                .build();
    }

    @Override
    public String confirm(String msgId, String code, String uid) throws IOException {
        Map<String, Object> bizMap = new HashMap<>();
        //短信序列号
        bizMap.put("msgId", msgId);
        //产品编码
        bizMap.put("productCode", "00010000674");
        //请求号
        bizMap.put("requestNo", UUID.randomUUID().toString().replace("-", ""));
        //系统名称
        bizMap.put("sysName", OutsideConfig.APP_CODE + "_API");
        //短信验证码，测试环境不真实发短信，可以填任意的6位数字
        bizMap.put("verificationCode", code);
        //业务版本号
        bizMap.put("version", "2.2");
        //商户接入ip
        bizMap.put("clientIp", "10.25.123.110");

        Map<String, Object> requestBodyMap = new HashMap<>();
        requestBodyMap.put("requestBody", JSON.toJSONString(bizMap));

        /**
         * 可选参数，根据业务需求，需要与对接人确认
         */
        bizMap.put("salerMerchantNo", suningProperties.getSalerMerchantNo()); //签约方商户号,不走商委通道无需传值

        String url = SuningOpenAPIConstant.getUrlByEnvName(suningProperties.getEnvName(), "CONFIRM");
        //加签与验签
        Map<String, Object> responseMap = this.signVerify(requestBodyMap, url);
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

    @Override
    public void yfbTradeRefund(OrderByStages orderByStages, UserOrders userOrders, String remark,
                               BigDecimal amount, EnumTradeType tradeType, String uid) {
        YfbTradeSerial yfbTradeSerial = yfbTradeSerialDao.selectOneBySerialNo(orderByStages.getOutTradeNo());
        String orderTime = new SimpleDateFormat("yyyyMMddHHmmss").format(yfbTradeSerial.getCreateTime());
        String orderAmount = String.valueOf(yfbTradeSerial.getAmount().multiply(new BigDecimal(100)).toBigIntegerExact());
        String outOrderNo = orderByStages.getOutTradeNo();
        YfbTradeRefund yfbTradeRefund = new YfbTradeRefund();
        yfbTradeRefund.setOrderId(userOrders.getOrderId());
        yfbTradeRefund.setOutTradeNo(orderByStages.getOutTradeNo());

        String tradeNo = UUID.randomUUID().toString().replace("-", "");
        yfbTradeRefund.setTradeNo(tradeNo);
        yfbTradeRefund.setRefundAmount(yfbTradeSerial.getAmount());
        yfbTradeRefund.setRefundReason(remark);


        // 构造业务请求参数的Map
        Map<String, Object> bizMap = new HashMap<>();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        bizMap.put("submitTime", format.format(new Date()));// 提交时间，yyyyMMddHHmmss
        bizMap.put("notifyUrl", OutsideConfig.DOMAIN + "/zyj-component/callBack/yfbNotify/yfbTradeRefundCallback"); //支付结果通知地址，商户接收支付结果的地址，如果不需要，可以不传
        bizMap.put("version", "1.1");// 业务版本号
        bizMap.put("refundOrderNo", tradeNo);// 商户网站生成的退款单号，同一天退款单号必须唯一
        bizMap.put("origMerchantOrderNo", outOrderNo);// 原商户订单号,值同正向下单时的商品订单号
        bizMap.put("origOutOrderNo", outOrderNo);// 原商品订单号,值同正向下单时的商品订单号
        bizMap.put("origOrderTime", orderTime);// 原订单创建时间,值同正向下单时的商户下单时间orderTime
        bizMap.put("refundOrderTime", format.format(new Date()));// 退款订单创建时间，格式：yyyyMMddHHmmss,该时间在退款查询接口中会用到
        bizMap.put("refundAmount", Long.valueOf(orderAmount));// 退款金额，单位为 分,不能超过原正向订单的金额
        /**
         * 可选参数，按商户需求传即可
         */
        // 退款操作人
//        bizMap.put("operator", "");
        // 退款理由
        bizMap.put("refundReason", remark);
        // 备注
//        bizMap.put("remark", "");
        // 隧道字段，1、用来和业务方协商的业务扩展字段(最大容量1024字节)，格式是Json字符串，不会回传商户。 2、该参数需要做Base64(字符集UTF-8)转码
//        bizMap.put("tunnelData", "");
        // 是否保证退款成功,01：不保证退款成功，不传或传其他值都为保证退款成功
//        bizMap.put("guaranteeRefundSucc", "");
        yfbTradeRefund.setRequest(JSON.toJSONString(bizMap));
        Map<String, Object> requestBodyMap = new HashMap<>();
        requestBodyMap.put("requestBody", JSON.toJSONString(bizMap));

        try {
            String url = SuningOpenAPIConstant.getUrlByEnvName(suningProperties.getEnvName(), "REFUND");
            //加签与验签
            Map<String, Object> responseMap = this.signVerify(requestBodyMap, url);
            log.info("【易付宝-代扣退款】退款接口响应参数: {}", JSON.toJSONString(responseMap));
            String responseCode = (String) responseMap.get("responseCode");
            if ("XY_0000".equals(responseCode)) {
                yfbTradeRefund.setStatus(EnumAliPayStatus.PAYING);
                log.error("【易付宝-代扣退款】退款受理成功");
            } else if ("XY_8888".equals(responseCode)) {
                yfbTradeRefund.setStatus(EnumAliPayStatus.PAYING);
                log.error("【易付宝-代扣退款】退款受理异常，需要通过退款接口查询结果");
            } else {
                yfbTradeRefund.setStatus(EnumAliPayStatus.FAILED);
                log.error("【易付宝-代扣退款】退款受理失败，可重新发起退款,outTradeNo={}", tradeNo);
            }
        } catch (IOException e) {
            log.error("【易付宝-代扣退款】- 开始处理,outTradeNo={}", tradeNo);
            e.printStackTrace();
        } finally {
            yfbTradeRefundDao.save(yfbTradeRefund);
        }
    }

    @Override
    public String pay(String contractNo, String amount,
                      String orderName, String orderTime,
                      String outOrderNo, String goodsName
    ) throws IOException {
        // 构造业务请求参数的Map
        Map<String, Object> bizMap = new HashMap<>();
        //商户接入ip
        bizMap.put("clientIp", "10.25.123.110");
        //签约协议号，签约确认接口返回参数
        bizMap.put("contractNo", contractNo);
        //币种
        bizMap.put("currency", "CNY");
        //商品类型，业务分配
        bizMap.put("goodsType", "012345");
        //支付结果通知地址，商户接收支付结果的地址，如果不需要，可以不传
        bizMap.put("notifyUrl", OutsideConfig.DOMAIN + "/zyj-backstage-web/hzsx/ope/order/suningStageOrderWithholdCallback");
        //订单金额，单位：分
        bizMap.put("orderAmount", amount);
        //订单有效期，为空则默认24小时，不为空以传的为准，yyyyMMddHHmmss
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 60);// 例子中取当前时间后1小时
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        bizMap.put("orderExpireTime", format.format(calendar.getTime()));

        // 订单名称做Base64编码
        byte[] bytes = Base64.getEncoder().encode(StringUtils.getBytesUtf8(orderName));
        bizMap.put("orderName", StringUtils.newStringUtf8(bytes));

        //商户下单时间，yyyyMMddHHmmss
        bizMap.put("orderTime", orderTime);
        //商户唯一订单号
        bizMap.put("outOrderNo", outOrderNo);
        //产品编码
        bizMap.put("productCode", "00010000674");
        //请求号
        bizMap.put("requestNo", UUID.randomUUID().toString().replace("-", ""));
        //系统名称
        bizMap.put("sysName", OutsideConfig.APP_CODE + "_API");
        //业务版本号
        bizMap.put("version", "2.2");

        /**
         * 可选参数，根据业务场景，跟对接人确认
         */
        //卖家商户号，不传的话，默认取系统接入方商户号作为卖家商户号
        bizMap.put("salerMerchantNo", suningProperties.getSalerMerchantNo());
        // 商品名称
        bizMap.put("goodsName", Base64.getEncoder().encodeToString(StringUtils.getBytesUtf8(goodsName)));

        Map<String, Object> requestBodyMap = new HashMap<>();
        requestBodyMap.put("requestBody", JSON.toJSONString(bizMap));
        String url = SuningOpenAPIConstant.getUrlByEnvName(suningProperties.getEnvName(), "PAY");
        //加签与验签
        Map<String, Object> responseMap = signVerify(requestBodyMap, url);
        System.out.println("支付接口响应参数：" + responseMap);
        String responseCode = (String) responseMap.get("responseCode");
        String statusCode = (String) responseMap.get("statusCode");
        if ("XY_0000".equals(responseCode) && "S".equals(statusCode)) {
            System.out.println("支付成功");
            return "代扣成功";
        } else if ("F".equals(statusCode)) {
            System.out.println("支付失败");
        } else {
            System.out.println("支付异常，需要等异步通知，或调订单查询接口查询支付结果");
        }
        return (String) responseMap.get("responseMsg");
    }

    /**
     * 退款
     *
     * @param orderMap
     * @return
     */
    @Override
    public String createRefundOrder(Map<String, Object> orderMap) {
        return "";
    }

    /**
     * 查询订单
     *
     * @param orderMap
     * @return
     * @throws IOException
     */
    @Override
    public String queryMerchantOrder(Map<String, Object> orderMap) throws IOException {
        return "";
    }

    @Override
    public String sign(Map<String, String> params, String uid) throws IOException {
        String encryptCardInfo = "";
        try {
            // 对密钥解密
            byte[] keyBytes = Base64.getDecoder().decode(suningProperties.getEppPublicSignKey());
            // 取得公钥
            X509EncodedKeySpec pkcs8KeySpec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey pubKey = keyFactory.generatePublic(pkcs8KeySpec);
            String plainXML = JSONObject.toJSONString(params);
            byte[] encryptedData = CryptoUtil.encrypt(plainXML.getBytes("UTF-8"), pubKey, 2048, 11, "RSA");// 加密
            System.out.println("加密串:" + CryptoUtil.bcdhexToAschex(encryptedData));
            encryptCardInfo = CryptoUtil.bcdhexToAschex(encryptedData);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("加密异常：" + e);
        }
        // 构造业务请求参数的Map
        Map<String, Object> bizMap = new HashMap<>();
        //加密卡信息
        bizMap.put("encryptCardInfo", encryptCardInfo);
        //用户商户会员ID
        bizMap.put("merchantUserNo", uid);
        //客户端请求IP
        bizMap.put("clientIp", "10.25.123.100");
        //商品类型
        bizMap.put("goodsType", suningProperties.getGoodsType());
        //调用方系统名称
        bizMap.put("sysName", OutsideConfig.APP_CODE + "_API");
        //产品编码
        bizMap.put("productCode", "00010000674");
        //API版本号
        bizMap.put("version", "2.2");
        //请求号
        bizMap.put("requestNo", UUID.randomUUID().toString().replace("-", ""));

        /**
         * 可选参数，根据业务需求，需要与对接人确认
         */
        bizMap.put("salerMerchantNo", suningProperties.getSalerMerchantNo()); //签约方商户号,不走商委通道无需传值

        Map<String, Object> requestBodyMap = new HashMap<>();
        requestBodyMap.put("requestBody", JSON.toJSONString(bizMap));
        String url = SuningOpenAPIConstant.getUrlByEnvName(suningProperties.getEnvName(), "SIGN");
        //加签与验签
        Map<String, Object> responseMap = this.signVerify(requestBodyMap, url);
        System.out.println("签约申请返回报文：" + responseMap);
        String responseCode = (String) responseMap.get("responseCode");
        String msgId = "";
        if ("XY_0000".equals(responseCode)) {
            JSONObject data = (JSONObject) responseMap.get("data");
            msgId = data.getString("msgId");
            // 商户对httpResponse 其他业务处理
        }
        return msgId;
    }

    public FtpgsUtils.AuthParam getAuthConfig(Map<String, Object> bizMap, String url) {
        FtpgsUtils.AuthParam authParam = new FtpgsUtils.AuthParam();
        authParam
/*           .setAppId ("【商户应用标识，苏宁金融分配给商户的appId】")
                .setPrivateKey ("【商户自行生成公私钥对后,保留的私钥】")
                .setPublicKey ("【苏宁金融网关平台demo中提供的易付宝公钥】")
                .setVersion ("【默认:1.0】")
                .setSignKeyIndex ("【绑定商户公钥的索引号,苏宁金融提供】")
                .setSignType ("【签名方式，默认:RSA2】")
                .setTimestamp ("【时间戳，格式：yyyyMMddHHmmss，例如：20190805093412，默认当前时间】")
                .setUrl ("【苏宁金融提供的HTTP接口地址】")*/

                .setAppId(suningProperties.getAppId())
                .setPrivateKey(suningProperties.getMerchantPrivateKey())
                .setPublicKey(suningProperties.getEppPublicSignKey())
                .setVersion("1.0")
                .setSignKeyIndex(suningProperties.getSignKeyIndex())
                .setSignType("RSA2")
                .setTimestamp(DateFormat.formatFromDate(new Date()))
                .setUrl(url)
                .setParams(bizMap);//设置业务参数Map
        return authParam;
    }

    @Override
    public Map<String, Object> signVerify(Map<String, Object> bizMap, String url) throws IOException {
        // 构造鉴权参数对象，设置对应的配置信息。pre测试环境
        FtpgsUtils.AuthParam authParam = this.getAuthConfig(bizMap, url);
        //1. 获取鉴权参数的JSON格式字符串
        String authParamString = FtpgsUtils.makeDefaultAuthParam(authParam);
        System.out.println("请求报文：" + JSON.toJSONString(authParamString));
        //2.发送HTTP网络请求，得到响应结果(此处可自行替换成自己的HTTP工具类，如httpClinet)
        String httpResponse = FtpgsUtils.post(authParam.getUrl(), authParamString, 5000, 5000);
        System.out.println("http响应结果为：" + httpResponse);
        //3.验签返回结果签名是否一致(如果不需要对返回结果验签，可跳过此步骤)
        boolean verifySign = FtpgsUtils.verifySign(httpResponse, authParam);
        System.out.println("验证结果为：" + verifySign);
        //4.取出返回结果中的params 字段，即为业务返回结果
        Map<String, Object> resultMap = JSONObject.parseObject(httpResponse, new TypeReference<Map<String, Object>>() {
        }, Feature.OrderedField);
        return resultMap;
    }
}
