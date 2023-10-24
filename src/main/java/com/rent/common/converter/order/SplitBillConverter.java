        
package com.rent.common.converter.order;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.rent.common.dto.backstage.SplitBilRentlDto;
import com.rent.common.dto.order.response.SplitBillBuyOutDto;
import com.rent.model.order.SplitBill;

import java.util.ArrayList;
import java.util.List;


/**
 * 分账信息Service
 *
 * @author zhao
 * @Date 2020-08-11 09:59
 */
public class SplitBillConverter {

    /**
     * model转dto
     * @param model
     * @return
     */
    public static SplitBilRentlDto model2RentDto(SplitBill model) {
        if (model == null) {
            return null;
        }
        SplitBilRentlDto dto = new SplitBilRentlDto();
        dto.setId(model.getId());
        dto.setOrderId(model.getOrderId());
        dto.setPeriod(model.getPeriod());
        dto.setUid(model.getUid());
        dto.setSplitBillRuleId(model.getSplitBillRuleId());
        dto.setOrderNo(model.getOrderNo());
        dto.setShopId(model.getShopId());
        dto.setType(model.getType());
        dto.setIdentity(model.getIdentity());
        dto.setName(model.getName());
        dto.setScale(model.getScale());
        dto.setUserPayAmount(model.getUserPayAmount());
        dto.setToShopAmount(model.getTransAmount());
        dto.setToOpeAmount(model.getUserPayAmount().subtract(model.getTransAmount()));
        dto.setStatus(model.getStatus());
        dto.setUserPayTime(model.getUserPayTime());
        dto.setPlanPayTime(model.getPlanPayTime());
        dto.setRealPayTime(model.getRealPayTime());
        dto.setCreateTime(model.getCreateTime());
        dto.setUpdateTime(model.getUpdateTime());
        return dto;
    }

    /**
     * model转dto
     * @param model
     * @return
     */
    public static SplitBillBuyOutDto model2BuyOutDto(SplitBill model) {
        if (model == null) {
            return null;
        }
        SplitBillBuyOutDto dto = new SplitBillBuyOutDto();
        dto.setId(model.getId());
        dto.setOrderId(model.getOrderId());
        dto.setUid(model.getUid());
        dto.setSplitBillRuleId(model.getSplitBillRuleId());
        dto.setOrderNo(model.getOrderNo());
        dto.setShopId(model.getShopId());
        dto.setType(model.getType());
        dto.setIdentity(model.getIdentity());
        dto.setName(model.getName());
        dto.setScale(model.getScale());
        dto.setUserPayAmount(model.getUserPayAmount());
        dto.setToShopAmount(model.getTransAmount());
        dto.setToOpeAmount(model.getUserPayAmount().subtract(model.getTransAmount()));
        dto.setStatus(model.getStatus());
        dto.setUserPayTime(model.getUserPayTime());
        dto.setPlanPayTime(model.getPlanPayTime());
        dto.setRealPayTime(model.getRealPayTime());
        dto.setCreateTime(model.getCreateTime());
        dto.setUpdateTime(model.getUpdateTime());
        return dto;
    }


    /**
     * modelList转dtoList
     *
     * @param modelList
     * @return
     */
    public static List<SplitBilRentlDto> modelList2RentDtoList(List<SplitBill> modelList) {
        if (CollectionUtil.isEmpty(modelList)) {
            return new ArrayList<>();
        }
        return Lists.newArrayList(Iterators.transform(modelList.iterator(), SplitBillConverter::model2RentDto));
    }

    /**
     * modelList转dtoList
     *
     * @param modelList
     * @return
     */
    public static List<SplitBillBuyOutDto> modelList2BuyOutDtoList(List<SplitBill> modelList) {
        if (CollectionUtil.isEmpty(modelList)) {
            return new ArrayList<>();
        }
        return Lists.newArrayList(Iterators.transform(modelList.iterator(), SplitBillConverter::model2BuyOutDto));
    }

}