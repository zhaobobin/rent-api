package com.rent.service.user.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.converter.user.BackstageFunctionConverter;
import com.rent.common.dto.backstage.request.MenuPageReq;
import com.rent.common.dto.backstage.resp.AuthPageResp;
import com.rent.dao.user.BackstageFunctionDao;
import com.rent.exception.HzsxBizException;
import com.rent.model.user.BackstageFunction;
import com.rent.service.user.MenuService;
import com.rent.util.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {
    private final BackstageFunctionDao backstageFunctionDao;

    @Override
    public Page<AuthPageResp> page(MenuPageReq request) {
        Page<BackstageFunction> page = backstageFunctionDao.page(
                new Page<>(request.getPageNumber(), request.getPageSize()),
                new QueryWrapper<BackstageFunction>()
                        .eq(!Objects.isNull(request.getParentId()), "parent_id", request.getParentId())
                        .like(StringUtil.isNotEmpty(request.getName()), "name", "%" + request.getName() + "%")
//                        .eq(StringUtil.isNotEmpty(request.getName()), "name", request.getName())
                        .eq(StringUtil.isNotEmpty(request.getCode()), "code", request.getCode())
                        .eq(StringUtil.isNotEmpty(request.getType()), "type", request.getType())
                        .eq(!Objects.isNull(request.getPlatform()), "platform", request.getPlatform())
        );
        List<AuthPageResp> records = BackstageFunctionConverter.modelListToAuthPageRespList(page.getRecords());
        if (!CollectionUtil.isEmpty(records)) {
            for (int i = 0; i < records.size(); i++) {
                AuthPageResp record = records.get(i);
                record.setChild(BackstageFunctionConverter.modelListToAuthPageRespList(backstageFunctionDao.getChildById(record.getId())));
                if (!CollectionUtil.isEmpty(record.getChild())) {
                    for (int j = 0; j < record.getChild().size(); j++) {
                        AuthPageResp child = record.getChild().get(j);
                        child.setChild(BackstageFunctionConverter.modelListToAuthPageRespList(backstageFunctionDao.getChildById(child.getId())));
                    }
                }
            }

        }
        return new Page<AuthPageResp>(page.getCurrent(), page.getSize(), page.getTotal()).setRecords(records);
    }

    @Override
    public boolean add(MenuPageReq request) {
        BackstageFunction parentFunction = backstageFunctionDao.getById(request.getParentId());
        if (request.getParentId() != 0 && parentFunction == null) {
            throw new HzsxBizException("-1", "父菜单不存在");
        }
        BackstageFunction backstageFunction = new BackstageFunction();
        backstageFunction.setParentId(request.getParentId());
        backstageFunction.setName(request.getName());

        backstageFunction.setCode(StringUtil.isEmpty(request.getCode()) ? "" : request.getCode());
        backstageFunction.setType(request.getType());
        backstageFunction.setPlatform(request.getPlatform());
        backstageFunction.setCreateTime(new Date());
        backstageFunctionDao.save(backstageFunction);
        return false;
    }
}
