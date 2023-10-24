package com.rent.service.user;


import com.rent.common.dto.backstage.RegisterReqDto;
import com.rent.common.dto.backstage.SendValidateCodeRespDto;
import com.rent.common.dto.backstage.request.LoginReqDto;
import com.rent.common.dto.backstage.resp.LoginRespDto;
import com.rent.common.dto.user.ChannelRegisterReqDto;
import com.rent.common.enums.user.EnumBackstageUserPlatform;

import javax.servlet.http.HttpServletRequest;

/**
 * @author udo
 */
public interface BackstageUserLoginService {


    /**
     * 后台用户登录 获取用户信息
     *
     * @param loginDto
     * @return
     */
    LoginRespDto login(LoginReqDto loginDto);

    void logout(HttpServletRequest request);


    /**
     * 用户注册-只有商家可以注册
     *
     * @param registerReqDto
     * @return
     */
    Long register(RegisterReqDto registerReqDto);

    /**
     * 用户重置密码发送验证码
     *
     * @param email
     * @return
     */
    SendValidateCodeRespDto sendValidateCode(String email);

    /**
     * 用户重置密码
     *
     * @param mobile
     * @param codeKey
     * @param code
     * @param codeTime
     * @param newPassword
     * @param userType
     * @return
     */
    Boolean restPassword(String mobile, String codeKey, String code, Long codeTime, String newPassword, EnumBackstageUserPlatform userType);

    Boolean restPasswordOnLogin(String mobile, String oldPassword, String newPassword, EnumBackstageUserPlatform userType);


    /**
     * 渠道用户注册账号
     *
     * @param registerReqDto
     * @return
     */
    Long channelRegister(ChannelRegisterReqDto registerReqDto);
}
