package com.rent.config.doc;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhaowenchao
 */
@Configuration
public class SpringDocConfig {

    @Bean
    public OpenAPI createRestApi() {
        Info info = new Info().title("租赁系统").version("v1.0.0");
        return new OpenAPI().info(info);
    }
}
