        
package com.rent.mapper.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rent.model.user.UserThirdInfo;
import org.apache.ibatis.annotations.Param;

/**
 * UserThirdInfoDao
 *
 * @author zhao
 * @Date 2020-06-28 14:24
 */
public interface UserThirdInfoMapper extends BaseMapper<UserThirdInfo>{

    /**
     * 替换用户第三方信息的 把origin替换成newUid
     * @param origin
     * @param newUid
     */
    void replaceUid(@Param("origin") String origin,@Param("newUid") String newUid);
}