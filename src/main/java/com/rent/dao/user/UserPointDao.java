        
package com.rent.dao.user;

import com.rent.config.mybatis.IBaseDao;
import com.rent.model.order.UserPointDto;
import com.rent.model.user.UserPoint;

import java.util.List;

/**
 * UserPointDao
 *
 * @author youruo
 * @Date 2020-09-25 14:38
 */
public interface UserPointDao extends IBaseDao<UserPoint> {

    /**
     * 获取所有的信息记录
     * @return
     */
    List<UserPointDto> getAllData();

    /**
     * 根据uid获取埋点信息
     * @param uid
     * @return
     */
    List<UserPointDto> getByUid(String uid);


}
