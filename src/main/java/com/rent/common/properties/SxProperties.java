package com.rent.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "sx")
public class SxProperties {
    private String institutionId;
    private String aesKey;

}