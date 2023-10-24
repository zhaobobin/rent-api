package com.rent.common.converter.order;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.rent.common.dto.order.AccountPeriodDto;
import com.rent.model.order.AccountPeriod;

import java.util.ArrayList;
import java.util.List;

/**
 * 账单表Service
 * @author xiaoyao
 */
public class AccountPeriodConverter {

    /**
     * model转dto
     *
     * @param model
     * @return
     */
    public static AccountPeriodDto model2Dto(AccountPeriod model) {
        if (model == null) {
            return null;
        }
        AccountPeriodDto dto = new AccountPeriodDto();
        dto.setId(model.getId());
        dto.setShopName(model.getShopName());
        dto.setStatus(model.getStatus());
        dto.setTotalSettleAmount(model.getTotalSettleAmount());
        dto.setTotalBrokerage(model.getTotalBrokerage());
        dto.setSettleDate(model.getSettleDate());
        return dto;
    }
    /**
     * modelList转dtoList
     *
     * @param modelList
     * @return
     */
    public static List<AccountPeriodDto> modelList2DtoList(List<AccountPeriod> modelList) {
        if (CollectionUtil.isEmpty(modelList)) {
            return new ArrayList<>();
        }
        return Lists.newArrayList(Iterators.transform(modelList.iterator(), AccountPeriodConverter::model2Dto));
    }
}