        
package com.rent.common.converter.user;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.rent.common.dto.user.UserWordHistoryDto;
import com.rent.model.user.UserWordHistory;

import java.util.Collections;
import java.util.List;


/**
 * 用户搜索记录Service
 *
 * @author zhao
 * @Date 2020-06-18 15:41
 */
public class UserWordHistoryConverter {

    /**
     * model转dto
     *
     * @param model
     * @return
     */
    public static UserWordHistoryDto model2Dto(UserWordHistory model) {
        if (model == null) {
            return null;
        }
        UserWordHistoryDto dto = new UserWordHistoryDto();
        dto.setId(model.getId());
        dto.setCreateTime(model.getCreateTime());
        dto.setWord(model.getWord());
        dto.setUid(model.getUid());
        return dto;
    }

    /**
     * dto转do
     *
     * @param dto
     * @return
     */
    public static UserWordHistory dto2Model(UserWordHistoryDto dto) {
        if (dto == null) {
            return null;
        }
        UserWordHistory model = new UserWordHistory();
        model.setId(dto.getId());
        model.setCreateTime(dto.getCreateTime());
        model.setWord(dto.getWord());
        model.setUid(dto.getUid());
        return model;
    }

    /**
     * modelList转dtoList
     *
     * @param modelList
     * @return
     */
    public static List<UserWordHistoryDto> modelList2DtoList(List<UserWordHistory> modelList) {
        if (CollectionUtil.isEmpty(modelList)) {
            return Collections.emptyList();
        }
        return Lists.newArrayList(Iterators.transform(modelList.iterator(), UserWordHistoryConverter::model2Dto));
    }
}