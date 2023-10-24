package com.suning.ftpgs.openapi.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;
import com.suning.epps.merchantsignature.common.Digest;
import com.suning.epps.merchantsignature.common.RSAUtil;
import org.apache.commons.net.util.Base64;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author 张磊
 * Date:2019-08-02
 * Time:11:22
 * 类描述: 网关开放加签工具类
 */
public class FtpgsUtils {

    private static final String EQ = "=";
    private static final String AND = "&";
    private static final String WHY = "?";
    private static final String PATTERN = "yyyyMMddHHmmss";
    private static final String APP_ID = "appId";
    private static final String VERSION = "version";
    private static final String TIME_STAMP = "timestamp";
    private static final String SIGN_TYPE = "signType";
    private static final String RSA2 = "RSA2";
    private static final String SIGN_KEY_INDEX = "signkeyIndex";
    private static final String SIGN = "sign";
    private static final String GS_SIGN = "gsSign";
    public static final String PARAMS = "params";
    private static final String ENCRYPT = "encrypt";
    private static final String KEY = "key";
    private static final String MERCHANT_USER_NO = "merchantUserNo";
    private static final String GATEWAY_PATH = "gatewayPath";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String APPLICATION_JSON_UTF_8 = "application/json;charset=UTF-8";
    private static final String UTF8_CHARSET = "UTF-8";
    private static final int INDEX_NOT_FOUND = -1;

    private FtpgsUtils() {
    }

    /**
     * 构造开放鉴权方式
     *
     * @param authParam 请求参数对象
     * @param isNew     是否是新版鉴权模式
     * @param isH5      是否是H5鉴权模式
     * @param encrypt   是否需要加密
     * @return 返回构造好的鉴权参数JSON字符串
     */
    private static String makeAuthParam(AuthParam authParam, boolean isNew, boolean isRestFul, boolean isH5, boolean encrypt) {
        Map<String, Object> params = getParamsMap(authParam, isNew, isRestFul, isH5);
        getSign(authParam, params);
        if (encrypt) {
            encrypt(authParam, params);
        }
        if (isH5) {
            return authParam.getUrl() + WHY + appendUrl(params);
        } else {
            return JSON.toJSONString(params);
        }
    }

    /**
     * 使用AES密钥 对params进行加密
     * 对AES密钥进行RSA加密
     */
    private static void encrypt(AuthParam authParam, Map<String, Object> params) {
        try {
            String key = authParam.getKey();
            String str1 = getString(params, PARAMS);
            String str2 = EncryptUtil.encryptBase64DecorateAES(str1, key);
            params.put(PARAMS, str2);
            String encrypt = EncryptUtil.encryptByPublicKey(key, authParam.getPublicKey());
            params.put(KEY, encrypt);
        } catch (Exception e) {
            throw new FtpgsException("AES加密失败", e);
        }
    }

    /**
     * 取出params加密字符串
     * 使用AES密钥 对params进行解密
     */
    public static Map<String, Object> decrypt(AuthParam authParam, Map<String, Object> params) {
        String str = getString(params, PARAMS);
        try {
            if (params.containsKey(ENCRYPT) && (Boolean) params.get(ENCRYPT)) {
                String key = authParam.getKey();
                str = EncryptUtil.decryptBase64DecorateAES(str, key);
            }
            return JSON.parseObject(str, new TypeReference<Map<String, Object>>() {
            });
        } catch (Exception e) {
            throw new FtpgsException("AES解密失败", e);
        }
    }


    /**
     * 默认开放鉴权方式,业务参数和鉴权参数平级
     *
     * @param authParam 请求参数对象
     * @return 返回构造好的鉴权参数JSON字符串
     */
    public static String makeDefaultAuthParam(AuthParam authParam) {
        return makeAuthParam(authParam, false, false, false, false);
    }

    /**
     * 新版开放鉴权方式,业务参数转JSON字符串放在params字段中
     *
     * @param authParam 请求参数对象
     * @return 返回构造好的鉴权参数JSON字符串
     */
    public static String makeNewAuthParam(AuthParam authParam) {
        return makeAuthParam(authParam, true, false, false, false);
    }

    /**
     * RestFul开放鉴权方式,业务参数转JSON字符串放在params字段中
     * 需要设置 gatewayPath字段
     *
     * @param authParam 请求参数对象
     * @return 返回构造好的鉴权参数JSON字符串
     */
    public static String makeRestAuthParam(AuthParam authParam) {
        return makeAuthParam(authParam, true, true, false, false);
    }

    /**
     * H5鉴权模式
     * 获取构造好的URL链接
     *
     * @param authParam 请求参数对象
     */
    public static String makeH5Url(AuthParam authParam) {
        return makeAuthParam(authParam, true, false, true, true);
    }

