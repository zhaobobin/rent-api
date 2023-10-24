        
package com.rent.dao.marketing;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.dto.backstage.request.QueryOrderComplaintsPageReqVo;
import com.rent.common.dto.backstage.resp.OrderComplaintsPageRespVo;
import com.rent.config.mybatis.IBaseDao;
import com.rent.model.marketing.OrderComplaints;


public interface OrderComplaintsDao extends IBaseDao<OrderComplaints> {

    /**
     *
     * @param request
     * @return
     */
    Page<OrderComplaintsPageRespVo> queryOrderComplaintsPage(QueryOrderComplaintsPageReqVo request);

}
