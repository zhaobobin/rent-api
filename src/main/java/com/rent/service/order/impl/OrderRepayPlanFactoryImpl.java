package com.rent.service.order.impl;

import com.google.common.collect.Lists;
import com.rent.common.constant.CouponTemplateConstant;
import com.rent.common.dto.marketing.OrderCouponDto;
import com.rent.common.dto.order.OrderByStagesDto;
import com.rent.common.dto.order.OrderPricesDto;
import com.rent.common.dto.product.ShopAdditionalServicesDto;
import com.rent.common.enums.order.EnumOrderByStagesStatus;
import com.rent.common.enums.user.EnumBackstageUserPlatform;
import com.rent.common.util.AmountUtil;
import com.rent.common.util.GsonUtil;
import com.rent.model.order.OrderByStages;
import com.rent.model.order.UserOrders;
import com.rent.service.order.OrderRepayPlanFactory;
import com.rent.util.DateUtil;
import com.rent.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author xiaoyao
 * @version V1.0
 * @since 1.0 2020-6-11 16:42
 */
@Component
@Slf4j
public class OrderRepayPlanFactoryImpl implements OrderRepayPlanFactory {

    /** 月份最大天数 */
    private static final Integer COMMON_MONTH_MAX_DAY = 21;
    private static final BigDecimal depositRatio = new BigDecimal(0.75);

    @Override
    public OrderPricesDto createOrderRepayPlan(int duration, Date start, Date end, BigDecimal deposit, List<ShopAdditionalServicesDto> additionalServicesList,
                                               int num, BigDecimal skuPrice, OrderCouponDto orderCouponDto, BigDecimal marketPrice,BigDecimal retainDeductionAmount,Boolean isRelet) {

        log.info("租期:{},开始日期:{},结束日期:{},增值服务:{},数量:{},单日租金:{},优惠券信息:{},首期减免金额:{}", duration, start, end,
                GsonUtil.objectToJsonString(additionalServicesList), num, skuPrice,
                GsonUtil.objectToJsonString(orderCouponDto),retainDeductionAmount);
        //计算增值服务费金额
        BigDecimal additionalMoney = BigDecimal.ZERO;
        BigDecimal baseServiceFee = BigDecimal.ZERO;
        for (ShopAdditionalServicesDto shopAdditionalServicesDto : additionalServicesList) {
            if(shopAdditionalServicesDto.getPrice()!=null && shopAdditionalServicesDto.getStatus().equals(1)){
                if("-1".equals(shopAdditionalServicesDto.getShopId())){
                    baseServiceFee = baseServiceFee.add(shopAdditionalServicesDto.getPrice());
                }else {
                    additionalMoney = additionalMoney.add(shopAdditionalServicesDto.getPrice());
                }
            }
        }
        //计算还款账单
        List<OrderByStagesDto> orderByStagesDtoList = this.calculateRepayDay(start, duration, isRelet);
        //填充订单金额信息
        OrderPricesDto orderPricesDto = this.fillOrderPriceInfo(num, duration, deposit, skuPrice, additionalMoney,baseServiceFee,
                orderCouponDto, orderByStagesDtoList, marketPrice, retainDeductionAmount);
        log.info("生成的还款账单明细为:{}", GsonUtil.objectToJsonString(orderPricesDto));
        return orderPricesDto;
    }

