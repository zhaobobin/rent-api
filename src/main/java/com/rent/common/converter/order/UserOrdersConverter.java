package com.rent.common.converter.order;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.rent.common.dto.order.UserOrderReletSubmitRequest;
import com.rent.common.dto.order.UserOrdersDto;
import com.rent.common.dto.order.UserOrdersReqDto;
import com.rent.common.enums.order.EnumOrderStatus;
import com.rent.common.enums.order.EnumOrderType;
import com.rent.common.enums.order.EnumViolationStatus;
import com.rent.common.util.SequenceTool;
import com.rent.model.order.UserOrders;
import com.rent.util.AppParamUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 用户订单Service
 *
 * @author xiaoyao
 * @Date 2020-06-10 17:02
 */
public class UserOrdersConverter {

    /**
     * model转dto
     *
     * @param model
     * @return
     */
    public static UserOrdersDto model2Dto(UserOrders model) {
        if (model == null) {
            return null;
        }
        UserOrdersDto dto = new UserOrdersDto();
        dto.setId(model.getId());
        dto.setUid(model.getUid());
        dto.setCreateTime(model.getCreateTime());
        dto.setUpdateTime(model.getUpdateTime());
        dto.setDeleteTime(model.getDeleteTime());
        dto.setPaymentNo(model.getPaymentNo());
        dto.setPaymentTime(model.getPaymentTime());
        dto.setOrderId(model.getOrderId());
        dto.setUserName(model.getUserName());
        dto.setProductId(model.getProductId());
        dto.setDeliveryTime(model.getDeliveryTime());
        dto.setStatus(model.getStatus());
        dto.setCloseType(model.getCloseType());
        dto.setRentDuration(model.getRentDuration());
        dto.setExpressNo(model.getExpressNo());
        dto.setExpressId(model.getExpressId());
        dto.setRentStart(model.getRentStart());
        dto.setUnrentTime(model.getUnrentTime());
        dto.setUnrentExpressNo(model.getUnrentExpressNo());
        dto.setIsViolation(model.getIsViolation());
        dto.setUnrentExpressId(model.getUnrentExpressId());
        dto.setRemark(model.getRemark());
        dto.setShopRemark(model.getShopRemark());
        dto.setServiceRemark(model.getServiceRemark());
        dto.setGiveBackRemark(model.getGiveBackRemark());
        dto.setSettlementRemark(model.getSettlementRemark());
        dto.setOrderRemark(model.getOrderRemark());
        dto.setType(model.getType());
        dto.setShopId(model.getShopId());
        dto.setLogisticForm(model.getLogisticForm());
        dto.setSettlementTime(model.getSettlementTime());
        dto.setGiveBackOfflineShopId(model.getGiveBackOfflineShopId());
        dto.setConfirmSettlementTime(model.getConfirmSettlementTime());
        dto.setReceiveTime(model.getReceiveTime());
        dto.setCancelReason(model.getCancelReason());
        dto.setCancelTime(model.getCancelTime());
        dto.setGiveBackAddressId(model.getGiveBackAddressId());
        dto.setCloseTime(model.getCloseTime());
        dto.setReturnTime(model.getReturnTime());
        dto.setExamineStatus(model.getExamineStatus());
        dto.setAuditLabel(model.getAuditLabel());
        dto.setRequestNo(model.getRequestNo());
        dto.setChannelId(model.getChannelId());
        dto.setFaceAuthStatus(model.getFaceAuthStatus());
        return dto;
    }

