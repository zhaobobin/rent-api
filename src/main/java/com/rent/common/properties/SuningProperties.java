package com.rent.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "suning")
public class SuningProperties {
    private String openSign; // 是否开启签约功能
    private String appId; // 应用唯一标识
    private String salerMerchantNo; // 易付宝平台获取 商户号
    private String signKeyIndex; // 公钥索引
    private String goodsType; //
    private String eppPublicKey; // 苏宁的加密公钥
    private String eppPublicSignKey; // 苏宁的验签公钥
    private String merchantPrivateKey;// 商户端生产的私钥
    private String merchantPublicKey;// 商户端生产的私钥
    private String envName;// 调用易付宝的网关环境

}
