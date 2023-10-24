        
package com.rent.common.converter.user;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.rent.common.dto.user.ConfigDto;
import com.rent.common.dto.user.ConfigReqDto;
import com.rent.model.user.Config;

import java.util.List;


/**
 * 配置信息Service
 *
 * @author zhao
 * @Date 2020-11-11 16:52
 */
public class ConfigConverter {

    /**
     * model转dto
     *
     * @param model
     * @return
     */
    public static ConfigDto model2Dto(Config model) {
        if (model == null) {
            return null;
        }
        ConfigDto dto = new ConfigDto();
        dto.setId(model.getId());
        dto.setCode(model.getCode());
        dto.setName(model.getName());
        dto.setValue(model.getValue());
        dto.setCreateTime(model.getCreateTime());
        dto.setUpdateTime(model.getUpdateTime());
        dto.setDeleteTime(model.getDeleteTime());
        return dto;
    }

    /**
     * dto转do
     *
     * @param dto
     * @return
     */
    public static Config dto2Model(ConfigDto dto) {
        if (dto == null) {
            return null;
        }
        Config model = new Config();
        model.setId(dto.getId());
        model.setCode(dto.getCode());
        model.setValue(dto.getValue());
        model.setName(dto.getName());
        model.setCreateTime(dto.getCreateTime());
        model.setUpdateTime(dto.getUpdateTime());
        model.setDeleteTime(dto.getDeleteTime());
        return model;
    }

    /**
     * modelList转dtoList
     *
     * @param modelList
     * @return
     */
    public static List<ConfigDto> modelList2DtoList(List<Config> modelList) {
        if (CollectionUtil.isEmpty(modelList)) {
            return null;
        }
        return Lists.newArrayList(Iterators.transform(modelList.iterator(), ConfigConverter::model2Dto));
    }

    /**
     * dtoList转doList
     *
     * @param dtoList
     * @return
     */
    public static List<Config> dtoList2ModelList(List<ConfigDto> dtoList) {
        if (CollectionUtil.isEmpty(dtoList)) {
            return null;
        }
        return Lists.newArrayList(Iterators.transform(dtoList.iterator(), ConfigConverter::dto2Model));
    }

    /**
     * dto转model
     *
     * @param dto
     * @return
     */
    public static Config reqDto2Model(ConfigReqDto dto) {
        if (dto == null) {
            return null;
        }
        Config model = new Config();
            model.setId(dto.getId());
            model.setValue(dto.getValue());
        return model;
    }


}