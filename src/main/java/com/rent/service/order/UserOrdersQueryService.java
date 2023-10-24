package com.rent.service.order;


import com.rent.common.dto.components.dto.OrderContractDto;
import com.rent.common.dto.components.dto.UserOrderAgreementRequest;
import com.rent.common.dto.order.HotRentDto;
import com.rent.common.dto.order.OrderDetailResponse;
import com.rent.common.dto.order.OrderStatusCountDto;
import com.rent.common.dto.order.UserOrderPaySignDto;
import com.rent.common.dto.order.response.DepositOrderPageDto;
import com.rent.common.dto.order.response.DepositOrderRecordDto;
import com.rent.common.dto.order.response.OrderBackAddressResponse;
import com.rent.common.dto.product.OrderProductDetailDto;

import java.util.List;
import java.util.Map;

/**
 * @author xiaoyao
 * @version V1.0
 * @date 2020-7-8 上午 10:32:46
 * @since 1.0
 */
public interface UserOrdersQueryService {


    /**
     * 简版小程序统计订单数量
     * @param uid
     * @return
     */
    OrderStatusCountDto liteStatusCount(String uid);

    /**
     * 获取到最近30天所有的订单
     * @return
     */
    List<HotRentDto> hotRentOrder();

    /**
     * 订单协议内容
     * @param request
     * @return
     */
    OrderContractDto agreementV2(UserOrderAgreementRequest request);

    /**
     * 检验用户是否下过订单
     * @param uid uid
     * @return
     */
    Boolean checkUserHasPlaceOrder(String uid);

    /**
     * 查询订单详情
     *
     * @param orderId 订单编号
     * @return
     */
    OrderDetailResponse selectUserOrderDetail(String orderId);

    /**
     *
     * @param orderIdList
     * @return
     */
    Map<String, OrderProductDetailDto> selectOrderProductDetail(List<String> orderIdList);

    /**
     * 查询订单归还地址
     * @param orderId 订单id
     * @return
     */
    List<OrderBackAddressResponse> queryOrderGiveBackAddress(String orderId);


    /**
     * 获取用户押金订单
     * @param uid
     * @return
     */
    DepositOrderPageDto depositOrderList(String uid);

    /**
     * 查询用户押金充值提现记录
     * @param uid
     * @return
     */
    DepositOrderRecordDto queryDepositOrderList(String uid);


    /**
     * 查询订单免押及签约信息
     * @param uid
     * @return
     */
    UserOrderPaySignDto queryOrderPaySignInfo(String uid);
}