    /**
     * 新版开放鉴权方式,业务参数转JSON字符串放在params字段中(加签+加密)
     *
     * @param authParam 请求参数对象
     * @return 返回构造好的鉴权参数JSON字符串
     */
    public static String makeEncryptAuthParam(AuthParam authParam) {
        return makeAuthParam(authParam, true, false, false, true);
    }

    /**
     * RestFul开放鉴权方式,业务参数转JSON字符串放在params字段中(加签+加密)
     * 需要设置 gatewayPath字段
     *
     * @param authParam 请求参数对象
     * @return 返回构造好的鉴权参数JSON字符串
     */
    public static String makeEncryptRestAuthParam(AuthParam authParam) {
        return makeAuthParam(authParam, true, true, false, true);
    }

    /**
     * 验证返回签名是否正常
     *
     * @param httpResponse http返回的结果
     * @param publicKey    易付宝公钥
     */
    public static boolean verifySign(String httpResponse, String publicKey) {
        try {
            return verifySignBySignType(httpResponse, publicKey, RSA2);
        } catch (Exception e) {
            throw new FtpgsException("验证签名发生异常", e);
        }
    }

    private static boolean verifySignBySignType(String httpResponse, String publicKey, String signType) throws NoSuchAlgorithmException, InvalidKeySpecException, UnsupportedEncodingException, InvalidKeyException, SignatureException {
        // 返回结果解析
        Map<String, Object> resultMap = JSONObject.parseObject(httpResponse, new TypeReference<Map<String, Object>>() {
        }, Feature.OrderedField);
        // 获取签名值gsSign
        String resultSign = (String) resultMap.get(GS_SIGN);
        if (isBlank(resultSign)) {
            return false;
        }
        PublicKey pk = RSAUtil.getPublicKey(publicKey);
        // 排除不参与加签的字段。生成摘要明文（验签失败打印明文）
        String digestData = mapToString(resultMap, GS_SIGN);
        // 对拼接明文进行MD5摘要（验签失败打印MD5）
        digestData = Digest.digest(digestData);
        // 传入摘要、公钥和sign，进行验签
        if (equals(signType, RSA2)) {
            return RSAUtil.vertiy(digestData, resultSign, pk);
        } else {
            return verify(digestData, resultSign, pk, signType);
        }
    }

    public static boolean noticeVerify(String simpleData, String publicKey, String signType) throws InvalidKeySpecException, NoSuchAlgorithmException, UnsupportedEncodingException, SignatureException, InvalidKeyException {
        String signValue = null;
        StringBuilder excludeSignParameter = new StringBuilder();
        String[] excludeParams = simpleData.split("&");
        for (String temp : excludeParams) {
            String[] param = temp.split("=");
            if (param[0].equals("sign") || param[0].equals("sign_type") || param[0].equals("vk_version")) {
                if (param[0].equals("sign")) {
                    signValue = param[1];
                    System.out.println(temp);
                }
                continue;
            }
            excludeSignParameter.append(temp).append("&");
        }

        String verifyParam = excludeSignParameter.toString().substring(0, excludeSignParameter.toString().length() - 1);
        System.out.println(verifyParam);
        PublicKey pk = RSAUtil.getPublicKey(publicKey);
        String digestData = Digest.digest(verifyParam);
        return verify(digestData, signValue, pk, signType);
    }

    /**
     * 验证返回签名是否正常
     *
     * @param httpResponse http返回的结果
     * @param authParam    参数配置
     */
    public static boolean verifySign(String httpResponse, AuthParam authParam) {
        try {
            return verifySignBySignType(httpResponse, authParam.getPublicKey(), authParam.getSignType());
        } catch (Exception e) {
            throw new FtpgsException("验证签名发生异常", e);
        }
    }

    private static void getSign(AuthParam authParam, Map<String, Object> params) {
        try {
            // 排除不参与加签的字段。生成摘要明文(验签失败时，可打印改参数与网关服务端进行比对)
            String digestData = mapToString(params, SIGN_TYPE, SIGN_KEY_INDEX, SIGN);
            // 对拼接明文进行MD5摘要 (验签失败时，可打印改参数与网关服务端进行比对)
            digestData = Digest.digest(digestData);
            // 传入摘要和私钥，进行加签
            PrivateKey pk = RSAUtil.getPrivateKey(authParam.getPrivateKey());
            String signType = authParam.getSignType();
            String sign;
            if (equals(signType, RSA2)) {
                sign = RSAUtil.sign(digestData, pk);
            } else {
                sign = sign(digestData, pk, signType);
            }
            // 将加签后的sign放入请求参数中
            params.put(SIGN, sign);
        } catch (Exception e) {
            e.printStackTrace();
            throw new FtpgsException("生成签名发生异常", e);
        }
    }


