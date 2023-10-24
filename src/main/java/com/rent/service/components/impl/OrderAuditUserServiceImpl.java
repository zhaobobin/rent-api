package com.rent.service.components.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rent.common.enums.user.EnumBackstageUserStatus;
import com.rent.dao.order.OrderAuditUserDao;
import com.rent.dao.user.AuditUserDao;
import com.rent.model.order.OrderAuditUser;
import com.rent.model.user.AuditUser;
import com.rent.service.components.OrderAuditUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderAuditUserServiceImpl implements OrderAuditUserService {
    private final AuditUserDao auditUserDao;
    private final OrderAuditUserDao orderAuditUserDao;

    @Override
    public OrderAuditUser selectAuditUser(String shopId, String orderId) {
        List<AuditUser> auditUsers = auditUserDao.listShopAuditUser(shopId, EnumBackstageUserStatus.VALID);
        if (CollectionUtils.isEmpty(auditUsers)) {
            return null;
        }
        // TODO 随机选择
        int index = new Random().nextInt(auditUsers.size());
        AuditUser auditUser = auditUsers.get(index);
        OrderAuditUser orderAuditUser = new OrderAuditUser();
        orderAuditUser.setBackstageUserId(auditUser.getBackstageUserId());
        orderAuditUser.setType(auditUser.getType());
        orderAuditUser.setShopId(shopId);
        orderAuditUser.setCreateTime(new Date());
        orderAuditUser.setUpdateTime(new Date());
        orderAuditUser.setQrcodeUrl(auditUser.getQrcodeUrl());
        orderAuditUser.setOrderId(orderId);
        orderAuditUserDao.saveOrUpdate(orderAuditUser, new QueryWrapper<>(OrderAuditUser.builder()
                .orderId(orderId).build()));
        return orderAuditUser;
    }
}
