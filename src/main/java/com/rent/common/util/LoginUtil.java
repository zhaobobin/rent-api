package com.rent.common.util;

import com.rent.util.MD5;

/**
 * @author zhaowenchao
 */
public class LoginUtil {
	
	public static  String  verifySmsCode(String telephone,String codeKey,String code,Long codeTime,boolean verifyCodeTime) {
		if(verifyCodeTime) {
			if((System.currentTimeMillis()/1000)-codeTime>300) {
				return "验证码已过期";
			}
		}
		String trueCode = encryptSmsCode(code+telephone+codeTime.toString());
		if(trueCode.equals(codeKey)) {
			return null;
		}else {
			return "验证码错误";
		}
	}
	
	public static  String encryptSmsCode(String code) {
		return MD5.getMD5(MD5.getMD5(code+"qj479Z7@##$@$@%11123wqeqweqe221313pm5bW1CGV123@#5"));
	}


}
