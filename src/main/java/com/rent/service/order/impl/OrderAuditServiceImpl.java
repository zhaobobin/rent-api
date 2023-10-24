package com.rent.service.order.impl;

import com.rent.common.converter.order.OrderAuditRecordConverter;
import com.rent.common.dto.bo.LoginUserBo;
import com.rent.common.dto.order.OrderAuditRecordDto;
import com.rent.common.enums.order.EnumOrderAuditLabel;
import com.rent.common.enums.order.EnumOrderAuditRefuseType;
import com.rent.common.enums.order.EnumOrderAuditStatus;
import com.rent.common.enums.user.EnumBackstageUserPlatform;
import com.rent.dao.order.OrderAuditRecordDao;
import com.rent.dao.order.UserOrdersDao;
import com.rent.model.order.OrderAuditRecord;
import com.rent.model.order.UserOrders;
import com.rent.service.order.OrderAuditService;
import com.rent.util.LoginUserUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
@Slf4j
@RequiredArgsConstructor
public class OrderAuditServiceImpl implements OrderAuditService {

    private final OrderAuditRecordDao orderAuditRecordDao;
    private final UserOrdersDao userOrdersDao;

    @Override
    public void initAuditRecord(String orderId) {
        UserOrders userOrders = userOrdersDao.selectOneByOrderId(orderId);
        OrderAuditRecord minAssignedAuditUser = null;
        if(userOrders.getAuditLabel().equals(EnumOrderAuditLabel.PLATFORM_AUDIT)){
            minAssignedAuditUser = orderAuditRecordDao.getMinAssignedAuditUser(EnumBackstageUserPlatform.OPE.getCode(),EnumBackstageUserPlatform.OPE.getCode());
        }else {
            minAssignedAuditUser = orderAuditRecordDao.getMinAssignedAuditUser(EnumBackstageUserPlatform.SHOP.getCode(),userOrders.getShopId());
        }
        Date now = new Date();
        OrderAuditRecord record = new OrderAuditRecord();
        record.setOrderId(orderId);
        record.setApproveStatus(EnumOrderAuditStatus.TO_AUDIT);
        record.setCreateTime(now);
        record.setUpdateTime(now);
        if(minAssignedAuditUser!=null){
            record.setApproveUid(minAssignedAuditUser.getApproveUid());
        }
        orderAuditRecordDao.save(record);
    }

    @Override
    public void updateAuditRecord(String orderId, EnumOrderAuditStatus orderAuditStatus, EnumOrderAuditRefuseType refuseType, String remark) {
        OrderAuditRecord auditRecord = orderAuditRecordDao.getOrderAuditRecordByOrderId(orderId);
        LoginUserBo loginUser = LoginUserUtil.getLoginUser();
        auditRecord.setApproveUid(loginUser.getId().toString());
        auditRecord.setApproveUserName(loginUser.getName());
        auditRecord.setApproveStatus(orderAuditStatus);
        auditRecord.setRefuseType(refuseType);
        auditRecord.setRemark(remark);
        auditRecord.setApproveTime(new Date());
        auditRecord.setUpdateTime(new Date());
        orderAuditRecordDao.updateById(auditRecord);
    }

    @Override
    public OrderAuditRecordDto getByOrderId(String orderId) {
        OrderAuditRecord orderAuditRecord = orderAuditRecordDao.getOrderAuditRecordByOrderId(orderId);
        return OrderAuditRecordConverter.model2Dto(orderAuditRecord);
    }

}