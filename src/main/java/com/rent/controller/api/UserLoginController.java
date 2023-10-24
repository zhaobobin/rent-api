package com.rent.controller.api;


import com.rent.common.dto.CommonResponse;
import com.rent.common.dto.user.AlipayExemptLoginReq;
import com.rent.common.dto.user.UserThirdInfoDto;
import com.rent.exception.HzsxBizException;
import com.rent.service.user.UserLoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhaowenchao
 */
@RequiredArgsConstructor
@RestController
@Tag(name = "小程序用户模块")
@RequestMapping("/zyj-api-web/hzsx/aliPay/user")
public class UserLoginController {

    private final UserLoginService userLoginService;

    @Operation(summary = "新版登录")
    @PostMapping("/exemptLogin")
    public CommonResponse<UserThirdInfoDto> exemptLogin(@RequestBody AlipayExemptLoginReq req) {
        if(StringUtils.isEmpty(req.getAuthCode())){
            throw new HzsxBizException("-1","获取授权码异常,请到【我的】页面重新授权");
        }
        return CommonResponse.<UserThirdInfoDto>builder().data(userLoginService.alipayExemptLogin(req)).build();
    }


}
