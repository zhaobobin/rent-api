package com.rent.service.order.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rent.common.constant.ShopSplitRuleConstant;
import com.rent.common.constant.SplitBillRecordConstant;
import com.rent.common.dto.backstage.OnShelfProductReqDto;
import com.rent.common.dto.components.response.ExpressSignInfoResponse;
import com.rent.common.dto.product.SpiltBillRuleConfigDto;
import com.rent.common.enums.components.EnumTradeType;
import com.rent.common.enums.order.EnumOrderByStagesStatus;
import com.rent.common.enums.order.EnumOrderStatus;
import com.rent.common.enums.order.EnumViolationStatus;
import com.rent.common.util.StringUtil;
import com.rent.dao.order.*;
import com.rent.dao.product.PlatformExpressDao;
import com.rent.model.order.*;
import com.rent.model.product.PlatformExpress;
import com.rent.service.components.AliPayCapitalService;
import com.rent.service.components.SxService;
import com.rent.service.order.GenerateSplitBillService;
import com.rent.service.order.OrderReceiptCore;
import com.rent.service.order.SwipingActivityOrderService;
import com.rent.service.order.UserOrdersTaskService;
import com.rent.service.product.ProductService;
import com.rent.service.product.SplitBillConfigService;
import com.rent.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static com.rent.common.enums.order.EnumOrderStatus.PENDING_DEAL;
import static java.util.stream.Collectors.toList;

