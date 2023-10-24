package com.rent.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "oss")
public class OssProperties {

    private String endpoint;
    private String accessId;
    private String accessKey;
    private String bucketName;
    private String fileUrlPrefix;

}
