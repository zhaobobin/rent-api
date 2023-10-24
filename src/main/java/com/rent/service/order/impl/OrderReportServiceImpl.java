package com.rent.service.order.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rent.common.converter.order.OrderReportConverter;
import com.rent.common.dto.backstage.*;
import com.rent.common.enums.order.*;
import com.rent.common.enums.user.EnumBackstageUserPlatform;
import com.rent.dao.order.OrderAuditRecordDao;
import com.rent.dao.order.OrderReportDao;
import com.rent.dao.order.UserOrdersDao;
import com.rent.exception.HzsxBizException;
import com.rent.model.order.OrderAuditRecord;
import com.rent.model.order.OrderReport;
import com.rent.model.order.UserOrders;
import com.rent.model.order.UserPointDto;
import com.rent.service.order.OrderReportService;
import com.rent.service.product.PlatformChannelService;
import com.rent.service.user.UserPointService;
import com.rent.util.DateUtil;
import com.rent.util.LoginUserUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 订单报表统计Service
 *
 * @author xiaoyao
 * @Date 2020-08-11 16:17
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class OrderReportServiceImpl implements OrderReportService {

    private final UserOrdersDao userOrdersDao;
    private final OrderAuditRecordDao orderAuditRecordDao;
    private final PlatformChannelService platformChannelService;
    private final UserPointService userPointService;
    private final OrderReportDao orderReportDao;

    private static final BigDecimal HUNDRED = new BigDecimal(100);


    @Override
    public Map<String, Integer> businessOrderStatistics(Boolean isMyAuditOrder) {

        Map<String, Integer> map = new HashMap<>();
        for (EnumOrderStatus status : EnumOrderStatus.values()) {
            map.put(status.getCode(), 0);
        }
        map.put("", 0);
        String shopId = null;
        if (!EnumBackstageUserPlatform.OPE.getCode().equals(LoginUserUtil.getLoginUser().getShopId())) {
            shopId = LoginUserUtil.getLoginUser().getShopId();
        }
        //查询当日订单总量
        List<UserOrders> userOrdersTotal = userOrdersDao.selectCountByDate(null, null, null, shopId, isMyAuditOrder);
        for (UserOrders userOrders : userOrdersTotal) {
            if (EnumOrderType.GENERAL_ORDER.equals(userOrders.getType())) {
                String statusCode = userOrders.getStatus().getCode();
                map.put(statusCode, map.get(statusCode) + 1);
                map.put("", map.get("") + 1);
            }
        }
        return map;
    }

    @Override
    public List<OrderReportDto> queryOrderReport(QueryOrderReportRequest request) {
        List<OrderReport> orderReportList = orderReportDao.list(
                new QueryWrapper<>(new OrderReport()).between("statistics_date",
                        Long.valueOf(DateUtil.date2String(request.getOrderStatisticsStartDate(), DateUtil.DATE_FORMAT_2)),
                        Long.valueOf(DateUtil.date2String(request.getOrderStatisticsEndDate(), DateUtil.DATE_FORMAT_2))));
        return OrderReportConverter.modelList2DtoList(orderReportList);
    }

    @Override
    public OrderReportFormDto orderReportForm(OrderReportFormRequest request) {
        OrderReportFormDto resp = new OrderReportFormDto();
        QueryWrapper<UserOrders> queryWrapper = new QueryWrapper().select("order_id", "uid", "create_time", "status", "close_type", "audit_label", "channel_id", "examine_status");
        queryWrapper = queryWrapper.between("create_time", request.getBegin(), request.getEnd());
        List<String> sourceUserIdList = null;
        List<UserPointDto> sourceUserList = null;
        if (StringUtils.isNotEmpty(request.getAction()) && StringUtils.isNotEmpty(request.getChannel()) && StringUtils.isNotEmpty(request.getPosition())) {
            sourceUserList = userPointService.getUserSource(request.getPosition(), request.getAction(), request.getChannel());
            if (CollectionUtils.isEmpty(sourceUserList)) {
                throw new HzsxBizException("-1", "未查询到数据");
            }
            sourceUserIdList = sourceUserList.stream().map(UserPointDto::getUid).collect(Collectors.toList());
            queryWrapper.in("uid", sourceUserIdList);
        }
        if (StringUtils.isNotEmpty(request.getShopId())) {
            queryWrapper.eq("shop_id", request.getShopId());
        }
        if (StringUtils.isNotEmpty(request.getProductId())) {
            queryWrapper.eq("product_id", request.getProductId());
        }
        List<UserOrders> ordersList = userOrdersDao.list(queryWrapper);
        //这个是需要最终统计的list
        List<UserOrders> statisticsOrderList = null;
        if (CollectionUtils.isNotEmpty(sourceUserIdList)) {
            statisticsOrderList = new ArrayList<>(ordersList.size());
            Map<String, Date> uidChannelTimeMap = sourceUserList.stream()
                    .collect(Collectors.toMap(UserPointDto::getUid, UserPointDto::getCreateTime));
            for (UserOrders userOrders : ordersList) {
                Date channelTime = uidChannelTimeMap.get(userOrders.getUid());
                Date orderTime = userOrders.getCreateTime();
                //下单时间 在 渠道时间 之后
                if (DateUtil.compare(orderTime, channelTime) > 0) {
                    statisticsOrderList.add(userOrders);
                }
            }
        } else {
            statisticsOrderList = ordersList;
        }
        if (CollectionUtils.isEmpty(statisticsOrderList)) {
            throw new HzsxBizException("-1", "未查询到数据");
        }

        List<OrderReportFormChannelDto> orderReportFormChannelList = new ArrayList<>();
        List<String> orderIdList = statisticsOrderList.stream().map(UserOrders::getOrderId).collect(Collectors.toList());
        List<OrderAuditRecord> auditRecords = orderAuditRecordDao.list(new QueryWrapper<OrderAuditRecord>()
                .select("order_id", "approve_status")
                .in("order_id", orderIdList)
                .groupBy("order_id")
        );

        Map<String, EnumOrderAuditStatus> orderAuditStatusMap = auditRecords.stream()
                .collect(Collectors.toMap(OrderAuditRecord::getOrderId, OrderAuditRecord::getApproveStatus));

        Map<String, List<UserOrders>> channelOrderMap = statisticsOrderList.stream()
                .collect(Collectors.groupingBy(UserOrders::getChannelId));
        if (CollectionUtils.isNotEmpty(request.getChannelId())) {
            for (String channelId : request.getChannelId()) {
                List<UserOrders> channelOrderList = channelOrderMap.get(channelId);
                OrderReportFormChannelDto channelDto = processChannelData(channelOrderList, orderAuditStatusMap);
                channelDto.setChannelName(platformChannelService.getPlatFormChannel(channelId).getChannelName());
                orderReportFormChannelList.add(channelDto);
            }
        }
        OrderReportFormChannelDto allChannelDto = processChannelData(statisticsOrderList, orderAuditStatusMap);
        allChannelDto.setChannelName("总计");
        orderReportFormChannelList.add(allChannelDto);
        resp.setOrderReportFormChannel(orderReportFormChannelList);
        resp.setOrderReportFormChannel(orderReportFormChannelList);
        return resp;
    }

    /**
     * 封装单个渠道的数据
     *
     * @param channelOrderList
     * @param orderAuditStatusMap
     * @return
     */
    private OrderReportFormChannelDto processChannelData(List<UserOrders> channelOrderList, Map<String, EnumOrderAuditStatus> orderAuditStatusMap) {
        if (CollectionUtils.isEmpty(channelOrderList)) {
            OrderReportFormChannelDto channelDto = new OrderReportFormChannelDto();
            channelDto.setTotal(0);
            channelDto.setUnPayUserApply(0);
            channelDto.setUnPayUserApplyRate("0");
            channelDto.setPayFailed(0);
            channelDto.setPayFailedRate("0");
            channelDto.setOverTimePay(0);
            channelDto.setOverTimePayRate("0");
            channelDto.setTotalPaid(channelDto.getTotal() - channelDto.getUnPayUserApply() - channelDto.getPayFailed() - channelDto.getOverTimePay());
            channelDto.setTotalPaidRate("0");
            channelDto.setRiskClose(0);
            channelDto.setRiskCloseRate("0");
            channelDto.setBusinessAudit(0);
            channelDto.setBusinessAuditRefuse(0);
            channelDto.setBusinessAuditRefuseRate("0");
            channelDto.setPlatformAudit(0);
            channelDto.setPlatformAuditRefuse(0);
            channelDto.setPlatformAuditRefuseRate("0");
            channelDto.setTotalRefuseRate("0");
            channelDto.setPaidUserApply(0);
            channelDto.setPaidUserApplyRate("0");
            channelDto.setToAudit(0);
            channelDto.setPendingDeal(0);
            channelDto.setDealt(0);
            channelDto.setDealtPaidRate("0");
            channelDto.setDealtOrderRate("0");
            return channelDto;
        }
        //未支付主动取消
        Integer unPayUserApply = 0;
        //支付失败
        Integer payFailed = 0;
        //支付超时
        Integer overTimePay = 0;
        //系统风控拒绝
        Integer riskClose = 0;
        //已支付主动取消
        Integer paidUserApply = 0;
        //待审批
        Integer toAudit = 0;
        //待终审
        Integer toFinalAudit = 0;
        //待发货
        Integer pendingDeal = 0;
        //成交订单
        Integer dealt = 0;
        //平台审批订单数
        Integer platformAudit = 0;
        //平台拒绝
        Integer platformAuditRefuse = 0;
        //商家审批订单数
        Integer businessAudit = 0;
        //商家拒绝
        Integer businessAuditRefuse = 0;
        for (UserOrders userOrders : channelOrderList) {
            if (userOrders.getCloseType() == EnumOrderCloseType.UN_PAY_USER_APPLY) {
                unPayUserApply++;
            } else if (userOrders.getCloseType() == EnumOrderCloseType.PAY_FAILED) {
                payFailed++;
            } else if (userOrders.getCloseType() == EnumOrderCloseType.OVER_TIME_PAY) {
                overTimePay++;
            } else if (userOrders.getCloseType() == EnumOrderCloseType.RISK_CLOSE) {
                riskClose++;
            } else if (userOrders.getCloseType() == EnumOrderCloseType.PAYED_USER_APPLY) {
                paidUserApply++;
            } else if (userOrders.getCloseType() == EnumOrderCloseType.BUSINESS_CLOSE) {
                paidUserApply++;
            } else if (userOrders.getCloseType() == EnumOrderCloseType.PLATFORM_CLOSE) {
                paidUserApply++;
            }

            if (userOrders.getStatus() == EnumOrderStatus.TO_AUDIT) {
                if (EnumOrderExamineStatus.FIANL_SET.contains(userOrders.getExamineStatus())) {
                    toFinalAudit++;
                } else {
                    toAudit++;
                }
            } else if (userOrders.getStatus() == EnumOrderStatus.PENDING_DEAL) {
                pendingDeal++;
            } else if (userOrders.getStatus() == EnumOrderStatus.WAITING_USER_RECEIVE_CONFIRM) {
                dealt++;
            } else if (userOrders.getStatus() == EnumOrderStatus.RENTING) {
                dealt++;
            } else if (userOrders.getStatus() == EnumOrderStatus.WAITING_SETTLEMENT) {
                dealt++;
            } else if (userOrders.getStatus() == EnumOrderStatus.WAITING_SETTLEMENT_PAYMENT) {
                dealt++;
            } else if (userOrders.getStatus() == EnumOrderStatus.FINISH) {
                dealt++;
            }

            EnumOrderAuditStatus auditStatus = orderAuditStatusMap.get(userOrders.getOrderId());
            if (auditStatus != null) {
                if (!auditStatus.equals(EnumOrderAuditStatus.TO_AUDIT)) {
                    if (userOrders.getAuditLabel().equals(EnumOrderAuditLabel.BUSINESS_AUDIT)) {
                        businessAudit++;
                    } else {
                        platformAudit++;
                    }
                }
                if (auditStatus.equals(EnumOrderAuditStatus.REFUSE)) {
                    if (userOrders.getAuditLabel().equals(EnumOrderAuditLabel.BUSINESS_AUDIT)) {
                        businessAuditRefuse++;
                    } else {
                        platformAuditRefuse++;
                    }
                }
            }
        }
        OrderReportFormChannelDto channelDto = new OrderReportFormChannelDto();
        channelDto.setTotal(channelOrderList.size());
        channelDto.setUnPayUserApply(unPayUserApply);
        channelDto.setUnPayUserApplyRate(getRate(unPayUserApply, channelDto.getTotal()));
        channelDto.setPayFailed(payFailed);
        channelDto.setPayFailedRate(getRate(payFailed, channelDto.getTotal() - channelDto.getUnPayUserApply()));
        channelDto.setOverTimePay(overTimePay);
        channelDto.setOverTimePayRate(getRate(overTimePay, channelDto.getTotal() - channelDto.getUnPayUserApply() - channelDto.getPayFailed()));
        channelDto.setTotalPaid(channelDto.getTotal() - channelDto.getUnPayUserApply() - channelDto.getPayFailed() - channelDto.getOverTimePay());
        channelDto.setTotalPaidRate(getRate(channelDto.getTotalPaid(), channelDto.getTotal()));
        channelDto.setRiskClose(riskClose);
        channelDto.setRiskCloseRate(getRate(channelDto.getRiskClose(), channelDto.getTotalPaid()));
        channelDto.setBusinessAudit(businessAudit);
        channelDto.setBusinessAuditRefuse(businessAuditRefuse);
        channelDto.setBusinessAuditRefuseRate(getRate(businessAuditRefuse, businessAudit));
        channelDto.setPlatformAudit(platformAudit);
        channelDto.setPlatformAuditRefuse(platformAuditRefuse);
        channelDto.setPlatformAuditRefuseRate(getRate(platformAuditRefuse, platformAudit));
        channelDto.setTotalRefuseRate(getRate(platformAuditRefuse + businessAuditRefuse + riskClose, channelDto.getTotalPaid()));
        channelDto.setPaidUserApply(paidUserApply);
        channelDto.setPaidUserApplyRate(getRate(paidUserApply, channelDto.getTotalPaid() - platformAuditRefuse - businessAuditRefuse - riskClose));
        channelDto.setToAudit(toAudit);
        channelDto.setToFinalAudit(toFinalAudit);
        channelDto.setPendingDeal(pendingDeal);
        channelDto.setDealt(dealt);
        channelDto.setDealtPaidRate(getRate(dealt, channelDto.getTotalPaid()));
        channelDto.setDealtOrderRate(getRate(dealt, channelDto.getTotal()));
        return channelDto;
    }

    /**
     * 计算保留两位小数点百分比
     *
     * @param molecular   分子
     * @param denominator 分母
     * @return
     */
    private String getRate(Integer molecular, Integer denominator) {
        if (denominator == 0) {
            return "-";
        }
        BigDecimal rate = new BigDecimal(molecular).divide(new BigDecimal(denominator), 4, BigDecimal.ROUND_HALF_UP);
        rate = rate.multiply(HUNDRED).setScale(2);
        return rate.toPlainString() + "%";
    }

}