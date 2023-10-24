        
package com.rent.service.order;

import com.rent.common.dto.order.OrderAuditRecordDto;
import com.rent.common.enums.order.EnumOrderAuditRefuseType;
import com.rent.common.enums.order.EnumOrderAuditStatus;


public interface OrderAuditService {


        /**
         * 初始化保存审核信息，分配信审人员
         * @param orderId
         */
        void initAuditRecord(String orderId);

        /**
         * 审核之后更新审核记录
         * @param orderId
         * @param orderAuditStatus
         * @param refuseType
         * @param remark
         */
        void updateAuditRecord(String orderId, EnumOrderAuditStatus orderAuditStatus, EnumOrderAuditRefuseType refuseType, String remark);

        /**
         * 根据ID查询审核记录
         * @param orderId
         * @return
         */
        OrderAuditRecordDto getByOrderId(String orderId);



}