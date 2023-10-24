
package com.rent.service.marketing;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.dto.api.request.SubmitOrderComplaintReqVo;
import com.rent.common.dto.api.resp.OrderComplaintsResponse;
import com.rent.common.dto.backstage.request.ModifyOrderComplaintsReqVo;
import com.rent.common.dto.backstage.request.QueryOrderComplaintsPageReqVo;
import com.rent.common.dto.backstage.resp.OrderComplaintsDetailRespVo;
import com.rent.common.dto.backstage.resp.OrderComplaintsPageRespVo;
import com.rent.common.dto.common.resp.GetOrderComplaintTypeRespVo;

import java.util.List;

public interface OrderComplaintsService {

    /**
     * 用户提交订单投诉
     * @param request
     * @return
     */
    Boolean addOrderComplaints(SubmitOrderComplaintReqVo request);

    /**
     * 录入小程序订单投诉处理结果
     * @param request
     * @return
     */
    Boolean modifyOrderComplaints(ModifyOrderComplaintsReqVo request);

    /**
     * 获取小程序订单投诉详情
     * @return
     */
    OrderComplaintsDetailRespVo queryOrderComplaintsDetail(Long id);

    /**
     * 分页获取小程序订单投诉
     * @param request
     * @return
     */
    Page<OrderComplaintsPageRespVo> queryOrderComplaintsPage(QueryOrderComplaintsPageReqVo request);

    /**
     * 获取订单投诉类型集合
     * @return
     */
    List<GetOrderComplaintTypeRespVo> getOrderComplaintsTypes();


    /**
     * 获取用户可投诉的订单信息
     * @param uid
     * @param channelId
     * @return
     */
    List<OrderComplaintsResponse> getOrderAndShopName(String uid, String channelId);
}