    private static Map<String, Object> getParamsMap(AuthParam authParam, boolean isNewVersion, boolean isRestFul, boolean isH5) {
        Map<String, Object> params = new LinkedHashMap<String, Object>();
        // 公共鉴权参数设置
        params.put(TIME_STAMP, authParam.getTimestamp());
        params.put(APP_ID, authParam.getAppId());
        params.put(VERSION, authParam.getVersion());
        params.put(SIGN_TYPE, authParam.getSignType());
        params.put(SIGN_KEY_INDEX, authParam.getSignKeyIndex());
        // 业务参数设置
        if (isNewVersion) {
            params.put(PARAMS, JSON.toJSONString(authParam.getParams()));
        } else {
            params.putAll(authParam.getParams());
        }
        if (isRestFul) {
            params.put(GATEWAY_PATH, authParam.getGatewayPath());
        }
        if (isH5 && !isBlank(authParam.getMerchantUserNo())) {
            params.put(MERCHANT_USER_NO, authParam.getMerchantUserNo());
        }
        return params;
    }

    public static String post(String url, Map<String, Object> params, int connectTimeout, int readTimeOut) throws IOException {
        return post(url, JSON.toJSONString(params), connectTimeout, readTimeOut);
    }

    public static String post(String url, Map<String, Object> params) throws IOException {
        return post(url, JSON.toJSONString(params), 5000, 5000);
    }

    public static String post(String url, String jsonStr) throws IOException {
        return post(url, jsonStr, 5000, 5000);
    }


