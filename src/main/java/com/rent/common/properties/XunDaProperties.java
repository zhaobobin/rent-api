package com.rent.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
@Data
@Component
@ConfigurationProperties(prefix = "sms.xunda")
public class XunDaProperties {
    private String account;
    private String password;
    private String extNo;
}