
package com.rent.common.converter.user;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.rent.common.dto.backstage.resp.AuthPageResp;
import com.rent.common.dto.user.BackstageFunctionDto;
import com.rent.model.user.BackstageFunction;

import java.util.Collections;
import java.util.List;


/**
 * 后台功能点Service
 *
 * @author zhao
 * @Date 2020-06-24 11:47
 */
public class BackstageFunctionConverter {

    /**
     * model转dto
     *
     * @param model
     * @return
     */
    public static BackstageFunctionDto model2Dto(BackstageFunction model) {
        if (model == null) {
            return null;
        }
        BackstageFunctionDto dto = new BackstageFunctionDto();
        dto.setId(model.getId());
        dto.setParentId(model.getParentId());
        dto.setName(model.getName());
        dto.setCode(model.getCode());
        dto.setType(model.getType());
        return dto;
    }


    /**
     * modelList转dtoList
     *
     * @param modelList
     * @return
     */
    public static List<BackstageFunctionDto> modelList2DtoList(List<BackstageFunction> modelList) {
        if (CollectionUtil.isEmpty(modelList)) {
            return Collections.emptyList();
        }
        return Lists.newArrayList(Iterators.transform(modelList.iterator(), BackstageFunctionConverter::model2Dto));
    }

    public static List<AuthPageResp> modelListToAuthPageRespList(List<BackstageFunction> mList) {
        if (CollectionUtil.isEmpty(mList)) {
            return Collections.emptyList();
        }
        return Lists.newArrayList(Iterators.transform(mList.iterator(), BackstageFunctionConverter::modelToAuthPageResp));
    }

    /**
     * model转选择dto
     *
     * @param model
     * @return
     */
    public static AuthPageResp modelToAuthPageResp(BackstageFunction model) {

        if (model == null) {
            return null;
        }
        AuthPageResp dto = new AuthPageResp();
        dto.setParentId(model.getParentId());
        dto.setName(model.getName());
        dto.setId(model.getId());
        dto.setCode(model.getCode());
        dto.setType(model.getType());
        dto.setPlatform(model.getPlatform());
        return dto;
    }


}