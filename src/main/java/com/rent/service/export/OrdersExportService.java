package com.rent.service.export;

import com.rent.common.dto.backstage.ExportRentOrderReq;
import com.rent.common.dto.export.OrderExportDto;
import com.rent.common.dto.order.ChannelOrdersExportDto;
import com.rent.common.dto.order.resquest.ChannelUserOrdersReqDto;

import java.util.List;

/**
 * @author zhaowenchao
 */
public interface OrdersExportService {

    /**
     * 租赁订单导出-商家和运营公用一个接口
     * @param request
     * @return
     */
    List<OrderExportDto> rentOrder(ExportRentOrderReq request);

    /**
     * 逾期订单导出-商家和运营公用一个接口
     * @param request
     * @return
     */
    List<OrderExportDto> overdueOrder(ExportRentOrderReq request);

    /**
     * 到期未归还导出-商家和运营公用一个接口
     * @param request
     * @return
     */
    List<OrderExportDto> notGiveBack(ExportRentOrderReq request);

    /**
     *
     * @param request
     * @return
     */
    List<ChannelOrdersExportDto> channelRentOrder(ChannelUserOrdersReqDto request);
}
