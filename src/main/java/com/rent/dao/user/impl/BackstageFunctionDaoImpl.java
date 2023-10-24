package com.rent.dao.user.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rent.common.converter.user.BackstageFunctionConverter;
import com.rent.common.dto.user.BackstageFunctionDto;
import com.rent.common.enums.user.EnumBackstageUserPlatform;
import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.user.BackstageFunctionDao;
import com.rent.mapper.user.BackstageFunctionMapper;
import com.rent.model.user.BackstageFunction;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


/**
 * BackstageFunctionDao
 *
 * @author zhao
 * @Date 2020-06-24 11:47
 */
@Repository
public class BackstageFunctionDaoImpl extends AbstractBaseDaoImpl<BackstageFunction, BackstageFunctionMapper> implements BackstageFunctionDao {
    @Override
    public List<BackstageFunction> getFunctionList(EnumBackstageUserPlatform platform) {
        List<BackstageFunction> list = list(new QueryWrapper<>());
        list = list.stream().filter(e -> e.getPlatform().equals(platform)).collect(Collectors.toList());
        return list;
    }

    @Override
    public List<BackstageFunctionDto> getDtoByIds(List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.emptyList();
        }
        List<BackstageFunction> list = list(new QueryWrapper<BackstageFunction>().in("id", ids));
        return BackstageFunctionConverter.modelList2DtoList(list);
    }

    @Override
    public List<BackstageFunction> getChildById(Long id) {
        return list(new QueryWrapper<BackstageFunction>().eq("parent_id", id));
    }
}
