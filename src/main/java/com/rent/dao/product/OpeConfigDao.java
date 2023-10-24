        
package com.rent.dao.product;

import com.rent.config.mybatis.IBaseDao;
import com.rent.model.product.OpeConfig;

import java.util.Set;

/**
 * OpeConfigDao
 *
 * @author youruo
 * @Date 2020-06-30 15:13
 */
public interface OpeConfigDao extends IBaseDao<OpeConfig> {

    /**
     * 查询渠道下的配置ID
     * @param type
     * @param channelId
     * @return
     */
    Set<Integer> getConfigId(Integer type,String channelId);


}
