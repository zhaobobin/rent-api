package com.rent.common.util;

import com.alipay.api.AlipayClient;
import com.alipay.api.CertAlipayRequest;
import com.alipay.api.DefaultAlipayClient;
import com.rent.config.outside.OutsideConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Author <a href="mailto:boannpx@163.com">niupq</a>
 * Date: 2019/6/19
 * Version:V1.0
 *
 * @author boan
 */
@Component
@Slf4j
public class AliPayClientFactory {

    private static volatile AlipayClient client = null;

    private static final String CHARSET = "utf-8";
    private static final String FORMAT = "json";
    private static final String SIGNTYPE = "RSA2";
    private static final String URL = "https://openapi.alipay.com/gateway.do";

    /**
     * 获取阿里请求客户端
     * @return
     */
    public static AlipayClient getAlipayClientByType() {
        if (client == null) {
            initClientMap();
        }
        return client;
    }

    /**
     * 初始化阿里请求客户端
     * https://opendocs.alipay.com/mini/0092cb
     * Q：如果某个应用有一个接口采用公钥证书模式接入，那同一个 APPID 下的所有接口调用是否必须都使用公钥证书模式？
     * A：是的。
     */
    public synchronized static void initClientMap() {
        if(client!=null){
            return;
        }
        CertAlipayRequest certAlipayRequest = new CertAlipayRequest();
        certAlipayRequest.setServerUrl(URL);
        certAlipayRequest.setFormat(FORMAT);
        certAlipayRequest.setCharset(CHARSET);
        certAlipayRequest.setSignType(SIGNTYPE);
        certAlipayRequest.setAppId(OutsideConfig.APPID);
        certAlipayRequest.setPrivateKey(OutsideConfig.PRIVATE_KEY);

        certAlipayRequest.setCertPath(OutsideConfig.APP_CERT_PUBLIC);
        certAlipayRequest.setAlipayPublicCertPath(OutsideConfig.ALIPAY_CERT_PUBLIC);
        certAlipayRequest.setRootCertPath(OutsideConfig.ALIPAY_CERT_ROOT);
        try {
            client = new DefaultAlipayClient(certAlipayRequest);
        } catch (Exception e) {
            log.error("【初始化支付宝AlipayClient异常】", e);
        }
    }

}