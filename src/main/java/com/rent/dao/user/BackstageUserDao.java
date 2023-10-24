        
package com.rent.dao.user;


import com.rent.common.dto.user.BackstageUserReqDto;
import com.rent.config.mybatis.IBaseDao;
import com.rent.model.user.BackstageUser;

/**
 * BackstageUserDao
 *
 * @author zhao
 * @Date 2020-06-24 11:46
 */
public interface BackstageUserDao extends IBaseDao<BackstageUser> {

    /**
     * 删除用户
     * @param request
     * @return
     */
    Boolean deleteBackstageUser(BackstageUserReqDto request);


    /**
     * 根据店铺ID获取注册的商家用户（即主账户）
     * @param shopId
     * @return
     */
    BackstageUser getRegisterByShopId(String shopId);
}