    /**
     * dto转do
     *
     * @param dto
     * @return
     */
    public static UserOrders dto2Model(UserOrdersDto dto) {
        if (dto == null) {
            return null;
        }
        UserOrders model = new UserOrders();
        model.setId(dto.getId());
        model.setUid(dto.getUid());
        model.setCreateTime(dto.getCreateTime());
        model.setUpdateTime(dto.getUpdateTime());
        model.setDeleteTime(dto.getDeleteTime());
        model.setPaymentNo(dto.getPaymentNo());
        model.setPaymentTime(dto.getPaymentTime());
        model.setOrderId(dto.getOrderId());
        model.setUserName(dto.getUserName());
        model.setProductId(dto.getProductId());
        model.setDeliveryTime(dto.getDeliveryTime());
        model.setStatus(dto.getStatus());
        model.setCloseType(dto.getCloseType());
        model.setRentDuration(dto.getRentDuration());
        model.setExpressNo(dto.getExpressNo());
        model.setExpressId(dto.getExpressId());
        model.setRentStart(dto.getRentStart());
        model.setUnrentTime(dto.getUnrentTime());
        model.setUnrentExpressNo(dto.getUnrentExpressNo());
        model.setIsViolation(dto.getIsViolation());
        model.setUnrentExpressId(dto.getUnrentExpressId());
        model.setRemark(dto.getRemark());
        model.setShopRemark(dto.getShopRemark());
        model.setServiceRemark(dto.getServiceRemark());
        model.setGiveBackRemark(dto.getGiveBackRemark());
        model.setSettlementRemark(dto.getSettlementRemark());
        model.setOrderRemark(dto.getOrderRemark());
        model.setType(dto.getType());
        model.setShopId(dto.getShopId());
        model.setLogisticForm(dto.getLogisticForm());
        model.setSettlementTime(dto.getSettlementTime());
        model.setGiveBackOfflineShopId(dto.getGiveBackOfflineShopId());
        model.setConfirmSettlementTime(dto.getConfirmSettlementTime());
        model.setReceiveTime(dto.getReceiveTime());
        model.setCancelReason(dto.getCancelReason());
        model.setCancelTime(dto.getCancelTime());
        model.setGiveBackAddressId(dto.getGiveBackAddressId());
        model.setCloseTime(dto.getCloseTime());
        model.setReturnTime(dto.getReturnTime());
        model.setExamineStatus(dto.getExamineStatus());
        model.setAuditLabel(dto.getAuditLabel());
        model.setRequestNo(dto.getRequestNo());
        model.setChannelId(dto.getChannelId());
        model.setFaceAuthStatus(dto.getFaceAuthStatus());
        return model;
    }

    /**
     * modelList转dtoList
     *
     * @param modelList
     * @return
     */
    public static List<UserOrdersDto> modelList2DtoList(List<UserOrders> modelList) {
        if (CollectionUtil.isEmpty(modelList)) {
            return new ArrayList<>();
        }
        return Lists.newArrayList(Iterators.transform(modelList.iterator(), UserOrdersConverter::model2Dto));
    }

    /**
     * dtoList转doList
     *
     * @param dtoList
     * @return
     */
    public static List<UserOrders> dtoList2ModelList(List<UserOrdersDto> dtoList) {
        if (CollectionUtil.isEmpty(dtoList)) {
            return null;
        }
        return Lists.newArrayList(Iterators.transform(dtoList.iterator(), UserOrdersConverter::dto2Model));
    }

