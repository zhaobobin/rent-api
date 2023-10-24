        
package com.rent.service.user.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.converter.user.ConfigConverter;
import com.rent.common.dto.user.ConfigDto;
import com.rent.common.dto.user.ConfigReqDto;
import com.rent.dao.user.ConfigDao;
import com.rent.model.user.Config;
import com.rent.service.user.ConfigService;
import com.rent.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 配置信息Service
 *
 * @author zhao
 * @Date 2020-11-11 16:52
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ConfigServiceImpl implements ConfigService {

    private final ConfigDao configDao;

    @Override
    public Boolean modifyConfig(ConfigDto request) {
        Config config = configDao.getById(request.getId());
        config.setValue(request.getValue());
        config.setUpdateTime(new Date());
        configDao.updateById(config);
        RedisUtil.set(config.getCode(),config.getValue());
        return Boolean.TRUE;
    }



    @Override
    public Page<ConfigDto> queryConfigPage(ConfigReqDto request) {
        Page<Config> page = configDao.page(new Page<>(request.getPageNumber(), request.getPageSize()),
            new QueryWrapper<>(ConfigConverter.reqDto2Model(request)));
        return new Page<ConfigDto>(page.getCurrent(), page.getSize(), page.getTotal()).setRecords(
            ConfigConverter.modelList2DtoList(page.getRecords()));
    }

    @Override
    public String getConfigByCode(String code) {
        return configDao.getValueByCode(code);
    }


}