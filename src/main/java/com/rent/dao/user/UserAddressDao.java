        
package com.rent.dao.user;

import com.rent.config.mybatis.IBaseDao;
import com.rent.model.user.UserAddress;

import java.util.List;

/**
 * UserAddressDao
 *
 * @author zhao
 * @Date 2020-06-18 13:54
 */
public interface UserAddressDao extends IBaseDao<UserAddress> {

    /**
     * 获取用户地址列表信息
     * @param uid
     * @return
     */
    List<UserAddress> getUserAddress(String uid);

    /**
     * 更新用户地址信息的uid 把origin替换成newUid
     * @param origin
     * @param newUid
     */
    void replaceUid(String origin, String newUid);

    /**
     * 将用户所有的地址都改成非默认的
     * @param uid
     */
    void updateUserAddressNoneDefault(String uid);
}