    /**
     * dto转model
     *
     * @param dto
     * @return
     */
    public static UserOrders reqDto2Model(UserOrdersReqDto dto) {
        if (dto == null) {
            return null;
        }
        UserOrders model = new UserOrders();
        model.setId(dto.getId());
        model.setUid(dto.getUid());
        model.setCreateTime(dto.getCreateTime());
        model.setUpdateTime(dto.getUpdateTime());
        model.setDeleteTime(dto.getDeleteTime());
        model.setPaymentNo(dto.getPaymentNo());
        model.setPaymentTime(dto.getPaymentTime());
        model.setOrderId(dto.getOrderId());
        model.setDeliveryTime(dto.getDeliveryTime());
        model.setStatus(dto.getStatus());
        model.setCloseType(dto.getCloseType());
        model.setRentDuration(dto.getRentDuration());
        model.setExpressNo(dto.getExpressNo());
        model.setExpressId(dto.getExpressId());
        model.setRentStart(dto.getRentStart());
        model.setUnrentTime(dto.getUnrentTime());
        model.setUnrentExpressNo(dto.getUnrentExpressNo());
        model.setIsViolation(dto.getIsViolation());
        model.setUnrentExpressId(dto.getUnrentExpressId());
        model.setRemark(dto.getRemark());
        model.setShopRemark(dto.getShopRemark());
        model.setServiceRemark(dto.getServiceRemark());
        model.setGiveBackRemark(dto.getGiveBackRemark());
        model.setSettlementRemark(dto.getSettlementRemark());
        model.setOrderRemark(dto.getOrderRemark());
        model.setType(dto.getType());
        model.setShopId(dto.getShopId());
        model.setLogisticForm(dto.getLogisticForm());
        model.setSettlementTime(dto.getSettlementTime());
        model.setGiveBackOfflineShopId(dto.getGiveBackOfflineShopId());
        model.setConfirmSettlementTime(dto.getConfirmSettlementTime());
        model.setReceiveTime(dto.getReceiveTime());
        model.setCancelReason(dto.getCancelReason());
        model.setCancelTime(dto.getCancelTime());
        model.setGiveBackAddressId(dto.getGiveBackAddressId());
        model.setCloseTime(dto.getCloseTime());
        model.setReturnTime(dto.getReturnTime());
        model.setExamineStatus(dto.getExamineStatus());
        model.setRequestNo(dto.getRequestNo());
        model.setChannelId(dto.getChannelId());
        model.setFaceAuthStatus(dto.getFaceAuthStatus());
        return model;
    }

    public static UserOrders submitDto2Model(String shopId, Date now,
                                             String orderId,
                                             int duration,
                                             String productId,
                                             String remark,
                                             String uid,
                                             String nsfLevel
                                             ) {

        UserOrders orders = new UserOrders();
        orders.setOrderId(orderId);
        orders.setRequestNo(orderId);
        orders.setRentDuration(duration);
        orders.setRemark(remark);
        orders.setShopId(shopId);
        orders.setProductId(productId);
        orders.setUid(uid);
        orders.setStatus(EnumOrderStatus.WAITING_PAYMENT);
        orders.setCreateTime(now);
        orders.setUpdateTime(now);
        orders.setIsViolation(EnumViolationStatus.NORMAL);
        orders.setType(EnumOrderType.GENERAL_ORDER);
        orders.setPaymentNo(SequenceTool.nextId());
        orders.setChannelId(AppParamUtil.getChannelId());
        orders.setNsfLevel(nsfLevel);
        return orders;
    }

    public static UserOrders submitDto2Model(String shopId, Date now, UserOrderReletSubmitRequest request) {
        if (null == request) {
            return null;
        }
        UserOrders orders = new UserOrders();
        orders.setOrderId(request.getOrderId());
        orders.setRequestNo(request.getOrderId());
        orders.setRentDuration(request.getDuration());
        orders.setRentStart(request.getStart());
        orders.setUnrentTime(request.getEnd());
        orders.setRemark(request.getRemark());
        orders.setShopId(shopId);
        orders.setPrice(request.getPrice());
        orders.setProductId(request.getProductId());
        orders.setUid(request.getUid());
        orders.setStatus(EnumOrderStatus.WAITING_PAYMENT);
        orders.setCreateTime(now);
        orders.setUpdateTime(now);
        orders.setIsViolation(EnumViolationStatus.NORMAL);
        orders.setType(EnumOrderType.RELET_ORDER);
        orders.setPaymentNo(SequenceTool.nextId());
        orders.setChannelId(AppParamUtil.getChannelId());
        orders.setOriginalOrderId(request.getOriginalOrderId());
        return orders;
    }
}