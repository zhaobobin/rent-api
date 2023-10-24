package com.rent.util;


import com.rent.common.dto.bo.LoginUserBo;
import com.rent.common.enums.EnumContext;

/**
 * 验证码生成器
 *
 * @author
 */
public class LoginUserUtil {

    private static final String LOGIN_USER_KEY="LOGIN_USER";

    public static void setLoginUser(LoginUserBo user){
        ThreadContextStoreUtil.getInstance().set(LOGIN_USER_KEY, user);
        ThreadContextStoreUtil.getInstance().set(EnumContext.OPERATOR_ID.getCode(), user.getId().toString());
    }

    public static LoginUserBo getLoginUser(){
        return (LoginUserBo)ThreadContextStoreUtil.getInstance().get(LOGIN_USER_KEY);
    }


}
