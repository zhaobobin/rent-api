package com.rent.common.converter.order;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.rent.common.dto.backstage.OrderByStagesForValetDto;
import com.rent.common.dto.components.dto.RepayPlanInfoDto;
import com.rent.common.dto.order.OrderByStagesDto;
import com.rent.common.util.StringUtil;
import com.rent.model.order.OrderByStages;
import com.rent.util.DateUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 用户订单分期Service
 *
 * @author xiaoyao
 * @Date 2020-06-11 17:25
 */
public class OrderByStagesConverter {

    /**
     * model转dto
     *
     * @param model
     * @return
     */
    public static OrderByStagesDto model2Dto(OrderByStages model) {
        if (model == null) {
            return null;
        }
        OrderByStagesDto dto = new OrderByStagesDto();
        dto.setId(model.getId().toString());
        dto.setCreateTime(model.getCreateTime());
        dto.setUpdateTime(model.getUpdateTime());
        dto.setDeleteTime(model.getDeleteTime());
        dto.setLeaseTerm(model.getLeaseTerm());
        dto.setOrderId(model.getOrderId());
        dto.setTotalPeriods(model.getTotalPeriods());
        dto.setCurrentPeriods(model.getCurrentPeriods());
        dto.setTotalRent(model.getTotalRent());
        dto.setCurrentPeriodsRent(model.getCurrentPeriodsRent());
        dto.setStatus(model.getStatus());
        dto.setOverdueDays(model.getOverdueDays());
        dto.setRepaymentDate(model.getRepaymentDate());
        dto.setStatementDate(model.getStatementDate());
        dto.setOutTransNo(model.getOutTradeNo());
        dto.setRefundTransactionNumber(model.getRefundTransactionNumber());
        dto.setShopId(model.getShopId());
        dto.setChannelId(model.getChannelId());
        dto.setSplitBillTime(model.getSplitBillTime());
        dto.setDeductionTimes(Optional.ofNullable(model.getDeductionTimes()).orElse(0));
        dto.setPaymentMethod(model.getPaymentMethod());
        return dto;
    }

    /**
     * dto转do
     *
     * @param dto
     * @return
     */
    public static OrderByStages dto2Model(OrderByStagesDto dto) {
        if (dto == null) {
            return null;
        }
        OrderByStages model = new OrderByStages();
        if(StringUtil.isNotEmpty(dto.getId())){
            model.setId(Long.parseLong(dto.getId()));
        }
        model.setCreateTime(dto.getCreateTime());
        model.setUpdateTime(dto.getUpdateTime());
        model.setDeleteTime(dto.getDeleteTime());
        model.setLeaseTerm(dto.getLeaseTerm());
        model.setOrderId(dto.getOrderId());
        model.setTotalPeriods(dto.getTotalPeriods());
        model.setCurrentPeriods(dto.getCurrentPeriods());
        model.setTotalRent(dto.getTotalRent());
        model.setCurrentPeriodsRent(dto.getCurrentPeriodsRent());
        model.setStatus(dto.getStatus());
        model.setOverdueDays(dto.getOverdueDays());
        model.setRepaymentDate(dto.getRepaymentDate());
        model.setStatementDate(dto.getStatementDate());
        model.setRefundTransactionNumber(dto.getRefundTransactionNumber());
        model.setShopId(dto.getShopId());
        model.setChannelId(dto.getChannelId());
        model.setSplitBillTime(dto.getSplitBillTime());
        return model;
    }

    /**
     * modelList转dtoList
     *
     * @param modelList
     * @return
     */
    public static List<OrderByStagesDto> modelList2DtoList(List<OrderByStages> modelList) {
        if (CollectionUtil.isEmpty(modelList)) {
            return new ArrayList<>();
        }
        return Lists.newArrayList(Iterators.transform(modelList.iterator(), OrderByStagesConverter::model2Dto));
    }

    /**
     * dtoList转doList
     *
     * @param dtoList
     * @return
     */
    public static List<OrderByStages> dtoList2ModelList(List<OrderByStagesDto> dtoList) {
        if (CollectionUtil.isEmpty(dtoList)) {
            return null;
        }
        return Lists.newArrayList(Iterators.transform(dtoList.iterator(), OrderByStagesConverter::dto2Model));
    }


    public static OrderByStagesForValetDto model2DtoForValet(OrderByStages model) {
        if (model == null) {
            return null;
        }
        OrderByStagesForValetDto dto = new OrderByStagesForValetDto();
        dto.setUpdateTime(model.getUpdateTime());
        dto.setCurrentPeriodsRent(model.getCurrentPeriodsRent());
        dto.setUserName(model.getTradeNo());
        dto.setProof(model.getOutTradeNo());
        return dto;
    }




    public static RepayPlanInfoDto model2Plan(OrderByStages model) {
        if (model == null) {
            return null;
        }
        RepayPlanInfoDto dto = new RepayPlanInfoDto();
        dto.setPeriod(model.getCurrentPeriods());
        dto.setNeedRepayAmount(model.getCurrentPeriodsRent());
        dto.setNeedRepayCapital(model.getCurrentPeriodsRent());
        if(model.getCurrentPeriods()==1){
            dto.setRepayDate(DateUtil.dateStr4(new Date()));
        }else {
            dto.setRepayDate(DateUtil.dateStr4(model.getStatementDate()));
        }
        return dto;
    }

    public static List<RepayPlanInfoDto> modelList2PlanList(List<OrderByStages> modelList) {
        if (CollectionUtil.isEmpty(modelList)) {
            return new ArrayList<>();
        }
        return Lists.newArrayList(Iterators.transform(modelList.iterator(), OrderByStagesConverter::model2Plan));
    }
}