    private OrderPricesDto fillOrderPriceInfo(int num, int duration, BigDecimal deposit, BigDecimal skuPrice,
                                              BigDecimal additionalServicesAmount,BigDecimal baseServiceFee, OrderCouponDto orderCouponDto,
                                              List<OrderByStagesDto> orderByStagesDtoList,BigDecimal marketPrice,BigDecimal retainDeductionAmount) {

        OrderPricesDto orderPricesDto = new OrderPricesDto();
        /* 纯租金 */
        BigDecimal rentPrice = skuPrice.multiply(new BigDecimal(duration * num))
                .setScale(2, BigDecimal.ROUND_HALF_UP);
        /* 总租金 = 纯租金 - 优惠金额*/
        BigDecimal totalRent;
        //计算优惠券金额
        if (null != orderCouponDto && orderCouponDto.getScene()
                .equals(CouponTemplateConstant.SCENE_RENT)) {
            totalRent = rentPrice.subtract(orderCouponDto.getDiscountAmount());
            totalRent = totalRent.compareTo(BigDecimal.ZERO) <= 0 ? new BigDecimal("0.01").multiply(
                            new BigDecimal(orderByStagesDtoList.size()))
                    .setScale(2, BigDecimal.ROUND_HALF_UP) : totalRent;
            orderPricesDto.setCouponPrice(orderCouponDto.getDiscountAmount());
            if (StringUtil.isNotEmpty(orderCouponDto.getShopId()) && !EnumBackstageUserPlatform.OPE.getCode().equals(orderCouponDto.getShopId())) {
                orderPricesDto.setShopCouponPrice(orderCouponDto.getDiscountAmount());
            } else {
                orderPricesDto.setPlatformCouponPrice(orderCouponDto.getDiscountAmount());
            }
        } else {
            totalRent = rentPrice;
        }


        //先按照只有一期的计算。如果只有一期，第一期纯租金就是总租金，首期支付租金=总租金+增值服费
        /*首期租金*/
        BigDecimal firstPeriodsRentPrice = totalRent;
        /*首期支付租金*/
        BigDecimal firstPeriodsPrice = totalRent.add(additionalServicesAmount);
        /* 剩余期每期租金 */
        BigDecimal otherPeriodsPrice = BigDecimal.ZERO;
        //如果不止一期
        if (orderByStagesDtoList.size() > 1) {
            /* 剩余期每期租金 */
            otherPeriodsPrice = totalRent.divide(new BigDecimal(orderByStagesDtoList.size()), 2,BigDecimal.ROUND_HALF_UP);
            //首期租金 = 租金 - 每期租金 * （总期数-1）
            firstPeriodsRentPrice = totalRent.subtract(
                            otherPeriodsPrice.multiply(new BigDecimal(orderByStagesDtoList.size() - 1 )))
                    .setScale(2, BigDecimal.ROUND_HALF_UP);
            if (null != orderCouponDto && orderCouponDto.getScene().equals(CouponTemplateConstant.SCENE_FIRST)) {
                //计算总租金
                BigDecimal discountAmount;
                discountAmount = firstPeriodsRentPrice.subtract(orderCouponDto.getDiscountAmount())
                        .compareTo(BigDecimal.ZERO) < 0 ? firstPeriodsRentPrice.subtract(new BigDecimal("0.01")) :
                        firstPeriodsRentPrice.subtract(orderCouponDto.getDiscountAmount());
                totalRent = totalRent.subtract(discountAmount);
                //首期租金优惠券 如果减后<0 设为0.01
                firstPeriodsRentPrice = firstPeriodsRentPrice.subtract(orderCouponDto.getDiscountAmount())
                        .compareTo(BigDecimal.ZERO) < 0 ? new BigDecimal("0.01") : firstPeriodsRentPrice.subtract(
                        orderCouponDto.getDiscountAmount());
                if (StringUtil.isNotEmpty(orderCouponDto.getShopId())) {
                    orderPricesDto.setShopCouponPrice(orderCouponDto.getDiscountAmount());
                } else {
                    orderPricesDto.setPlatformCouponPrice(orderCouponDto.getDiscountAmount());
                }
                orderPricesDto.setCouponPrice(orderCouponDto.getDiscountAmount());
            }
            //首期减免的金额和优惠券不可同时使用，传参数时，两个参数不会同时存在
            if(null != retainDeductionAmount){
                totalRent = totalRent.subtract(retainDeductionAmount);
                //首期租金优惠券 如果减后<0 设为0.01
                firstPeriodsRentPrice = firstPeriodsRentPrice.subtract(retainDeductionAmount).compareTo(BigDecimal.ZERO) < 0 ? new BigDecimal("0.01") : firstPeriodsRentPrice.subtract(retainDeductionAmount);
            }
            //首期支付租金 = 首期纯租金 + 增值服务费用
            firstPeriodsPrice = firstPeriodsRentPrice.add(additionalServicesAmount);
        }
        //月租金
        // 计算原始月租金 除不尽的情况余数会加到首月，这里只是平均每月
        // 原始月租金
        BigDecimal originalMonthRentPrice;
        if (duration > 30) {
            originalMonthRentPrice = AmountUtil.safeMultiply(skuPrice, new BigDecimal(30));
        } else {
            originalMonthRentPrice = AmountUtil.safeMultiply(skuPrice, new BigDecimal(duration));
        }
        /* 订单总金额(总租金+增值服务+物流费用) */
        BigDecimal orderAmount = totalRent.add(additionalServicesAmount);
        //计算押金
        //市场价
        BigDecimal depositSkuMarketPrice = marketPrice.multiply(depositRatio).setScale(2, BigDecimal.ROUND_HALF_UP);
        //租金
        //原押金公式 =押金 =（（总租金>官方价*70%）？总租金：官方价*70%）-第一期租金-（（芝麻冻结额度>免押额度）？芝麻冻结额度：免押额度），若没有走芝麻，则“芝麻冻结额度=0”
        //新公式押金 =官方价*75%  -  第一期租金-（（芝麻冻结额度>免押额度）？芝麻冻结额度：免押额度）,若没有走芝麻，则“芝麻冻结额度=0”
        deposit = depositSkuMarketPrice;
        deposit = deposit.add(baseServiceFee);
        log.info("押金金额为:{}", deposit);
        /* 冻结金额 */
        BigDecimal freezePrice = firstPeriodsPrice.add(deposit);
        //填充还款计划金额
        for (OrderByStagesDto orderByStagesDto : orderByStagesDtoList) {
            orderByStagesDto.setTotalRent(totalRent);
            if(orderByStagesDto.getCurrentPeriodsRent()==null){
                if (orderByStagesDto.getCurrentPeriods() == 1) {
                    orderByStagesDto.setCurrentPeriodsRent(firstPeriodsPrice);
                } else {
                    orderByStagesDto.setCurrentPeriodsRent(otherPeriodsPrice);
                }
            }
        }
        orderPricesDto.setSkuPrice(skuPrice);
        orderPricesDto.setRentPrice(rentPrice.setScale(2, BigDecimal.ROUND_HALF_UP));
        orderPricesDto.setTotalRent(totalRent.setScale(2, BigDecimal.ROUND_HALF_UP));
        orderPricesDto.setTotalPeriods(orderByStagesDtoList.size());
        orderPricesDto.setFirstPeriodsPrice(firstPeriodsPrice.setScale(2, BigDecimal.ROUND_HALF_UP));
        orderPricesDto.setFirstPeriodsRentPrice(firstPeriodsRentPrice.setScale(2, BigDecimal.ROUND_HALF_UP));
        orderPricesDto.setOtherPeriodsPrice(otherPeriodsPrice.setScale(2, BigDecimal.ROUND_HALF_UP));
        orderPricesDto.setRestPeriods(orderByStagesDtoList.size() - 1);
        orderPricesDto.setAdditionalServicesPrice(additionalServicesAmount);
        orderPricesDto.setLogisticPrice(BigDecimal.ZERO);
        orderPricesDto.setDepositAmount(deposit);
        orderPricesDto.setDepositReduce(BigDecimal.ZERO);
        orderPricesDto.setOriginalMonthRentPrice(originalMonthRentPrice.setScale(2, BigDecimal.ROUND_HALF_UP));
        orderPricesDto.setOrderAmount(orderAmount.setScale(2, BigDecimal.ROUND_HALF_UP));
        orderPricesDto.setFreezePrice(freezePrice.setScale(2, BigDecimal.ROUND_HALF_UP));
        orderPricesDto.setDeposit(deposit.setScale(2, BigDecimal.ROUND_HALF_UP));
        orderPricesDto.setOrderByStagesDtoList(orderByStagesDtoList);
        orderPricesDto.setRetainDeductionAmount(retainDeductionAmount);
        return orderPricesDto;
    }


