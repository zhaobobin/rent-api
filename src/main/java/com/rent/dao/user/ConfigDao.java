        
package com.rent.dao.user;


import com.rent.config.mybatis.IBaseDao;
import com.rent.model.user.Config;

/**
 * ConfigDao
 *
 * @author zhao
 * @Date 2020-11-11 16:52
 */
public interface ConfigDao extends IBaseDao<Config> {

    /**
     * 根据code获取value
     * @param code
     * @return
     */
    String getValueByCode(String code);


}
