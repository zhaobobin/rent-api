        
package com.rent.service.user;



import com.rent.common.dto.user.AlipayExemptLoginReq;
import com.rent.common.dto.user.UserThirdInfoDto;

/**
 * 小程序登录服务
 *
 * @author zhao
 * @Date 2020-06-28 14:20
 */
public interface UserLoginService {

    /**
     * 支付宝小程序用户免密登录
     * @param req
     * @return
     */
    UserThirdInfoDto alipayExemptLogin(AlipayExemptLoginReq req);

}