    @Override
    public BigDecimal calculateBuyOutAmount(UserOrders userOrders, List<OrderByStages> orderByStagesList,BigDecimal salePrice) {
        log.info("订单:{},开始计算买断价格", userOrders.getOrderId());
        //已付租金
        BigDecimal repayRentAmount = BigDecimal.ZERO;

        Set<String> orderIds = new HashSet<>();
        for (OrderByStages a : orderByStagesList) {
            orderIds.add(a.getOrderId());
            if ( EnumOrderByStagesStatus.PAYED.equals(a.getStatus())|| EnumOrderByStagesStatus.OVERDUE_PAYED.equals(a.getStatus())) {
                repayRentAmount = repayRentAmount.add(a.getCurrentPeriodsRent());
            }
        }
        BigDecimal buyOutAmount = salePrice.subtract(repayRentAmount);
        buyOutAmount = buyOutAmount.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : buyOutAmount;
        log.info("订单:{},开始不包含优惠券的买断价格为:{}", userOrders.getOrderId(), buyOutAmount.toPlainString());
        return buyOutAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
    }


    @Override
    public BigDecimal getBuyOutAmount(UserOrders userOrders, List<OrderByStages> orderByStagesList, BigDecimal salePrice) {
        return calculateBuyOutAmount(userOrders,orderByStagesList,salePrice);
    }

