package com.rent.dao.user.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.user.ConfigDao;
import com.rent.mapper.user.ConfigMapper;
import com.rent.model.user.Config;
import org.springframework.stereotype.Repository;

/**
 * ConfigDao
 *
 * @author zhao
 * @Date 2020-11-11 16:52
 */
@Repository
public class ConfigDaoImpl extends AbstractBaseDaoImpl<Config, ConfigMapper> implements ConfigDao {


    @Override
    public String getValueByCode(String code) {
        Config config =  getOne(new QueryWrapper<Config>().eq("code",code).isNull("delete_time"));
        return config == null ? null : config.getValue();
    }
}
