        
package com.rent.dao.order;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.dto.order.response.FeeBillTicketPageResp;
import com.rent.common.dto.order.resquest.FeeBillTicketPageReq;
import com.rent.config.mybatis.IBaseDao;
import com.rent.model.order.FeeBillTicket;

/**
 * FeeBillDao
 *
 * @author zhao
 * @Date 2020-08-11 09:59
 */
public interface FeeBillTicketDao extends IBaseDao<FeeBillTicket> {


    /**
     * 分页查看发票申请
     * @param req
     * @return
     */
    Page<FeeBillTicketPageResp> pageTicket(FeeBillTicketPageReq req);
}
