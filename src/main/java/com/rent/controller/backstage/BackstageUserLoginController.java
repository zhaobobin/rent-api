package com.rent.controller.backstage;


import com.rent.common.dto.CommonResponse;
import com.rent.common.dto.backstage.RegisterReqDto;
import com.rent.common.dto.backstage.RestPasswordOnLoginedReqDto;
import com.rent.common.dto.backstage.RestPasswordReqDto;
import com.rent.common.dto.backstage.SendValidateCodeRespDto;
import com.rent.common.dto.backstage.request.LoginReqDto;
import com.rent.common.dto.backstage.resp.LoginRespDto;
import com.rent.common.dto.user.ChannelRegisterReqDto;
import com.rent.service.user.BackstageUserLoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


@Slf4j
@RestController
@RequestMapping("/zyj-backstage-web/hzsx/user")
@RequiredArgsConstructor
@Tag(name = "后台用户登录注册相关")
public class BackstageUserLoginController {

    private final BackstageUserLoginService loginService;

    @Operation(summary = "后台用户登录")
    @PostMapping("/login")
    public CommonResponse<LoginRespDto> login(@RequestBody @Valid LoginReqDto loginDto) {
        return CommonResponse.<LoginRespDto>builder().data(loginService.login(loginDto)).build();
    }

    @Operation(summary = "后台用户退出登录")
    @GetMapping("/logout")
    public CommonResponse<Void> logout(HttpServletRequest request) {
        loginService.logout(request);
        return CommonResponse.<Void>builder().data(null).build();
    }

    @Operation(summary = "商家用户注册")
    @PostMapping("/register")
    public CommonResponse<Long> register(@RequestBody @Valid RegisterReqDto registerReqDto) {
        return CommonResponse.<Long>builder().data(loginService.register(registerReqDto)).build();
    }

    @Operation(summary = "后台用户发送短信验证码")
    @GetMapping("/sendValidateCode")
    public CommonResponse<SendValidateCodeRespDto> sendValidateCode(@Parameter(name = "mobile", description = "手机号码") @RequestParam("mobile") String mobile) {
        return CommonResponse.<SendValidateCodeRespDto>builder().data(loginService.sendValidateCode(mobile)).build();
    }

    @Operation(summary = "后台用户重置密码-登陆后")
    @PostMapping("/restPasswordOnLogined")
    public CommonResponse<Boolean> restPasswordOnLogin(@RequestBody @Valid RestPasswordOnLoginedReqDto reqDto) {
        return CommonResponse.<Boolean>builder().data(loginService.restPasswordOnLogin(reqDto.getMobile(), reqDto.getOldPassword(), reqDto.getNewPassword(), reqDto.getUserType())).build();
    }

    @Operation(summary = "后台用户重置密码")
    @PostMapping("/restPassword")
    public CommonResponse<Boolean> restPassword(@RequestBody @Valid RestPasswordReqDto reqDto) {
        return CommonResponse.<Boolean>builder().data(loginService.restPassword(reqDto.getMobile(), reqDto.getCodeKey(), reqDto.getCode(), reqDto.getCodeTime(), reqDto.getNewPassword(), reqDto.getUserType())).build();
    }

    @Operation(summary = "渠道用户注册")
    @PostMapping("/channelRegister")
    public CommonResponse<Long> channelRegister(@RequestBody @Valid ChannelRegisterReqDto registerReqDto) {
        return CommonResponse.<Long>builder().data(loginService.channelRegister(registerReqDto)).build();
    }
}

