package com.rent.mapper.order;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.dto.backstage.ExportRentOrderReq;
import com.rent.common.dto.export.OrderExportDto;
import com.rent.common.dto.order.BackstageUserOrderDto;
import com.rent.common.dto.order.OrderByConditionRequest;
import com.rent.model.order.UserOrders;

import java.util.List;

/**
 * UserOrdersDao
 *
 * @author xiaoyao
 * @Date 2020-06-03 16:49
 */
public interface UserOrdersMapper extends BaseMapper<UserOrders> {


    /**
     *
     * @param request
     * @return
     */
    List<OrderExportDto> getRentOrderExport(ExportRentOrderReq request);

    /**
     *
     * @param request
     * @return
     */
    List<OrderExportDto> getOverdueOrderExport(ExportRentOrderReq request);

    /**
     *
     * @param request
     * @return
     */
    List<OrderExportDto> getNotGiveBackOrderExport(ExportRentOrderReq request);

    /**
     *
     * @param request
     * @return
     */
    Page<BackstageUserOrderDto> queryOpeOrderByCondition(IPage<UserOrders> page, OrderByConditionRequest request);

}