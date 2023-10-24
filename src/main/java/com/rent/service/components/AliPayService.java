package com.rent.service.components;

import com.rent.common.dto.components.response.AlipayOauthTokenResponse;

/**
 * @author xiaoyao
 * @version V1.0
 * @date 2020-7-3 下午 1:38:54
 * @since 1.0
 */
public interface AliPayService {

    /**
     * https://docs.open.alipay.com/api_9/alipay.system.oauth.token/
     * 换取授权访问令牌
     *
     * @param code 授权码
     * @return
     */
    AlipayOauthTokenResponse getAliPaySystemOauthToken(String code);

}
