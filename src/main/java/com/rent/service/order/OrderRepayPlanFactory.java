package com.rent.service.order;



import com.rent.common.dto.marketing.OrderCouponDto;
import com.rent.common.dto.order.OrderPricesDto;
import com.rent.common.dto.product.ShopAdditionalServicesDto;
import com.rent.model.order.OrderByStages;
import com.rent.model.order.UserOrders;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * @author xiaoyao
 * @version 1.0
 * @since 1.0 2020-6-11 16:42
 */
public interface OrderRepayPlanFactory {
    /**
     * 创建还款订单
     *
     * @param duration 租期
     * @param start 租期开始时间
     * @param end 租期结束时间
     * @param deposit 押金
     * @param additionalServicesList 增值服务列表
     * @param num 数量
     * @param skuPrice sku单日租金
     * @param orderCouponDto 优惠券信息
     * @param marketPrice 市场价
     * @return 还款账单
     */
    OrderPricesDto createOrderRepayPlan(int duration, Date start, Date end, BigDecimal deposit,
                                        List<ShopAdditionalServicesDto> additionalServicesList, int num, BigDecimal skuPrice,
                                        OrderCouponDto orderCouponDto, BigDecimal marketPrice,BigDecimal retainDeductionAmount,Boolean isRelet);

    /**
     * 计算买断价格
     * @param userOrders 订单信息
     * @param orderByStagesList 账单列表
     * @param salePrice  销售价
     * @return
     */
    BigDecimal calculateBuyOutAmount(UserOrders userOrders, List<OrderByStages> orderByStagesList, BigDecimal salePrice);

    /**
     * 计算买断价格
     * @param userOrders 订单信息
     * @param orderByStagesList 账单列表
     * @param salePrice  销售价
     * @return
     */
    BigDecimal getBuyOutAmount(UserOrders userOrders, List<OrderByStages> orderByStagesList, BigDecimal salePrice);

}