    /**
     * 功能描述: http-post<br>
     * 发送HTTP 工具类
     *
     * @param url            需要请求的URL地址
     * @param jsonStr        请求参数 JSON格式字符串
     * @param connectTimeout 连接超时时间
     * @param readTimeOut    读取超时时间
     * @return
     * @throws IOException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static String post(String url, String jsonStr, int connectTimeout, int readTimeOut) throws IOException {
        OutputStreamWriter writer = null;
        BufferedReader reader = null;
        try {
            URL urlObject = new URL(url);
            URLConnection conn = urlObject.openConnection();
            // 设置/发送HTTP请求(注意如果商户自行使用httpClient 发送网络请求时，contentType 为 application/json )
            conn.setRequestProperty(CONTENT_TYPE, APPLICATION_JSON_UTF_8);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setConnectTimeout(connectTimeout);
            conn.setReadTimeout(readTimeOut);
            writer = new OutputStreamWriter(conn.getOutputStream(), UTF8_CHARSET);
            writer.write(jsonStr);
            writer.flush();
            // 获取HTTP响应报文体
            StringBuilder responseBuffer = new StringBuilder();
            String line;
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), UTF8_CHARSET));
            while ((line = reader.readLine()) != null) {
                responseBuffer.append(line);
            }
            return responseBuffer.toString();
        } finally {
            if (writer != null) {
                writer.close();
            }
            if (reader != null) {
                reader.close();
            }
        }
    }

    private static String mapToString(Map<String, Object> map, String... exclude) {
        Map<String, String> treeMap = new TreeMap<String, String>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            if (key == null || contains(exclude, key)) {
                continue;
            }
            if (entry.getValue() instanceof String) {
                treeMap.put(entry.getKey(), entry.getValue().toString().trim());
            } else {
                treeMap.put(entry.getKey(), JSON.toJSONString(entry.getValue()));
            }
        }
        return append(treeMap);
    }

    private static String append(Map<String, String> map) {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            builder.append(entry.getKey());
            builder.append(EQ);
            builder.append(entry.getValue());
            builder.append(AND);
        }
        if (builder.length() > 0) {
            builder.deleteCharAt(builder.length() - 1);
        }
        return builder.toString().trim();
    }

    /**
     * 拼接明文
     */
    private static String appendUrl(Map<String, Object> map) {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            builder.append(entry.getKey());
            builder.append(EQ);
            String value = entry.getValue().toString();
            try {
                value = URLEncoder.encode(value, UTF8_CHARSET);
            } catch (UnsupportedEncodingException e) {//NOSONAR
            }
            builder.append(value);
            builder.append(AND);
        }
        if (builder.length() > 0) {
            builder.deleteCharAt(builder.length() - 1);
        }
        return builder.toString().trim();
    }

    private static boolean contains(Object[] array, Object objectToFind) {
        return indexOf(array, objectToFind) != -1;
    }

    private static int indexOf(Object[] array, Object objectToFind) {
        if (array == null) {
            return INDEX_NOT_FOUND;
        }
        if (objectToFind == null) {
            for (int i = 0; i < array.length; i++) {
                if (array[i] == null) {
                    return i;
                }
            }
        } else if (array.getClass().getComponentType().isInstance(objectToFind)) {
            for (int i = 0; i < array.length; i++) {
                if (objectToFind.equals(array[i])) {
                    return i;
                }
            }
        }
        return INDEX_NOT_FOUND;
    }

    /**
     * 鉴权参数
     */
    public static class AuthParam {

        /**
         * 请求访问的URL
         */
        private String url;

        /**
         * appId
         */
        private String appId;

        /**
         * 版本,默认1.0
         */
        private String version = "1.0";

        /**
         * 时间戳 格式：yyyyMMddHHmmss
         */
        private String timestamp;

        /**
         * 签名方式,默认RSA2
         */
        private String signType;

        /**
         * 密钥索引
         */
        private String signKeyIndex;


        /**
         * 商户私钥
         */
        private String privateKey;

        /**
         * 苏宁金融网关平台提供的公钥
         */
        private String publicKey;

        /**
         * restFul风格时传递，接口路径
         */
        private String gatewayPath;

        /**
         * 商户会员号，受限接口，或半受限需要传递
         */
        private String merchantUserNo;


        /**
         * 用于AES加密的 key
         */
        private String key;

        /**
         * 业务参数
         */
        private Map<String, Object> params = new LinkedHashMap<String, Object>();

        private String getAppId() {
            return appId;
        }

        public AuthParam setAppId(String appId) {
            this.appId = appId;
            return this;
        }

        private String getVersion() {
            if (isBlank(version)) {
                version = "1.0";
            }
            return version;
        }

        public AuthParam setVersion(String version) {
            this.version = version;
            return this;
        }

        private String getTimestamp() {
            if (isBlank(timestamp)) {
                timestamp = new SimpleDateFormat(PATTERN).format(new Date());
            }
            return timestamp;
        }

        public AuthParam setTimestamp(String timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        private String getSignType() {
            if (isBlank(signType)) {
                signType = RSA2;
            }
            return signType;
        }

        public AuthParam setSignType(String signType) {
            this.signType = signType;
            return this;
        }

        private String getSignKeyIndex() {
            return signKeyIndex;
        }

        public AuthParam setSignKeyIndex(String signKeyIndex) {
            this.signKeyIndex = signKeyIndex;
            return this;
        }

        private Map<String, Object> getParams() {
            return params;
        }

        public AuthParam setParams(Map<String, Object> params) {
            this.params = params;
            return this;
        }

        private String getPrivateKey() {
            return privateKey;
        }

        public AuthParam setPrivateKey(String privateKey) {
            this.privateKey = privateKey;
            return this;
        }

        public String getGatewayPath() {
            return gatewayPath;
        }

        public AuthParam setGatewayPath(String gatewayPath) {
            this.gatewayPath = gatewayPath;
            return this;
        }

        public String getUrl() {
            return url;
        }

        public AuthParam setUrl(String url) {
            this.url = url;
            return this;
        }

        public String getMerchantUserNo() {
            return merchantUserNo;
        }

        public void setMerchantUserNo(String merchantUserNo) {
            this.merchantUserNo = merchantUserNo;
        }

        public String getPublicKey() {
            return publicKey;
        }

        public AuthParam setPublicKey(String publicKey) {
            this.publicKey = publicKey;
            return this;
        }

        public String getKey() {
            if (isBlank(key)) {
                key = EncryptUtil.getAesKey();
            }
            return key;
        }

        public AuthParam setKey(String key) {
            this.key = key;
            return this;
        }
    }

    public static class FtpgsException extends RuntimeException {
        public FtpgsException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    private static boolean isBlank(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }


    private static boolean equals(String str1, String str2) {
        return str1 == null ? str2 == null : str1.equals(str2);
    }

    private static String getString(Map map, Object key) {
        if (map != null) {
            Object answer = map.get(key);
            if (answer != null) {
                return answer.toString();
            }
        }
        return null;
    }

    private static String sign(String data, PrivateKey pk, String algorithm) throws InvalidKeyException, NoSuchAlgorithmException,
            SignatureException {
        return Base64.encodeBase64String(sign(data.getBytes(), pk, algorithm)).trim();
    }

    private static byte[] sign(byte[] data, PrivateKey prik, String algorithm) throws NoSuchAlgorithmException, InvalidKeyException,
            SignatureException {
        Signature signature = Signature.getInstance(algorithm);
        signature.initSign(prik);
        signature.update(data);
        return signature.sign();
    }

    private static boolean verify(String data, String sign, PublicKey pk, String algorithm) throws InvalidKeyException,
            NoSuchAlgorithmException, SignatureException {
        return verify(data.getBytes(), Base64.decodeBase64(sign), pk, algorithm);
    }

    private static boolean verify(byte[] data, byte[] sign, PublicKey pk, String algorithm) throws NoSuchAlgorithmException,
            InvalidKeyException, SignatureException {
        Signature signature = Signature.getInstance(algorithm);
        signature.initVerify(pk);
        signature.update(data);
        return signature.verify(sign);
    }
}
