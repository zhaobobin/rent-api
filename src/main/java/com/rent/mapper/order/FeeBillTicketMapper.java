        
package com.rent.mapper.order;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.dto.order.response.FeeBillTicketPageResp;
import com.rent.common.dto.order.resquest.FeeBillTicketPageReq;
import com.rent.model.order.FeeBillTicket;


public interface FeeBillTicketMapper extends BaseMapper<FeeBillTicket>{

    /**
     * 分页查看发票申请
     * @param page
     * @param request
     * @return
     */
    Page<FeeBillTicketPageResp> pageTicket(IPage<FeeBillTicket> page, FeeBillTicketPageReq request);
}