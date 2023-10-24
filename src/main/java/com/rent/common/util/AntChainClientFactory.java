package com.rent.common.util;

import cn.com.antcloud.api.AntFinTechApiClient;
import cn.com.antcloud.api.AntFinTechProfile;
import com.rent.common.properties.AntChainProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class AntChainClientFactory {

    private static volatile AntFinTechApiClient client = null;
    private static final String baseUrl = "https://prodapigw.cloud.alipay.com";

    private AntChainProperties antChainProperties;

    public AntChainClientFactory(AntChainProperties antChainProperties) {
        this.antChainProperties = antChainProperties;
    }

    /**
     * 获取阿里请求客户端
     * @return
     */
    public AntFinTechApiClient getClient() {
        if (client == null) {
            initClient();
        }
        return client;
    }


    private synchronized void initClient() {
        if(client!=null){
            return;
        }
        AntFinTechProfile profile = AntFinTechProfile.getProfile(baseUrl, antChainProperties.getAccessKey(),antChainProperties.getAccessSecret());
        client = new AntFinTechApiClient(profile);
    }

}