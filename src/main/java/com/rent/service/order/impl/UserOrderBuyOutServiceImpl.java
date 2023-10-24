package com.rent.service.order.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rent.common.dto.components.dto.SendMsgDto;
import com.rent.common.dto.components.response.AliPayTradeCreateResponse;
import com.rent.common.dto.marketing.OrderCouponDto;
import com.rent.common.dto.order.response.BuyOutOrderPageDto;
import com.rent.common.dto.order.response.BuyOutOrderPayResponse;
import com.rent.common.dto.order.resquest.UserOrderBuyOutReqDto;
import com.rent.common.dto.product.OrderProductDetailDto;
import com.rent.common.dto.product.ProductShopCateReqDto;
import com.rent.common.dto.product.ShopDto;
import com.rent.common.enums.components.EnumAliPayStatus;
import com.rent.common.enums.components.EnumTradeType;
import com.rent.common.enums.order.*;
import com.rent.common.enums.product.ProductStatus;
import com.rent.common.util.AsyncUtil;
import com.rent.common.util.GsonUtil;
import com.rent.common.util.SequenceUtil;
import com.rent.dao.order.*;
import com.rent.exception.HzsxBizException;
import com.rent.model.order.*;
import com.rent.service.components.SendSmsService;
import com.rent.service.marketing.LiteCouponCenterService;
import com.rent.service.order.*;
import com.rent.service.product.ProductService;
import com.rent.service.product.ShopService;
import com.rent.util.AppParamUtil;
import com.rent.util.DateUtil;
import com.rent.util.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * 用户买断Service
 *
 * @author xiaoyao
 * @Date 2020-06-23 14:50
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserOrderBuyOutServiceImpl implements UserOrderBuyOutService {

    private final UserOrderBuyOutDao userOrderBuyOutDao;
    private final UserOrderItemsDao userOrderItemsDao;
    private final OrderByStagesDao orderByStagesDao;
    private final UserOrdersDao userOrdersDao;
    private final UserOrderCashesDao userOrderCashesDao;
    private final OrderAddressDao orderAddressDao;
    private final CapitalOperateCore capitalOperateCore;
    private final OrderRepayPlanFactory orderRepayPlanFactory;
    private final OrderFinishCore orderFinishCore;
    private final UserOrdersQueryService userOrdersQueryService;
    private final OrderOperateCore orderOperateCore;
    private final ProductService productService;
    private final LiteCouponCenterService liteCouponCenterService;
    private final ShopService shopService;
    private final SendSmsService sendSmsService;

    @Override
    public BuyOutOrderPageDto buyOutPage(String orderId) {
        UserOrders userOrders = userOrdersDao.selectOneByOrderId(orderId);
        UserOrderItems userOrderItems = userOrderItemsDao.selectOneByOrderId(orderId);
        BigDecimal salePrice = userOrderItems.getSalePrice();
        log.info("订单销售价 orderId：{}， salePrice :{}",orderId,salePrice);
        if (salePrice == null) {
            throw new HzsxBizException(EnumOrderError.SALE_PRICE_NOT_ALLOW_NULL.getCode(), EnumOrderError.SALE_PRICE_NOT_ALLOW_NULL.getMsg(), this.getClass());
        }
        List<OrderByStages> orderByStages = orderByStagesDao.queryAllOrderByOrderId(orderId);

        //已支付租金
        BigDecimal paidRent = orderByStages.stream()
            .filter(b-> b.getStatus().equals(EnumOrderByStagesStatus.OVERDUE_PAYED) || b.getStatus().equals(EnumOrderByStagesStatus.PAYED))
            .map(b -> ObjectUtils.defaultIfNull(b.getCurrentPeriodsRent(), BigDecimal.ZERO))
            .reduce(BigDecimal::add)
            .orElse(BigDecimal.ZERO);
        BuyOutOrderPageDto buyOutOrderPageDto = new BuyOutOrderPageDto();
        //到期买断尾款--无续租则获取订单总租金--有续租则获取以往订单共同的总租金
        BigDecimal totolRent = userOrdersDao.getOrderTotolRent(orderId);
        BigDecimal dueBuyOutAmount = salePrice.subtract(totolRent).setScale(2, BigDecimal.ROUND_HALF_UP);
        buyOutOrderPageDto.setDueBuyOutAmount(dueBuyOutAmount.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : dueBuyOutAmount);
        //买断尾款
        BigDecimal endFund = orderRepayPlanFactory.getBuyOutAmount(userOrders, orderByStages, userOrderItems.getSalePrice());
        //买断应付金额
        BigDecimal needPay = null;
        if (endFund.compareTo(BigDecimal.ZERO) <= 0) {
            endFund = BigDecimal.ZERO;
            needPay = BigDecimal.ZERO;
        } else {
            //优惠计算，返回可用优惠券列表，默认使用最大优惠券
            ProductShopCateReqDto productShopCateReqDto = productService.selectProductCateByProductId(userOrders.getProductId());
            List<OrderCouponDto> orderCouponDtos = liteCouponCenterService.getOrderCoupon(null == productShopCateReqDto || null == productShopCateReqDto.getCategoryId() ? null :productShopCateReqDto.getCategoryId().toString(),
                    userOrders.getProductId(),
                    userOrders.getShopId(),
                    userOrders.getUid(),
                    endFund,
                    BigDecimal.ZERO,
                    EnumOrderType.BUYOUT_ORDER);
            if (CollectionUtil.isEmpty(orderCouponDtos)) {
                log.info("订单：{}没有可用的优惠券", orderId);
                needPay = endFund;
                buyOutOrderPageDto.setCouponAmount(BigDecimal.ZERO);
                buyOutOrderPageDto.setCouponCode(null);
            } else {
                buyOutOrderPageDto.setOrderCouponDtos(orderCouponDtos);
                orderCouponDtos.get(0).setCheck(Boolean.TRUE);
                needPay = endFund.subtract(orderCouponDtos.get(0).getDiscountAmount()).setScale(2, BigDecimal.ROUND_UP);
                needPay = needPay.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : needPay;
                buyOutOrderPageDto.setCouponAmount(orderCouponDtos.get(0).getDiscountAmount());
                buyOutOrderPageDto.setCouponCode(orderCouponDtos.get(0).getCode());
            }
        }
        if (endFund.compareTo(BigDecimal.ZERO) <= 0) {
            endFund = BigDecimal.ZERO;
            needPay = BigDecimal.ZERO;
        }
        UserOrderCashes userOrderCashes = userOrderCashesDao.selectOneByOrderId(orderId);
        OrderProductDetailDto detailDto = userOrdersQueryService.selectOrderProductDetail(Collections.singletonList(orderId)).get(orderId);
        buyOutOrderPageDto.setSkuTitle(detailDto.getSkuTitle());
        buyOutOrderPageDto.setProductName(detailDto.getProductName());
        buyOutOrderPageDto.setImages(detailDto.getMainImageUrl());
        buyOutOrderPageDto.setTotalRent(userOrderCashes.getTotalRent());
        buyOutOrderPageDto.setDeposit(userOrderCashes.getDeposit());
        buyOutOrderPageDto.setEndFund(endFund);
        buyOutOrderPageDto.setNeedPay(needPay);
        buyOutOrderPageDto.setPaid(paidRent);
        buyOutOrderPageDto.setOrderId(orderId);
        buyOutOrderPageDto.setBuyOutSupport(null == userOrderItems.getBuyOutSupport() ? ProductStatus.IS_NOT_BUY_OUT.getCode() : userOrderItems.getBuyOutSupport());
        return buyOutOrderPageDto;
    }
    @Override
    public BuyOutOrderPageDto buyOutTrial(UserOrderBuyOutReqDto request) {
        String orderId = request.getOrderId();
        UserOrders userOrders = userOrdersDao.selectOneByOrderId(orderId);
        UserOrderItems userOrderItems = userOrderItemsDao.selectOneByOrderId(orderId);
        BigDecimal salePrice = userOrderItems.getSalePrice();
        log.info("订单销售价 orderId：{}， salePrice :{}",orderId,salePrice);
        if (salePrice == null) {
            throw new HzsxBizException(EnumOrderError.SALE_PRICE_NOT_ALLOW_NULL.getCode(), EnumOrderError.SALE_PRICE_NOT_ALLOW_NULL.getMsg(), this.getClass());
        }
        List<OrderByStages> orderByStages = orderByStagesDao.queryOrderByOrderId(orderId);
        BuyOutOrderPageDto buyOutOrderPageDto = new BuyOutOrderPageDto();
        //到期买断尾款
        BigDecimal dueBuyOutAmount = salePrice.subtract(orderByStages.get(0).getTotalRent()).setScale(2, BigDecimal.ROUND_HALF_UP);
        buyOutOrderPageDto.setDueBuyOutAmount(dueBuyOutAmount.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : dueBuyOutAmount);
        //买断尾款
        BigDecimal endFund = orderRepayPlanFactory.getBuyOutAmount(userOrders, orderByStages, salePrice);
        //买断应付金额
        BigDecimal needPay = null;
        ProductShopCateReqDto productShopCateReqDto = productService.selectProductCateByProductId(userOrders.getProductId());

        List<OrderCouponDto> orderCouponDtos = liteCouponCenterService.getOrderCoupon(
                    null == productShopCateReqDto || null == productShopCateReqDto.getCategoryId() ? null :productShopCateReqDto.getCategoryId().toString(),
                    userOrders.getProductId(),
                    userOrders.getShopId(),
                    userOrders.getUid(),
                    endFund,
                    BigDecimal.ZERO,
                    EnumOrderType.BUYOUT_ORDER);
        buyOutOrderPageDto.setOrderCouponDtos(orderCouponDtos);
        if(StringUtils.isNotEmpty(request.getCouponId())){
            OrderCouponDto chosenCoupon = null;
            for (OrderCouponDto orderCouponDto : orderCouponDtos) {
                if(orderCouponDto.getCode().equals(request.getCouponId())){
                    chosenCoupon =orderCouponDto;
                    orderCouponDto.setCheck(Boolean.TRUE);
                    break;
                }
            }
            if(chosenCoupon==null){
                throw new HzsxBizException("-1","未查询到选中的优惠券");
            }
            needPay = endFund.subtract(chosenCoupon.getDiscountAmount()).setScale(2, BigDecimal.ROUND_UP);
            buyOutOrderPageDto.setCouponAmount(chosenCoupon.getDiscountAmount());
            buyOutOrderPageDto.setCouponCode(chosenCoupon.getCode());
        }else {
            needPay = endFund;
            buyOutOrderPageDto.setCouponAmount(BigDecimal.ZERO);
            buyOutOrderPageDto.setCouponCode(null);
        }
        needPay = needPay.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : needPay;
        if (endFund.compareTo(BigDecimal.ZERO) <= 0) {
            endFund = BigDecimal.ZERO;
            needPay = BigDecimal.ZERO;
        }
        buyOutOrderPageDto.setEndFund(endFund);
        buyOutOrderPageDto.setNeedPay(needPay);
        return buyOutOrderPageDto;
    }

    @Override
    public BuyOutOrderPayResponse liteBuyOutOrderPay(String orderId, String couponCode) {
        UserOrderBuyOut userOrderBuyOut = userOrderBuyOutDao.selectOneByOrderId(orderId);
        if (null != userOrderBuyOut && !(EnumOrderBuyOutStatus.PAYING.equals(userOrderBuyOut.getState())|| EnumOrderBuyOutStatus.UN_PAY.equals(userOrderBuyOut.getState()))) {
            throw new HzsxBizException(EnumOrderError.ORDER_BUY_OUT_STATUS_ERROR.getCode(),EnumOrderError.ORDER_BUY_OUT_STATUS_ERROR.getMsg(), this.getClass());
        }

        UserOrders userOrders = userOrdersDao.selectOneByOrderId(orderId);
        if (userOrders == null) {
            log.error("【买断】查询订单失败->订单号={}", new Object[] {orderId});
            throw new HzsxBizException(EnumOrderError.ORDER_NOT_EXISTS.getCode(),EnumOrderError.ORDER_NOT_EXISTS.getMsg(), this.getClass());
        }

        //待确认是否只有租用中才可以买断-有14中状态
        if (!EnumOrderStatus.RENTING.equals(userOrders.getStatus())) {
            log.error("【买断】订单状态不支持买断->当前订单状态={}", new Object[] {userOrders.getStatus()});
            throw new HzsxBizException(EnumOrderError.ORDER_STATUS_ERROR.getCode(),EnumOrderError.ORDER_STATUS_ERROR.getMsg(), this.getClass());
        }

        UserOrderItems userOrderItems = userOrderItemsDao.selectOneByOrderId(orderId);
        BigDecimal salePrice = userOrderItems.getSalePrice();
        if (salePrice == null) {
            throw new HzsxBizException(EnumOrderError.SALE_PRICE_NOT_ALLOW_NULL.getCode(),
                    EnumOrderError.SALE_PRICE_NOT_ALLOW_NULL.getMsg(), this.getClass());
        }
        //校验商品买断规则-以前的数据默认支持提前买断
        Integer buyOutSupport =  userOrderItems.getBuyOutSupport();
        if(ProductStatus.IS_BUY_OUT_MATURE.getCode().equals(buyOutSupport)){
            Boolean isBefore = DateUtil.isBefore(DateUtil.dateStr4(userOrders.getUnrentTime()));
            if(null == userOrders.getUnrentTime()){
                throw new HzsxBizException(EnumOrderError.BUY_OUT_UNRENT_ERROR.getCode(),EnumOrderError.BUY_OUT_UNRENT_ERROR.getMsg(), this.getClass());
            }
            if(!isBefore){
                throw new HzsxBizException(EnumOrderError.BUY_OUT_MATURE_ERROR.getCode(),EnumOrderError.BUY_OUT_MATURE_ERROR.getMsg(), this.getClass());
            }
        }
        List<OrderByStages> orderByStages = orderByStagesDao.queryAllOrderByOrderId(orderId);
        //已付租金
        BigDecimal paid = orderByStages.stream()
                .filter(a -> EnumOrderByStagesStatus.PAYED.equals(a.getStatus())| EnumOrderByStagesStatus.OVERDUE_PAYED.equals(a.getStatus()))
                .map(OrderByStages::getCurrentPeriodsRent)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        //买断尾款
        BigDecimal endFund = orderRepayPlanFactory.getBuyOutAmount(userOrders, orderByStages, userOrderItems.getSalePrice());
        String buyOutOrderId = SequenceUtil.getTypeSerialNo(EnumSerialModalName.BUY_OUT_ID);
        BigDecimal marketPrice = userOrderItems.getMarketPrice();
        String uid = userOrders.getUid();
        String payNumber = SequenceUtil.nextId();
        if (null != userOrderBuyOut) {
            userOrderBuyOut.setOutTransNo(payNumber);
            userOrderBuyOut.setChannelId(AppParamUtil.getChannelId());
            userOrderBuyOut.setUpdateTime(new Date());
            userOrderBuyOut.setPaidRent(paid);
            userOrderBuyOut.setEndFund(endFund);
        } else {
            userOrderBuyOut = UserOrderBuyOut.builder()
                    .uid(uid)
                    .orderId(orderId)
                    .buyOutOrderId(buyOutOrderId)
                    .marketPrice(marketPrice)
                    .shopId(userOrders.getShopId())
                    .salePrice(salePrice)
                    .realSalePrice(salePrice)
                    .paidRent(paid)
                    .endFund(endFund)
                    .state(EnumOrderBuyOutStatus.UN_PAY)
                    .outTransNo(payNumber)
                    .channelId(AppParamUtil.getChannelId())
                    .createTime(new Date())
                    .build();
            userOrderBuyOutDao.save(userOrderBuyOut);
        }
        //优惠计算 金额大于0使用优惠券
        if (StringUtil.isNotEmpty(couponCode) && endFund.compareTo(BigDecimal.ZERO) > 0) {
            ProductShopCateReqDto productShopCateReqDto = productService.selectProductCateByProductId(userOrders.getProductId());
            OrderCouponDto couponDto  = liteCouponCenterService.getCouponInfoByCode(productShopCateReqDto.getCategoryId().toString(), userOrders.getProductId(), userOrders.getShopId(),
                    userOrders.getUid(),endFund, BigDecimal.ZERO,EnumOrderType.BUYOUT_ORDER, couponCode);
            liteCouponCenterService.updateCouponUsed(couponDto.getCode(), buyOutOrderId);

            endFund = endFund.subtract(couponDto.getDiscountAmount())
                    .setScale(2, BigDecimal.ROUND_UP);
            BigDecimal discountAmount = endFund.compareTo(BigDecimal.ZERO) < 0 ? endFund : couponDto.getDiscountAmount();
            if (StringUtil.isNotEmpty(couponDto.getShopId())) {
                userOrderBuyOut.setShopCouponReduction(discountAmount);
            } else {
                userOrderBuyOut.setPlatformCouponReduction(discountAmount);
            }
            endFund = endFund.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : endFund;
        }
        //更新记录
        userOrderBuyOutDao.saveOrUpdate(userOrderBuyOut);
        if (endFund.compareTo(BigDecimal.ZERO) <= 0) {
            //不要发起支付
            String finalPayNumber = payNumber;
            //订单相关处理
            AsyncUtil.runAsync( () -> buyOutPayedCallBack(finalPayNumber, orderId, SequenceUtil.nextId(), EnumAliPayStatus.SUCCESS));
            return new BuyOutOrderPayResponse();
        }
        //调用支付
        AliPayTradeCreateResponse aliPayTradeCreateResponse = capitalOperateCore.alipayTradeCreate(EnumTradeType.BUY_OUT,null,"订单买断 订单号:" + orderId,userOrders.getOrderId(), payNumber, endFund,userOrders.getUid(), null, null, userOrders.getProductId());
        return BuyOutOrderPayResponse.builder()
                .payUrl(aliPayTradeCreateResponse.getTradeNo())
                .serialNo(aliPayTradeCreateResponse.getSerialNo())
                .build();
    }

//    @Override
//    public UserOrderBuyOutDto selectOutOrderByOrderId(String orderId) {
//        return UserOrderBuyOutConverter.model2Dto(userOrderBuyOutDao.selectOneByOrderId(orderId));
//    }

    @Override
    public void buyOutPayedCallBack(String outTradeNo, String orderId, String tradeNO, EnumAliPayStatus payStatus) {
        UserOrderBuyOut userBuyOutOrder = userOrderBuyOutDao.getOne(new QueryWrapper<>(UserOrderBuyOut.builder().orderId(orderId).build()));
        Date now = new Date();
        if (null != userBuyOutOrder) {
            if (EnumAliPayStatus.SUCCESS.equals(payStatus)) {
                //修改买断订单
                log.info("【买断订单是否存在】userBuyOutOrder：{}", GsonUtil.objectToJsonString(userBuyOutOrder));
                userBuyOutOrder.setState(EnumOrderBuyOutStatus.FINISH);
                userBuyOutOrder.setUpdateTime(now);
                userOrderBuyOutDao.updateById(userBuyOutOrder);
                //修改原订单
                UserOrders userOrders = this.userOrdersDao.selectOneByOrderId(orderId);
                //添加订单操作记录 add at 2020年10月27日13:58:41
                AsyncUtil.runAsync( () -> orderOperateCore.orderOperationRegister(userOrders.getOrderId(), userOrders.getStatus(),userOrders.getStatus(), userOrders.getOrderId(), userOrders.getUserName(), "用户买断"));
                //完结订单
                orderFinishCore.orderFinishCommHandle(userOrders);
                // //发送短信
                AsyncUtil.runAsync(() -> buyOutSendMsg(userOrders, userBuyOutOrder.getBuyOutOrderId()));
            } else if (EnumAliPayStatus.CANCEL.equals(payStatus)) {
                userBuyOutOrder.setState(EnumOrderBuyOutStatus.CANCEL);
                userBuyOutOrder.setUpdateTime(new Date());
                userOrderBuyOutDao.updateById(userBuyOutOrder);
            }
        }
    }

    /**
     * 买断发送短信
     *
     * @return void
     * @Author xiaoyao
     * @Date 10:10 2020-5-5
     * @Param [orders, userBuyOutOrder]
     **/
    private void buyOutSendMsg(UserOrders orders, String buyOutOrderId) {
        if (null != orders) {
            //发送下单用户
            OrderAddress orderAddress = orderAddressDao.queryByOrderId(orders.getOrderId());
            Map<String, Object> data = new HashMap<>();
            data.put("#orderId#", orders.getOrderId());
            log.info("【开始给买断用户发送短信】telephone:{}", orderAddress.getTelephone());
            SendMsgDto sendMsgDto = new SendMsgDto();
            sendMsgDto.setOrderId(orders.getOrderId());
            sendMsgDto.setTelephone(orderAddress.getTelephone());
            sendSmsService.payedBuyOutOrder(sendMsgDto);

            //发送商家
            ShopDto shopDto = shopService.queryByShopId(orders.getShopId());
            if (shopDto!=null) {
                log.info("【开始给买断商户发送短信】 telephone：{},userName:{},buyOutOrderId:{}", shopDto.getRecvMsgTel(), orderAddress.getRealname(), buyOutOrderId);
                sendSmsService.payedBuyOutOrderToBusiness(orderAddress.getRealname(),buyOutOrderId,shopDto.getRecvMsgTel(),orders.getUnrentTime());
            } else {
                log.info("商户未查到");
            }
        }
    }

}