package com.rent.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "ant-chain")
public class AntChainProperties {

    private String accessKey;
    private String accessSecret;
    private String leaseId;
}
