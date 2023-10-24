        
package com.rent.service.order;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.dto.order.response.EnableApplyFeeBillTicketResp;
import com.rent.common.dto.order.response.FeeBillTicketPageResp;
import com.rent.common.dto.order.resquest.FeeBillTicketPageReq;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author zhaowenchao
 */
public interface FeeBillTicketService {

    /**
     * 查看可以申请开票的费用记录id
     * @param shopId
     * @return
     */
    EnableApplyFeeBillTicketResp getEnableApplyFeeBill(String shopId);

    /**
     * 申请开票
     * @param idList
     * @param amount
     */
    void apply(List<Long> idList, BigDecimal amount,String shopId);

    /**
     * 分页查看发票申请
     * @param req
     * @return
     */
    Page<FeeBillTicketPageResp> page(FeeBillTicketPageReq req);

    /**
     * 确认开票
     * @param id
     */
    void confirm(Long id);

}