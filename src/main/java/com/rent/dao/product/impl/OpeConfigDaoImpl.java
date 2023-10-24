    
package com.rent.dao.product.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.product.OpeConfigDao;
import com.rent.mapper.product.OpeConfigMapper;
import com.rent.model.product.OpeConfig;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * OpeConfigDao
 *
 * @author youruo
 * @Date 2020-06-30 15:13
 */
@Repository
public class OpeConfigDaoImpl extends AbstractBaseDaoImpl<OpeConfig, OpeConfigMapper> implements OpeConfigDao {


    @Override
    public Set<Integer> getConfigId(Integer type, String channelId) {
        List<OpeConfig> opeConfigList = list(new QueryWrapper<OpeConfig>()
                .select("config_id")
                .eq("index_type", type)
                .eq("channel_id", channelId)
                .isNull("delete_time"));

        return opeConfigList.stream().map(OpeConfig::getConfigId).collect(Collectors.toSet());
    }
}