    /**
     * 计算账单日
     *
     * @param start 起租日
     * @param duration 租期
     * @return 分期账单
     */
    private List<OrderByStagesDto> calculateRepayDay(Date start, int duration,Boolean isRelet) {
        Date now = new Date();
        String startDay = DateUtil.getDate(start, "dd");
        //计算总期数
        int totalPeriods = (duration % 31 == 0) ? duration / 31 : duration / 31 + 1;
        //如果只有一期
        if (totalPeriods == 1) {
            OrderByStagesDto orderByStagesDto = new OrderByStagesDto();
            orderByStagesDto.setStatementDate(now);
            orderByStagesDto.setTotalPeriods(totalPeriods);
            orderByStagesDto.setCurrentPeriods(1);
            return Lists.newArrayList(orderByStagesDto);
        }
        List<OrderByStagesDto> orderByStagesDtoList = Lists.newArrayList();
        //一期单独计算
        OrderByStagesDto firstOrderByStages = new OrderByStagesDto();
        firstOrderByStages.setStatementDate(now);
        firstOrderByStages.setTotalPeriods(totalPeriods);
        firstOrderByStages.setCurrentPeriods(1);
        orderByStagesDtoList.add(firstOrderByStages);
        //计算基数
        int addMonth = Integer.parseInt(startDay) > COMMON_MONTH_MAX_DAY ? 0 : -1;
        //后续期次计算
        if (totalPeriods > 1) {
            for (int i = 2; i <= totalPeriods; i++) {
                OrderByStagesDto orderByStagesDto = new OrderByStagesDto();
                //此处要区分是否是续租
                if(null != isRelet && isRelet){
                    orderByStagesDto.setStatementDate(DateUtil.getReletAfterMonthFirstDate(start,i + addMonth));
                }else{
                    orderByStagesDto.setStatementDate(DateUtil.getAfterMonthFirstDate(i + addMonth));
                }
                orderByStagesDto.setTotalPeriods(totalPeriods);
                orderByStagesDto.setCurrentPeriods(i);
                orderByStagesDtoList.add(orderByStagesDto);
            }
        }
        return orderByStagesDtoList;
    }
}
