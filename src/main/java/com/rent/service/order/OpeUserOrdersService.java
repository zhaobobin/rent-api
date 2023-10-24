package com.rent.service.order;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.dto.backstage.*;
import com.rent.common.dto.order.*;
import com.rent.common.dto.order.response.BackstageBuyOutOrderDetailDto;
import com.rent.common.dto.order.response.BuyOutOriginalOrderDto;
import com.rent.common.dto.order.resquest.CloseOrderByConditionRequest;
import com.rent.common.dto.order.resquest.OrderRentChangeReqDto;
import com.rent.common.dto.order.resquest.QueryBuyOutOrdersRequest;
import com.rent.common.dto.order.resquest.TelephoneOrderAuditRequest;
import com.rent.model.order.UserOrders;

/**
 * @author xiaoyao
 * @version V1.0
 * @date 2020-7-9 下午 2:08:10
 * @since 1.0
 */
public interface OpeUserOrdersService {

    /**
     * 查询运营订单
     *
     * @param model 查询条件
     * @return 返回值
     */
    Page<BackstageUserOrderDto> queryOpeOrderByCondition(OrderByConditionRequest model);

    /**
     * 查询订单详情
     * @param orderId 订单id
     * @return
     */
    BackstageUserOrderDetailDto queryOpeUserOrderDetail(String orderId);

    /**
     * 查询待关闭订单列表
     * @param request 查询条件
     * @return 结果
     */
    Page<OpeUserOrderClosingDto> queryPendingOrderClosureList(CloseOrderByConditionRequest request);

    /**
     * 运营中心买断订单查询
     *
     * @param model
     * @return
     */
    Page<OpeBuyOutOrdersDto> queryOpeBuyOutOrderList(QueryBuyOutOrdersRequest model);

    /**
     * 查询买断订单详情
     * @param buyOutOrderId 买断订单号
     * @return
     */
    BackstageBuyOutOrderDetailDto queryOpeBuyOutOrderDetail(String buyOutOrderId);

    /**
     * 修改订单租金
     * @param request
     * @return
     */
    Boolean orderRentChange(OrderRentChangeReqDto request);


    /**
     * 电审审核
     * @param request
     */
    void telephoneAuditOrder(TelephoneOrderAuditRequest request);

    /**
     * 首页订单统计
     * @param request
     * @return
     */
    OpeOrderStatisticsDto opeOrderStatistics(OpeOrderStatisticsRequest request);

    /**
     * 组装租赁订单相关信息
     * @param userOrders
     * @return
     */
    OpeUserOrderInfoDto assemblyOrderInfo(UserOrders userOrders);

    /**
     * 组装租赁店铺相关信息
     * @param userOrders
     * @return
     */
    OpeShopInfoDto assemblyOrderShopInfo(UserOrders userOrders);
//
    /**
     * 组装租赁店铺相关信息
     * @param userOrders
     * @return
     */
    OpeOrderProductInfo assemblyOrderProductInfo(UserOrders userOrders);

    /**
     * 查询订单账单信息
     * @param orderId 订单号
     * @return
     */
    BackstageOrderStagesDto queryOrderStagesDetail(String orderId);
//
    /**
     * 手动发起代扣
     *
     * @param request
     * @return
     */
    StageOrderWithholdResponse stageOrderWithhold(StageOrderWithholdRequest request);

    /**
     * 修改订单总租金
     * @param request
     * @return
     */

    StageOrderWithholdResponse stageOrderWithholdByAlipay(StageOrderWithholdRequest request);

    StageOrderWithholdResponse stageOrderWithholdByBank(StageOrderWithholdRequest request);

//    StageOrderWithholdResponse suningStageOrderWithholdCallback(StageOrderWithholdRequest request);


    BuyOutOriginalOrderDto assemblyBuyOutOriginalOrderDto(UserOrders userOrders);
}
