        
package com.rent.mapper.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rent.model.user.UserAddress;
import org.apache.ibatis.annotations.Param;

/**
 * UserAddressDao
 *
 * @author zhao
 * @Date 2020-06-18 13:54
 */
public interface UserAddressMapper extends BaseMapper<UserAddress>{


    /**
     * 替换用户第三方信息的 把origin替换成newUid
     * @param origin
     * @param newUid
     */
    void replaceUid(@Param("origin") String origin, @Param("newUid") String newUid);

    /**
     * 将用户所有的地址都改成非默认的
     * @param uid
     */
    void updateUserAddressNoneDefault(@Param("uid")String uid);
}