/**
 * @author xiaoyao
 * @version V1.0
 * @date 2020-6-18 15:47
 * @since 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserOrdersTaskServiceImpl implements UserOrdersTaskService {

    private static final Map<String, String> splitBillTypeMap = new HashMap<String, String>() {{
        put(ShopSplitRuleConstant.TYPE_BUY_OUT, "GenerateBuyOutSplitBillImplService");
        put(ShopSplitRuleConstant.TYPE_RENT_MONTH, "GenerateRentMonthSplitBillImplService");
        put(ShopSplitRuleConstant.TYPE_ORDER_SETTLE, "GenerateOrderSettleSplitBillImplService");
    }};

    private static final Set<EnumOrderStatus> splitBillOrderStatusSet = new HashSet<EnumOrderStatus>() {{
        add(EnumOrderStatus.WAITING_USER_RECEIVE_CONFIRM);
        add(EnumOrderStatus.RENTING);
        add(EnumOrderStatus.WAITING_SETTLEMENT);
        add(EnumOrderStatus.WAITING_SETTLEMENT_PAYMENT);
        add(EnumOrderStatus.FINISH);
    }};

    //已付款集合
    private static final List<EnumOrderStatus> PAYED_STATUS_LIST = Arrays.asList(PENDING_DEAL,
            EnumOrderStatus.WAITING_USER_RECEIVE_CONFIRM, EnumOrderStatus.WAITING_SETTLEMENT,
            EnumOrderStatus.WAITING_SETTLEMENT_PAYMENT, EnumOrderStatus.FINISH);

    private static final Set<EnumOrderStatus> DEAL_STATUS_SET = new HashSet<EnumOrderStatus>() {{
        add(EnumOrderStatus.WAITING_USER_RECEIVE_CONFIRM);
        add(EnumOrderStatus.RENTING);
        add(EnumOrderStatus.WAITING_SETTLEMENT);
        add(EnumOrderStatus.WAITING_SETTLEMENT_PAYMENT);
        add(EnumOrderStatus.FINISH);
    }};

    private final UserOrdersDao userOrdersDao;
    private final OrderByStagesDao orderByStagesDao;
    private final AliPayCapitalService aliPayCapitalService;
    private final OrderAddressDao orderAddressDao;
    private final SplitBillConfigService splitBillConfigService;
    private final UserOrderBuyOutDao userOrderBuyOutDao;
    private final ApplicationContext applicationContext;
    private final UserOrderCashesDao userOrderCashesDao;
    private final OrderReportDao orderReportDao;
    private final OrderSettlementDao orderSettlementDao;
    private final PlatformExpressDao platformExpressDao;
    private final ProductService productService;
    private final ChannelUserOrdersDao channelUserOrdersDao;
    private final ChannelSplitBillDao channelSplitBillDao;
    private final SxService sxService;
    private final OrderReceiptCore orderReceiptCore;
    private final SwipingActivityOrderService swipingActivityOrderService;

    @Override
    public boolean orderOverdueTask() {
        log.info("Quartz_task_orderOverdueTask=============================================================================");
        Date now = new Date();
        UserOrders userOrders = new UserOrders();
        userOrders.setIsViolation(EnumViolationStatus.SETTLEMENT_OVERDUE);
        userOrders.setUpdateTime(now);
        userOrdersDao.update(userOrders, new QueryWrapper<>(new UserOrders()).eq("status",
                        EnumOrderStatus.WAITING_SETTLEMENT_PAYMENT)
                .lt("date(confirm_settlement_time)", DateUtil.addDate(new Date(), -3))
                .isNull("delete_time"));
        return true;
    }

    @Override
    public boolean billMaturity() {
        log.info("Quartz_task_billMaturity=============================================================================");
        //这个月1号
        Date dayBegin = DateUtil.getDayBegin(new Date());
        Date now = new Date();
        //未设置逾期账单设置为逾期,同时计算逾期天数
        List<OrderByStages> orderByStagesList = orderByStagesDao.queryToOverDueList(null, dayBegin, EnumOrderByStagesStatus.UN_PAY);
        if (CollectionUtil.isNotEmpty(orderByStagesList)) {
            for (OrderByStages orderByStages : orderByStagesList) {
                UserOrders userOrders = userOrdersDao.selectOneByOrderId(orderByStages.getOrderId());
                int betweenDays = DateUtil.getBetweenDays(DateUtil.getDayBegin(orderByStages.getStatementDate()), now);
                if (null == userOrders) {
                    continue;
                }
                if (!EnumOrderStatus.RENTING.equals(userOrders.getStatus())) {
                    continue;
                }
                if (DateUtil.compare(orderByStages.getStatementDate(), dayBegin) > 0) {
                    continue;
                }
                orderByStages.setUpdateTime(now);
                orderByStages.setStatus(EnumOrderByStagesStatus.OVERDUE_NOT_PAY);
                orderByStages.setOverdueDays(betweenDays);
                orderByStagesDao.updateById(orderByStages);
                userOrders.setIsViolation(EnumViolationStatus.STAGE_OVERDUE);
                userOrders.setUpdateTime(now);
                userOrdersDao.updateById(userOrders);
            }
        }
        //已设置逾期账单，计算逾期天数
        List<OrderByStages> overdueStagesList = orderByStagesDao.queryToOverDueList(null, null, EnumOrderByStagesStatus.OVERDUE_NOT_PAY);
        if (CollectionUtil.isNotEmpty(overdueStagesList)) {
            for (OrderByStages orderByStages : overdueStagesList) {
                orderByStages.setUpdateTime(now);
                orderByStages.setOverdueDays(
                        DateUtil.getBetweenDays(DateUtil.getDayBegin(orderByStages.getStatementDate()), now));
                orderByStagesDao.updateById(orderByStages);
            }
        }
        return true;
    }

    @Override
    public boolean stageOrderPay() {
        log.info("Quartz_task_stageOrderPay=============================================================================");
        Date now = new Date();
        //查询待支付列表并更新成操作中9
        List<String> statusList = Arrays.asList(EnumOrderByStagesStatus.UN_PAY.getCode(),
                EnumOrderByStagesStatus.OVERDUE_NOT_PAY.getCode());
        Date end = DateUtil.getDayEnd(new Date());
        Date start = DateUtil.addDate(end, -91);
        List<OrderByStages> orderByStagesList = orderByStagesDao.queryUnPayList(start, end, statusList);

        if (CollectionUtil.isEmpty(orderByStagesList)) {
            log.info("【分期租金 预授权转支付】订单数量=0");
            return true;
        }
        log.info("【分期租金 预授权转支付】订单数量={}", new Object[]{orderByStagesList.size()});
        for (OrderByStages orderByStages : orderByStagesList) {
            try {
                log.info("【分期租金 预授权转支付】开始处理订单,orderId={},currentPeriod={}", orderByStages.getOrderId(), orderByStages.getCurrentPeriods());
                UserOrders userOrders = userOrdersDao.selectOneByOrderId(orderByStages.getOrderId());
                if (userOrders == null) {
                    log.info("【分期租金 预授权转支付】未查询到主订单,orderId={}", orderByStages.getOrderId());
                    continue;
                }
                if (!EnumOrderStatus.RENTING.equals(userOrders.getStatus())) {
                    log.info("【分期租金 预授权转支付】订单状态错误,orderId={},订单状态={}", orderByStages.getOrderId(), userOrders.getStatus());
                    continue;
                }
                if (StringUtil.isEmpty(userOrders.getRequestNo())) {
                    log.info("【分期租金 预授权转支付】订单requestNo未空,orderId={}", orderByStages.getOrderId());
                    continue;
                }
                //预授权转支付
                aliPayCapitalService.orderByStageAliPayTradePay(orderByStages.getOrderId(),
                        "分期扣款订单:" + orderByStages.getOrderId() + "|租期:" + orderByStages.getCurrentPeriods(),
                        orderByStages.getCurrentPeriodsRent(), Collections.singletonList(orderByStages.getCurrentPeriods().toString()), EnumTradeType.BILL_WITHHOLD);
            } catch (Exception e) {
                log.error("【分期租金 预授权转支付】处理订单异常,orderId={},currentPeriod={},异常信息:", orderByStages.getOrderId(), orderByStages.getCurrentPeriods(), e);
            }
        }
        return true;
    }

    @Override
    public void generateSplitBillTask() {
        log.info("Quartz_task_generateSplitBillTask=============================================================================");
        List<OrderByStages> unSplitBillOrderStageList = orderByStagesDao.getUnSplitBillOrderStages();
        Date today = DateUtil.getStartTimeOfDay(new Date());
        for (OrderByStages orderByStages : unSplitBillOrderStageList) {
            UserOrders userOrders = userOrdersDao.selectOneByOrderId(orderByStages.getOrderId());
            if (userOrders == null) {
                continue;
            }
            if (userOrders.getStatus().equals(EnumOrderStatus.CLOSED)) {
                orderByStages.setSplitBillTime(new Date());
                orderByStagesDao.updateById(orderByStages);
                continue;
            }
            if (userOrders.getRentStart() == null) {
                continue;
            }
            Date rentStartTime = DateUtil.getStartTimeOfDay(userOrders.getRentStart());
            // 订单租期开始后3天才结算佣金给商户
            if (DateUtil.getBetweenDays(rentStartTime, today) < 1) {
                continue;
            }
            log.info("开始针对订单[{}]的第{}期进行结算", orderByStages.getOrderId(), orderByStages.getCurrentPeriods());
            if (splitBillOrderStatusSet.contains(userOrders.getStatus())) {
                generateSplitBill(ShopSplitRuleConstant.TYPE_RENT, orderByStages.getId(), orderByStages.getShopId(),
                        userOrders.getCreateTime(), orderByStages.getCurrentPeriodsRent(), orderByStages.getRepaymentDate(),
                        orderByStages.getOrderId(), orderByStages.getCurrentPeriods(), userOrders.getUid(),
                        userOrders.getChannelId());
            }
            ChannelUserOrders channelUserOrders = channelUserOrdersDao.getByOrderId(orderByStages.getOrderId());
            if (channelUserOrders != null) {
                ChannelSplitBill channelSplitBill = new ChannelSplitBill();
                channelSplitBill.setPeriod(orderByStages.getCurrentPeriods());
                channelSplitBill.setOrderId(orderByStages.getOrderId());
                channelSplitBill.setUid(userOrders.getUid());
                channelSplitBill.setUserPayAmount(orderByStages.getCurrentPeriodsRent());
                channelSplitBill.setScale(channelUserOrders.getScale());
                channelSplitBill.setChannelAmount(orderByStages.getCurrentPeriodsRent().multiply(channelUserOrders.getScale()));
                channelSplitBill.setCreateTime(new Date());
                channelSplitBill.setMarketingId(channelUserOrders.getMarketingChannelId());
                channelSplitBill.setStatus(SplitBillRecordConstant.STATUS_WAITING_SETTLEMENT);
                log.info("针对订单{}进行渠道佣金结算,金额:{}", orderByStages.getOrderId(), channelSplitBill.getChannelAmount());
                channelSplitBillDao.save(channelSplitBill);
            }
        }
        List<UserOrderBuyOut> unSplitBillOrderBuyOutList = userOrderBuyOutDao.queryUnSplitList();
        for (UserOrderBuyOut userOrderBuyOut : unSplitBillOrderBuyOutList) {
            UserOrders userOrders = userOrdersDao.selectOneByOrderId(userOrderBuyOut.getOrderId());
            generateSplitBill(ShopSplitRuleConstant.TYPE_BUY_OUT, userOrderBuyOut.getId(), userOrders.getShopId(),
                    userOrderBuyOut.getCreateTime(), userOrderBuyOut.getEndFund(), userOrderBuyOut.getCreateTime(),
                    userOrderBuyOut.getBuyOutOrderId(), null, userOrders.getUid(), userOrders.getChannelId());
        }

        List<OrderSettlement> unSplitBillSettlementList = orderSettlementDao.queryUnSplitList();
        for (OrderSettlement orderSettlement : unSplitBillSettlementList) {
            UserOrders userOrders = userOrdersDao.selectOneByOrderId(orderSettlement.getOrderId());
            BigDecimal amount = orderSettlement.getLoseAmount()
                    .add(orderSettlement.getDamageAmount())
                    .add(orderSettlement.getPenaltyAmount())
                    .add(orderSettlement.getDeposit());
            generateSplitBill(ShopSplitRuleConstant.TYPE_ORDER_SETTLE, orderSettlement.getId(), userOrders.getShopId(),
                    userOrders.getCreateTime(), amount, orderSettlement.getPaymentTime(), orderSettlement.getOrderId(),
                    null, userOrders.getUid(), userOrders.getChannelId());
        }
    }

    private void generateSplitBill(String type, Long recordId, String shopId, Date time, BigDecimal userPayAmount,
                                   Date userPayTime, String orderId, Integer period, String uid, String channelId) {
        SpiltBillRuleConfigDto configDto = splitBillConfigService.getUseAbleConfigByType(shopId, time, type, channelId);
        if (configDto == null) {
            log.info("店铺未配置结算规则,跳过订单结算,店铺ID:{},订单号:{},期数:{}", shopId, orderId, period);
            return;
        }
        // 判断当前日期，周日 = 1 所以要纠正一下日期
        int day = DateUtil.getDayOfWeek();
        day = day != 1 ? day - 1 : 7;
        if (!configDto.getCycle().contains(String.valueOf(day))) {
            log.info("当日非店铺结算,跳过订单{}结算", orderId);
            return;
        }
        GenerateSplitBillService generateSplitBillService = (GenerateSplitBillService) applicationContext.getBean(
                splitBillTypeMap.get(configDto.getType()));

        try {
            generateSplitBillService.generate(configDto, recordId, shopId, userPayAmount, userPayTime, type, orderId,
                    period, uid);
        } catch (Exception e) {
            log.error("【生成分账信息异常】orderId={},period={}", new Object[]{orderId, period, e});
        }

    }

    @Override
    @Async
    public void confirmOrderReceipt() {
        log.info("Quartz_task_confirmOrderReceipt=============================================================================");
        List<UserOrders> waitingReceiveList = userOrdersDao.list(new QueryWrapper<>(new UserOrders())
                .eq("status", EnumOrderStatus.WAITING_USER_RECEIVE_CONFIRM)
                .isNull("delete_time"));
        if (CollectionUtil.isEmpty(waitingReceiveList)) {
            log.info("没有待用户确认收货订单，直接退出");
            return;
        }
        //查询物流公司信息
        Map<Long, String> platFormExpressMap = platformExpressDao.list(new QueryWrapper<PlatformExpress>().select("id,short_name"))
                .stream()
                .collect(Collectors.toMap(PlatformExpress::getId, PlatformExpress::getShortName));
        //查询收货地址信息
        Map<String, UserOrders> userOrdersMap = waitingReceiveList.stream().collect(Collectors.toMap(UserOrders::getOrderId, a -> a));
        List<OrderAddress> orderAddressList = orderAddressDao.list(new QueryWrapper<>(new OrderAddress()).in("order_id", userOrdersMap.keySet()));
        List<String> orderIds = waitingReceiveList.stream().map(UserOrders::getOrderId).collect(Collectors.toList());
        Set<String> swipingActivityOrderIdSet = swipingActivityOrderService.getSwipingOrderMapByIds(orderIds);
        //更新租期
        orderAddressList.forEach(orderAddress -> {
            UserOrders userOrders = userOrdersMap.get(orderAddress.getOrderId());
            //刷单商品不走这个确认收货流程
            if (swipingActivityOrderIdSet.contains(userOrders.getOrderId())) {
                return;
            }
            ExpressSignInfoResponse signInfoResponse = sxService.querySignInfo(
                    platFormExpressMap.get(userOrders.getExpressId()), userOrders.getExpressNo(), orderAddress.getTelephone());
            //如果已签收，更新租期
            if (signInfoResponse.getResult()) {
                orderReceiptCore.taskConfirm(userOrders, signInfoResponse.getSignTime());
            }
        });
    }

    @Override
    @Async
    public void orderStatisticsTask() {
        log.info("Quartz_task_orderStatisticsTask=============================================================================");
        //查询当日  订单总量
        Date now = new Date();
        List<UserOrders> userOrdersList = userOrdersDao.selectCountByDate(
                DateUtil.getDayBegin(DateUtil.addDate(now, -1)), DateUtil.getDayEnd(DateUtil.addDate(now, -1)),
                PAYED_STATUS_LIST, null);
        int count = userOrdersDao.countByDate(DateUtil.getDayBegin(DateUtil.addDate(now, -1)),
                DateUtil.getDayEnd(DateUtil.addDate(now, -1)), null);
        //今日总租金
        BigDecimal todayTotalOrderRent = BigDecimal.ZERO;
        if (CollectionUtil.isNotEmpty(userOrdersList)) {
            List<String> orderIdList = userOrdersList.stream()
                    .map(UserOrders::getOrderId)
                    .collect(toList());
            List<UserOrderCashes> userOrderCashesList = userOrderCashesDao.queryByOrderIds(orderIdList);
            todayTotalOrderRent = userOrderCashesList.stream()
                    .map(UserOrderCashes::getTotalRent)
                    .reduce(BigDecimal::add)
                    .orElse(BigDecimal.ZERO);
        }
        OrderReport orderReport = new OrderReport();
        orderReport.setStatisticsDate(
                Long.valueOf(DateUtil.getDate(DateUtil.addDate(now, -1), DateUtil.DATE_FORMAT_2)));
        orderReport.setTotalOrderCount((long) count);
        orderReport.setSuccessOrderCount((long) userOrdersList.size());
        orderReport.setSuccessOrderRent(todayTotalOrderRent);
        orderReport.setCreateTime(now);
        orderReport.setUpdateTime(now);

        orderReportDao.save(orderReport);
    }

    @Override
    public void initProductScore() {
        log.info("Quartz_task_initProductScore=============================================================================");
        Date end = DateUtil.getDayBegin(new Date());
        Date start = DateUtil.addDate(end, -30);
        List<UserOrders> list = userOrdersDao.list(new QueryWrapper<UserOrders>()
                .select("product_id,status,payment_time")
                .between("create_time", start, end));
        Map<String, List<UserOrders>> map = list.stream().collect(Collectors.groupingBy(UserOrders::getProductId));
        List<OnShelfProductReqDto> dtoList = new ArrayList<>();
        for (String productId : map.keySet()) {
            OnShelfProductReqDto dto = new OnShelfProductReqDto();
            dto.setProductId(productId);
            List<UserOrders> productOrderList = map.get(productId);
            int orderCount = 0;
            int paidCount = 0;
            int dealCount = 0;
            for (UserOrders userOrders : productOrderList) {
                orderCount++;
                if (userOrders.getPaymentTime() != null) {
                    paidCount++;
                }
                if (DEAL_STATUS_SET.contains(userOrders.getStatus())) {
                    dealCount++;
                }
            }
            dto.setSysScore(orderCount + paidCount * 4 + dealCount * 5);
            dtoList.add(dto);
        }
        productService.initProductScore(dtoList);
    }
}
