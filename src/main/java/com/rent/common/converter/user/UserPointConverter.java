        
package com.rent.common.converter.user;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.rent.model.order.UserPointDto;
import com.rent.model.user.UserPoint;

import java.util.Collections;
import java.util.List;


/**
 * 平台来源渠道Service
 *
 * @author zhao
 * @Date 2020-06-28 14:31
 */
public class UserPointConverter {

    /**
     * model转dto
     *
     * @param model
     * @return
     */
    public static UserPointDto model2Dto(UserPoint model) {
        if (model == null) {
            return null;
        }
        UserPointDto dto = new UserPointDto();
        dto.setPosition(model.getPosition());
        dto.setAction(model.getAction());
        dto.setCreateTime(model.getCreateTime());
        dto.setUid(model.getUid());
        dto.setChannelId(model.getChannelId());
        return dto;
    }


    /**
     * modelList转dtoList
     *
     * @param modelList
     * @return
     */
    public static List<UserPointDto> modelList2DtoList(List<UserPoint> modelList) {
        if (CollectionUtil.isEmpty(modelList)) {
            return Collections.emptyList();
        }
        return Lists.newArrayList(Iterators.transform(modelList.iterator(), UserPointConverter::model2Dto));
    }


}