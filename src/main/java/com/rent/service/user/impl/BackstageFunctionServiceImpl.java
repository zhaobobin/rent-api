
package com.rent.service.user.impl;


import com.google.common.collect.Sets;
import com.rent.common.converter.user.BackstageFunctionConverter;
import com.rent.common.dto.backstage.resp.AuthPageResp;
import com.rent.common.enums.user.EnumBackstageUserPlatform;
import com.rent.dao.user.BackstageFunctionDao;
import com.rent.model.user.BackstageFunction;
import com.rent.service.user.BackstageFunctionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 后台功能点Service
 *
 * @author zhao
 * @Date 2020-06-24 11:47
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class BackstageFunctionServiceImpl implements BackstageFunctionService {

    private final BackstageFunctionDao backstageFunctionDao;

    @Override
    public List<AuthPageResp> getFunctionList(EnumBackstageUserPlatform platform, List<Long> chosenFunction) {
        List<BackstageFunction> list = backstageFunctionDao.getFunctionList(platform);
        AuthPageResp resp = new AuthPageResp();
        resp.setId(0L);
        Set<Long> chosenFunctionIdSet = Sets.newHashSet(chosenFunction);
        packChildren(resp, list, chosenFunctionIdSet);
        return resp.getChild();
    }

    private void packChildren(AuthPageResp resp, List<BackstageFunction> list, Set<Long> chosenFunction) {
        List<BackstageFunction> childList = list.stream().filter(backstageFunction -> backstageFunction.getParentId().equals(resp.getId())).collect(Collectors.toList());
        List<AuthPageResp> dtoList = new ArrayList<>(childList.size());
        for (BackstageFunction backstageFunction : childList) {
            AuthPageResp dto = BackstageFunctionConverter.modelToAuthPageResp(backstageFunction);
            dto.setChosen(chosenFunction.contains(dto.getId()));
            dtoList.add(dto);
            packChildren(dto, list, chosenFunction);
        }
        resp.setChild(dtoList);
    }
}