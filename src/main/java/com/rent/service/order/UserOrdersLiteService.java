package com.rent.service.order;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.dto.marketing.OrderCouponDto;
import com.rent.common.dto.order.OrderPricesDto;
import com.rent.common.dto.order.UserOrderListDto;
import com.rent.common.dto.api.resp.LiteConfirmOrderResp;
import com.rent.common.dto.api.resp.OrderSubmitResponse;
import com.rent.common.dto.api.request.LiteConfirmOrderReq;
import com.rent.common.dto.order.resquest.OrderListQueryRequest;
import com.rent.common.dto.api.request.TrailLiteRequest;
import com.rent.common.dto.api.request.UserOrderSubmitReq;
import com.rent.common.dto.product.OrderProductDetailDto;
import com.rent.common.dto.product.ShopAdditionalServicesDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author zhaowenchao
 */
public interface UserOrdersLiteService {


    /**
     * 确认订单
     *
     * @param request
     * @return
     */
    LiteConfirmOrderResp confirm(LiteConfirmOrderReq request);

    /**
     * 价格试算
     *
     * @param request
     * @return
     */
    OrderPricesDto trail(TrailLiteRequest request);

    /**
     * 提交订单
     *
     * @param request
     * @return
     */
    OrderSubmitResponse submit(UserOrderSubmitReq request);

    /**
     * 查询订单列表
     *
     * @param request
     * @return
     */
    Page<UserOrderListDto> userOrderList(OrderListQueryRequest request);

    /**
     * 查询订单商品详情
     *
     * @param orderIdList
     * @return
     */
    Map<String, OrderProductDetailDto> selectOrderProductDetail(List<String> orderIdList);

    OrderPricesDto createOrderRepayPlan(int duration, List<ShopAdditionalServicesDto> additionalServicesList,
                                        BigDecimal skuPrice, BigDecimal salePrice, Boolean isBuyOutSupported,
                                        OrderCouponDto orderCouponDto, BigDecimal marketPrice, BigDecimal depositPrice);
}