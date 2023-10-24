        
package com.rent.common.converter.order;

import com.rent.common.dto.order.OrderAuditRecordDto;
import com.rent.model.order.OrderAuditRecord;


/**
 * 订单审核记录表Service
 *
 * @author xiaoyao
 * @Date 2020-10-22 10:29
 */
public class OrderAuditRecordConverter {

    /**
     * model转dto
     * @param model
     * @return
     */
    public static OrderAuditRecordDto model2Dto(OrderAuditRecord model) {
        if (model == null) {
            return null;
        }
        OrderAuditRecordDto dto = new OrderAuditRecordDto();
        dto.setId(model.getId());
        dto.setOrderId(model.getOrderId());
        dto.setApproveUid(model.getApproveUid());
        dto.setApproveUserName(model.getApproveUserName());
        dto.setApproveTime(model.getApproveTime());
        dto.setApproveStatus(model.getApproveStatus());
        dto.setRefuseType(model.getRefuseType());
        dto.setRemark(model.getRemark());
        dto.setCreateTime(model.getCreateTime());
        dto.setUpdateTime(model.getUpdateTime());
        return dto;
    }

}