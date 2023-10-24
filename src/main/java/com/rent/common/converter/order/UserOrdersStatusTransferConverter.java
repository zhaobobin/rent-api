        
package com.rent.common.converter.order;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.rent.common.dto.order.UserOrdersStatusTransferDto;
import com.rent.model.order.UserOrdersStatusTranfer;

import java.util.List;


/**
 * 订单状态流转Service
 *
 * @author xiaoyao
 * @Date 2020-10-26 16:46
 */
public class UserOrdersStatusTransferConverter {

    /**
     * model转dto
     *
     * @param model
     * @return
     */
    public static UserOrdersStatusTransferDto model2Dto(UserOrdersStatusTranfer model) {
        if (model == null) {
            return null;
        }
        UserOrdersStatusTransferDto dto = new UserOrdersStatusTransferDto();
        dto.setOrderId(model.getOrderId());
        dto.setOperatorId(model.getOperatorId());
        dto.setOperatorName(model.getOperatorName());
        dto.setOldStatus(model.getOldStatus());
        dto.setNewStatus(model.getNewStatus());
        dto.setCreateTime(model.getCreateTime());
        dto.setOperate(model.getOperate());
        return dto;
    }

    /**
     * modelList转dtoList
     *
     * @param modelList
     * @return
     */
    public static List<UserOrdersStatusTransferDto> modelList2DtoList(List<UserOrdersStatusTranfer> modelList) {
        if (CollectionUtil.isEmpty(modelList)) {
            return null;
        }
        return Lists.newArrayList(Iterators.transform(modelList.iterator(), UserOrdersStatusTransferConverter::model2Dto));
    }
}