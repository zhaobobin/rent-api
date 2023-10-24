        
package com.rent.dao.order;


import com.rent.common.dto.order.ChannelOrdersExportDto;
import com.rent.common.dto.order.resquest.ChannelUserOrdersReqDto;
import com.rent.config.mybatis.IBaseDao;
import com.rent.model.order.ChannelUserOrders;

import java.util.List;

/**
 * ChannelUserOrders
 *
 * @author zhao
 * @Date 2020-08-11 09:59
 */
public interface ChannelUserOrdersDao extends IBaseDao<ChannelUserOrders> {


    /**
     * 根据订单编号查询记录
     * @param orderId
     * @return
     */
    ChannelUserOrders getByOrderId(String orderId);

    /**
     *
     * @param request
     * @return
     */
    List<ChannelOrdersExportDto> channelRentOrder(ChannelUserOrdersReqDto request);
}
