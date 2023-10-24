        
package com.rent.mapper.order;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rent.common.dto.order.ChannelOrdersExportDto;
import com.rent.common.dto.order.resquest.ChannelUserOrdersReqDto;
import com.rent.model.order.ChannelUserOrders;

import java.util.List;

/**
 * SplitBillDao
 *
 * @author zhao
 * @Date 2020-08-11 09:59
 */
public interface ChannelUserOrdersMapper extends BaseMapper<ChannelUserOrders>{

    /**
     *
     * @param request
     * @return
     */
    List<ChannelOrdersExportDto> channelRentOrder(ChannelUserOrdersReqDto request);
}