package com.rent.service.components.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.rent.common.dto.components.response.AlipayOauthTokenResponse;
import com.rent.common.util.AliPayClientFactory;
import com.rent.exception.HzsxBizException;
import com.rent.service.components.AliPayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author xiaoyao
 * @version V1.0
 * @date 2020-7-3 下午 1:39:25
 * @since 1.0
 */
@Service
@Slf4j
public class AliPayServiceImpl implements AliPayService {

    @Override
    public AlipayOauthTokenResponse getAliPaySystemOauthToken(String code) {
        log.info("[授权访问令牌] code:{}", code);
        AlipayClient alipayClient = AliPayClientFactory.getAlipayClientByType();

        AlipaySystemOauthTokenRequest request = new AlipaySystemOauthTokenRequest();
        request.setGrantType("authorization_code");
        request.setCode(code);

        AlipaySystemOauthTokenResponse response;
        try {
            response = alipayClient.certificateExecute(request);
            if (!response.isSuccess()) {
                log.error(
                    "aliPayService getAliPaySystemOauthToken failed -->" + response.getBody() + "-->" + " sub_code: "
                        + response.getSubCode());
                throw new HzsxBizException("99999", "aliPayService getAliPaySystemOauthToken 异常");
            }

        } catch (Exception e) {
            log.error("业务出现异常", e);
            throw new HzsxBizException("99999", "aliPayService getAliPaySystemOauthToken 异常");
        }
        AlipayOauthTokenResponse resp = new AlipayOauthTokenResponse();
        BeanUtil.copyProperties(response, resp);
        return resp;
    }

}
