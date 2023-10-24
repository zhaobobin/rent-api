package com.rent.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "sms.aliyun")
public class SmsAliyunProperties {

    private String accessKey;
    private String accessSecret;
}
