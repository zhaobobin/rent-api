package com.rent.common.constant;

import java.util.HashMap;
import java.util.Map;

public enum SuningOpenAPIConstant {

//    // 测试接口
//    private static final String BASE_URL = "https://ftpgspre.cnsuning.com/ftpgs/trade/agreementPayGateway";
//    private static final String PRO_BASE_URL = "https://mfg.suning.com/ftpgs/ocps/agreementPayment";

    PRE_URL("PRE", "https://ftpgspre.cnsuning.com/ftpgs/trade/agreementPayGateway"), // 测试环境

    PRO_URL("PRO", "https://mfg.suning.com/ftpgs/ocps/agreementPayment"); // 生产环境

    private static Map<String, String> URL_MAP = new HashMap<String, String>() {
        {
            put("SIGN", "sign"); // 签约
            put("CONFIRM", "confirm"); //确认
            put("PAY", "pay"); //支付
            put("REFUND", "createRefundOrder"); //退款
        }
    };


    SuningOpenAPIConstant(String envName, String requestUrl) {
        this.envName = envName;
        this.requestUrl = requestUrl;

    }

    private String envName;

    private String requestUrl;


    public static String getUrlByEnvName(String envName, String key) {
        if (null == envName) return null;
        for (SuningOpenAPIConstant environmentEnum : SuningOpenAPIConstant.values()) {
            if (environmentEnum.getEnvName().equals(envName)) {
                String base_url = environmentEnum.getRequestUrl();
                String url = URL_MAP.get(key);
                return base_url + "/" + url;
            }
        }
        return null;
    }

    public String getEnvName() {
        return envName;
    }

    public void setEnvName(String envName) {
        this.envName = envName;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

}
