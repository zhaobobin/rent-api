package com.rent.dao.order.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.rent.common.enums.order.EnumOrderByStagesStatus;
import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.order.OrderByStagesDao;
import com.rent.dao.order.UserOrdersDao;
import com.rent.mapper.order.OrderByStagesMapper;
import com.rent.model.order.OrderByStages;
import com.rent.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * OrderByStagesDao
 *
 * @author xiaoyao
 * @Date 2020-06-11 17:25
 */
@Repository
public class OrderByStagesDaoImpl extends AbstractBaseDaoImpl<OrderByStages, OrderByStagesMapper>
        implements OrderByStagesDao {
    @Autowired
    private UserOrdersDao userOrdersDao;

    @Override
    public void updateStatusByOrderId(String orderId, EnumOrderByStagesStatus newStatus,
                                      EnumOrderByStagesStatus oldStatus) {
        OrderByStages orderByStages = new OrderByStages();
        orderByStages.setUpdateTime(new Date());
        orderByStages.setStatus(newStatus);
        this.update(orderByStages, new QueryWrapper<>(OrderByStages.builder()
                .orderId(orderId)
                .status(oldStatus)
                .build()));
    }

    @Override
    public List<OrderByStages> queryOrderByStagesList(OrderByStages orderByStages) {
        return this.list(new QueryWrapper<>(orderByStages));
    }

    @Override
    public List<OrderByStages> queryOrderByOrderId(String orderId) {
        return this.list(new QueryWrapper<>(OrderByStages.builder()
                .orderId(orderId)
                .build()));
    }

    @Override
    public List<OrderByStages> queryAllOrderByOrderId(String orderId) {
        List<String> orderIds = userOrdersDao.selectAllOrderId(orderId);
        return this.list(new QueryWrapper<OrderByStages>().in("order_id", orderIds));
    }

    @Override
    public OrderByStages queryOrderByOrderIdAndPeriod(String orderId, Integer currentPeriod) {
        return getOne(new QueryWrapper<OrderByStages>().eq("order_id", orderId)
                .eq("current_periods", currentPeriod));
    }

    @Override
    public OrderByStages queryOrderByStagesOne(OrderByStages orderByStages) {
        return this.getOne(new QueryWrapper<>(orderByStages));
    }

    @Override
    public List<OrderByStages> queryToOverDueList(Date startDate, Date endDate, EnumOrderByStagesStatus status) {
        return this.list(new QueryWrapper<>(new OrderByStages()).select()
                .le(null!= endDate,"statement_date", endDate)
                .ge(null!=startDate,"statement_date", startDate)
                .eq("status", status));
    }

    @Override
    public List<OrderByStages> queryUnPayList(Date start, Date end, List<String> statusList) {
        return this.list(new QueryWrapper<>(new OrderByStages()).select()
                .between("statement_date", start, end)
                .gt("current_periods", 1)
                .in("status", statusList));
    }

    @Override
    public List<OrderByStages> getUnSplitBillOrderStages() {
        List<Integer> paidStatus = new ArrayList<>(2);
        paidStatus.add(2);
        paidStatus.add(3);

        List<OrderByStages> unSplitBillOrderStages = this.list(new QueryWrapper<>(new OrderByStages()).in("`status`",
                paidStatus)
                .isNull("split_bill_time"));
        return unSplitBillOrderStages;
    }

    @Override
    public List<OrderByStages> getUnSplitListByOrderId(String orderId) {
        List<OrderByStages> unSplitBillOrderStages = this.list(new QueryWrapper<>(new OrderByStages()).in("`order_id`",
                orderId)
                .isNull("split_bill_time"));
        return unSplitBillOrderStages;
    }

    @Override
    public List<OrderByStages> getSplitListByOrderId(String orderId) {
        List<OrderByStages> unSplitBillOrderStages = this.list(new QueryWrapper<>(new OrderByStages()).in("`order_id`",
                orderId)
                .isNotNull("split_bill_time"));
        return unSplitBillOrderStages;
    }

    @Override
    public Boolean updateSplitBillTime(Long id) {
        OrderByStages orderByStages = new OrderByStages().setId(id)
                .setSplitBillTime(new Date());
        updateById(orderByStages);
        return Boolean.TRUE;
    }

    @Override
    public Map<String, List<OrderByStages>> queryOrderByStagesByOrders(List<String> orderIdList) {
        List<OrderByStages> orderByStages = this.list(
                new QueryWrapper<>(new OrderByStages()).in(CollectionUtil.isNotEmpty(orderIdList), "order_id",
                        orderIdList));
        if (CollectionUtil.isEmpty(orderByStages)) {
            return Maps.newHashMap();
        }
        return orderByStages.stream()
                .collect(Collectors.toMap(OrderByStages::getOrderId, Lists::newArrayList,
                        (List<OrderByStages> oldList, List<OrderByStages> newList) -> {
                            oldList.addAll(newList);
                            return oldList;
                        }));
    }

    @Override
    public List<OrderByStages> queryOrderByStages(List<String> orderIdList) {
        if(CollectionUtil.isEmpty(orderIdList)){
            return Collections.emptyList();
        }
        return this.list(
            new QueryWrapper<>(new OrderByStages()).in(CollectionUtil.isNotEmpty(orderIdList), "order_id",
                orderIdList));
    }

    @Override
    public List<OrderByStages> selectRepaymentReminderOrders() {
        Date now = new Date();
        String limitStartDate = DateUtil.getDayBeginStr(now);
        String limitEndDate = DateUtil.getDayEndStr(now);
        List<OrderByStages> result = new ArrayList<>();
        List<OrderByStages> stages = list(new QueryWrapper<OrderByStages>()
                .in("status", EnumOrderByStagesStatus.UN_PAY.getCode(), EnumOrderByStagesStatus.OVERDUE_NOT_PAY.getCode())
                .isNotNull("statement_date")
                .isNull("delete_time")
                .ge("statement_date", limitStartDate)
                .le("statement_date", limitEndDate)
        );
        if (CollectionUtil.isEmpty(stages)) {
            return Collections.emptyList();
        }
        stages = stages.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(o -> o.getOrderId()))), ArrayList::new));
        stages.forEach(item -> {
            if (checkOverMoreOne(item)) {
                result.add(item);
            }
        });
        return result;
    }


    @Override
    public List<OrderByStages> selectOverdueRepaymentReminder() {
        Date now = new Date();
        Date limitDate = DateUtil.dateReduceDay(now, 20);
        String limitStartDate = DateUtil.getDayBeginStr(limitDate);
        Date limitDate1 = DateUtil.dateReduceDay(now,1);
        String limitEndDate = DateUtil.getDayEndStr(limitDate1);
        List<OrderByStages> result = new ArrayList<>();
        List<OrderByStages> stages = list(new QueryWrapper<OrderByStages>()
                .in("status", EnumOrderByStagesStatus.UN_PAY.getCode(), EnumOrderByStagesStatus.OVERDUE_NOT_PAY.getCode())
                .isNotNull("statement_date")
                .isNull("delete_time")
                .ge("statement_date", limitStartDate)
                .le("statement_date", limitEndDate)
        );
        if (CollectionUtil.isEmpty(stages)) {
            return Collections.emptyList();
        }
        stages = stages.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(o -> o.getOrderId()))), ArrayList::new));
        stages.forEach(item -> {
            if (checkOverMoreOne(item)) {
                result.add(item);
            }
        });
        return result;
    }

    @Override
    public OrderByStages getLastPeriod(String orderId) {
        return getOne(new QueryWrapper<OrderByStages>().eq("order_id",orderId).orderByDesc("current_periods").last("limit 1"));
    }

    @Override
    public OrderByStages getEarliestPeriod(String orderId) {
        return getOne(new QueryWrapper<OrderByStages>().eq("order_id",orderId).orderByAsc("current_periods").last("limit 1"));
    }

    @Override
    public List<OrderByStages> queryByOutTradeNo(String outTradeNo) {
        return list(new QueryWrapper<OrderByStages>().eq("out_trade_no",outTradeNo));
    }

    @Override
    public BigDecimal getOtherPerdioRent(String orderId) {
        OrderByStages orderByStages =  getOne(new QueryWrapper<OrderByStages>().eq("order_id",orderId).eq("current_periods",2));
        if(orderByStages==null){
            return null;
        }
        return orderByStages.getCurrentPeriodsRent();
    }

    @Override
    public OrderByStages getOtherPeriod(String orderId) {
        return getOne(new QueryWrapper<OrderByStages>().eq("order_id",orderId).eq("current_periods",2));
    }

    @Override
    public List<OrderByStages> getOtherPeriodList(String orderId) {
        return list(new QueryWrapper<OrderByStages>().eq("order_id",orderId).ge("current_periods",2));
    }


    /**
     * 检验是否超过一天逾期
     *
     * @param orderByStages
     * @return
     */
    public Boolean checkOverMoreOne(OrderByStages orderByStages) {
        return userOrdersDao.checkOrderStatus(orderByStages.getOrderId());
    }

    @Override
    public Boolean getIsHavePay(String orderId) {
        List<OrderByStages> stages = list(new QueryWrapper<OrderByStages>()
                .eq("order_id", orderId)
                .in("status", EnumOrderByStagesStatus.PAYED.getCode(), EnumOrderByStagesStatus.OVERDUE_PAYED.getCode())
                .isNotNull("statement_date")
                .isNull("delete_time")
        );
        if (CollectionUtil.isNotEmpty(stages)) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    @Override
    public Integer getRemainPeriodCount(String orderId) {
        return count(new QueryWrapper<OrderByStages>().eq("order_id",orderId).eq("status",EnumOrderByStagesStatus.UN_PAY.getCode()));
    }


}
