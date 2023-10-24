package com.rent.service.order;

import com.rent.common.dto.order.OrderPricesDto;
import com.rent.common.dto.order.UserOrderReletSubmitRequest;
import com.rent.common.dto.product.ConfirmOrderProductDto;
import com.rent.common.dto.product.ShopAdditionalServicesDto;
import com.rent.common.dto.product.ShopDto;
import com.rent.common.dto.user.UserAddressDto;
import com.rent.common.dto.user.UserCertificationDto;
import com.rent.model.order.UserOrders;

import java.util.List;

/**
 * 订单提交核心
 *
 * @author zhangqing@yunrong.cn
 * @version V2.1
 * @since 2.1.0 2020-6-15 18:19
 */
public interface OrderSubmitCore {

    /**
     * 保存订单相关数据
     *
     * @param shopDto 店铺id
     * @param snapshotsId 商品快照id
     * @param additionalServicesList 增值服务列表
     * @param userAddress 用户地址信息
     * @param orderPricesDto 订单金额信息
     * @param userCertificationDto 用户身份信息
     */
    void saveOrderData(ShopDto shopDto,
                       Integer snapshotsId,
                       List<ShopAdditionalServicesDto> additionalServicesList,
                       UserAddressDto userAddress,
                       OrderPricesDto orderPricesDto,
                       UserCertificationDto userCertificationDto,
                       String orderId,
                       int duration,
                       String productId,
                       Long skuId,
                       String remark,
                       String uid,
                       String nsfLevel,
                       ConfirmOrderProductDto productDto
    );

    /**
     * 保存续租订单相关数据
     *  @param shopDto 店铺id
     * @param snapshotsId 商品快照id
     * @param additionalServicesDtoList 增值服务列表
     * @param request 提交订单入参
     * @param orderPricesDto 订单金额信息
     * @param userCertificationDto 用户身份信息
     * @param originalUserOrders 原订单信息
     */
    void saveReletOrderData(ShopDto shopDto, Integer snapshotsId,
        List<ShopAdditionalServicesDto> additionalServicesDtoList, UserOrderReletSubmitRequest request,
        OrderPricesDto orderPricesDto, UserCertificationDto userCertificationDto, UserOrders originalUserOrders);
}
