package com.rent.common.converter.order;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.rent.common.dto.backstage.OrderReportDto;
import com.rent.model.order.OrderReport;
import com.rent.util.DateUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 订单报表统计Service
 *
 * @author xiaoyao
 * @Date 2020-08-11 16:17
 */
public class OrderReportConverter {

    /**
     * model转dto
     *
     * @param model
     * @return
     */
    public static OrderReportDto model2Dto(OrderReport model) {
        if (model == null) {
            return null;
        }
        OrderReportDto dto = new OrderReportDto();
        dto.setStatisticsDate(DateUtil.string2Date(model.getStatisticsDate()
            .toString()));
        dto.setTotalOrderCount(model.getTotalOrderCount());
        dto.setSuccessOrderCount(model.getSuccessOrderCount());
        dto.setSuccessOrderRent(model.getSuccessOrderRent());
        return dto;
    }

    /**
     * modelList转dtoList
     *
     * @param modelList
     * @return
     */
    public static List<OrderReportDto> modelList2DtoList(List<OrderReport> modelList) {
        if (CollectionUtil.isEmpty(modelList)) {
            return new ArrayList<>();
        }
        return Lists.newArrayList(Iterators.transform(modelList.iterator(), OrderReportConverter::model2Dto));
    }


}