package com.rent;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author zhaowenchao
 */
@SpringBootApplication(scanBasePackages = {"com.rent.**"}, exclude = {DataSourceAutoConfiguration.class})
@Slf4j
@EnableScheduling
@EnableConfigurationProperties
public class RentApplication {

    public static void main(String[] args) {
//        AliPayClientFactory.initClientMap();
        SpringApplication.run(RentApplication.class, args);
        System.out.println("启动成功 ");
    }

}
