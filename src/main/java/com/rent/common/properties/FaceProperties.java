package com.rent.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "face")
public class FaceProperties {

    private  String keyId;
    private  String keySecret;
    private  String sceneId;
}
