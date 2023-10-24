package com.rent.common.converter.order;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.rent.common.dto.order.FeeBillDto;
import com.rent.common.enums.order.EnumFeeBillType;
import com.rent.common.enums.order.EnumOderFeeBillInvoiceStatus;
import com.rent.common.enums.order.EnumOrderStatus;
import com.rent.common.enums.order.EnumSettleStatus;
import com.rent.model.order.FeeBill;
import com.rent.model.order.SplitBill;
import com.rent.model.order.UserOrders;
import com.rent.util.LoginUserUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author zhaowenchao
 */
public class FeeBillConverter {

    public static FeeBill generateAssessmentFeeBill(UserOrders userOrders,BigDecimal unitAmount) {
        Date now = new Date();
        FeeBill feeBill = new FeeBill();
        feeBill.setOrderId(userOrders.getOrderId());
        feeBill.setUid(userOrders.getUid());
        feeBill.setAliUid(userOrders.getUid());
        feeBill.setAmount(unitAmount);
        feeBill.setShopId(userOrders.getShopId());
        feeBill.setStatus(EnumSettleStatus.WAITING_SETTLEMENT);
        feeBill.setType(EnumFeeBillType.ASSESSMENT);
        feeBill.setChannelId(userOrders.getChannelId());
        feeBill.setChannelId(userOrders.getChannelId());
        feeBill.setOrderTime(userOrders.getCreateTime());
        feeBill.setOrderStatus(userOrders.getStatus());
        feeBill.setTicketStatus(EnumOderFeeBillInvoiceStatus.UN_TICKET);
        feeBill.setCreateTime(now);
        feeBill.setUpdateTime(now);
        return feeBill;
    }

    public static FeeBill generateReportFeeBill(UserOrders userOrders,BigDecimal unitAmount) {
        Date now = new Date();
        FeeBill feeBill = new FeeBill();
        feeBill.setOrderId(userOrders.getOrderId());
        feeBill.setUid(userOrders.getUid());
        feeBill.setAliUid(userOrders.getUid());
        feeBill.setAmount(unitAmount);
        feeBill.setShopId(userOrders.getShopId());
        feeBill.setStatus(EnumSettleStatus.WAITING_SETTLEMENT);
        feeBill.setType(EnumFeeBillType.CREDIT_REPORT);
        feeBill.setChannelId(userOrders.getChannelId());
        feeBill.setOrderTime(userOrders.getCreateTime());
        feeBill.setOrderStatus(userOrders.getStatus());
        feeBill.setTicketStatus(EnumOderFeeBillInvoiceStatus.UN_TICKET);
        feeBill.setCreateTime(now);
        feeBill.setUpdateTime(now);
        feeBill.setBackstageUserId(LoginUserUtil.getLoginUser().getId().toString());
        return feeBill;
    }

    public static FeeBill generateContractFeeBill(UserOrders userOrders,BigDecimal unitAmount) {
        Date now = new Date();
        FeeBill feeBill = new FeeBill();
        feeBill.setOrderId(userOrders.getOrderId());
        feeBill.setUid(userOrders.getUid());
        feeBill.setAliUid(userOrders.getUid());
        feeBill.setAmount(unitAmount);
        feeBill.setShopId(userOrders.getShopId());
        feeBill.setStatus(EnumSettleStatus.WAITING_SETTLEMENT);
        feeBill.setType(EnumFeeBillType.CONTRACT);
        feeBill.setChannelId(userOrders.getChannelId());
        feeBill.setOrderTime(userOrders.getCreateTime());
        feeBill.setOrderStatus(userOrders.getStatus());
        feeBill.setTicketStatus(EnumOderFeeBillInvoiceStatus.UN_TICKET);
        feeBill.setCreateTime(now);
        feeBill.setUpdateTime(now);
        feeBill.setBackstageUserId(LoginUserUtil.getLoginUser().getId().toString());
        return feeBill;
    }

    public static FeeBill generateSplitBillFeeBill(SplitBill splitBill, Long accountPeriodId) {
        BigDecimal fee = splitBill.getUserPayAmount().subtract(splitBill.getTransAmount());
        if(fee.compareTo(BigDecimal.ZERO)>0){
            return null;
        }
        Date now = new Date();
        FeeBill feeBill = new FeeBill();
        feeBill.setFundFlowId(accountPeriodId);
        feeBill.setAmount(fee);
        feeBill.setOrderId(splitBill.getOrderId());
        feeBill.setUid(splitBill.getUid());
        feeBill.setAliUid(splitBill.getUid());
        feeBill.setShopId(splitBill.getShopId());
        feeBill.setStatus(EnumSettleStatus.SETTLED);
        feeBill.setType(EnumFeeBillType.SPLIT_BILL);
        feeBill.setOrderStatus(EnumOrderStatus.RENTING);
        feeBill.setOrderTime(splitBill.getUserPayTime());
        feeBill.setTicketStatus(EnumOderFeeBillInvoiceStatus.UN_TICKET);
        feeBill.setCreateTime(now);
        feeBill.setUpdateTime(now);
        return feeBill;
    }

    public static FeeBillDto modelToDto(FeeBill model) {
        if(model==null){
            return null;
        }
        FeeBillDto dto = new FeeBillDto();
        dto.setId(model.getId());
        dto.setFundFlowId(model.getFundFlowId());
        dto.setOrderId(model.getOrderId());
        dto.setUid(model.getUid());
        dto.setAliUid(model.getAliUid());
        dto.setShopId(model.getShopId());
        dto.setAmount(model.getAmount());
        dto.setStatus(model.getStatus());
        dto.setType(model.getType());
        dto.setChannelId(model.getChannelId());
        dto.setCreateTime(model.getCreateTime());
        dto.setOrderStatus(model.getOrderStatus());
        dto.setOrderTime(model.getOrderTime());
        dto.setCreateTime(model.getCreateTime());
        dto.setUpdateTime(model.getUpdateTime());
        return dto;
    }

    public static List<FeeBillDto> modelListToDtoList(List<FeeBill> models) {
        if (CollectionUtil.isEmpty(models)) {
            return new ArrayList<>();
        }
        return Lists.newArrayList(Iterators.transform(models.iterator(), FeeBillConverter::modelToDto));
    }

}