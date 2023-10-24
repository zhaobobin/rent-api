        
package com.rent.mapper.marketing;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.dto.backstage.request.QueryOrderComplaintsPageReqVo;
import com.rent.common.dto.backstage.resp.OrderComplaintsPageRespVo;
import com.rent.model.marketing.OrderComplaints;


public interface OrderComplaintsMapper extends BaseMapper<OrderComplaints>{

    /**
     *
     * @param request
     * @return
     */
    Page<OrderComplaintsPageRespVo> queryOrderComplaintsPage(IPage<OrderComplaints> page, QueryOrderComplaintsPageReqVo request);
}