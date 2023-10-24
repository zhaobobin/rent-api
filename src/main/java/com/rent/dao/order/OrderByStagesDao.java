package com.rent.dao.order;

import com.rent.common.enums.order.EnumOrderByStagesStatus;
import com.rent.config.mybatis.IBaseDao;
import com.rent.model.order.OrderByStages;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * OrderByStagesDao
 *
 * @author xiaoyao
 * @Date 2020-06-11 17:25
 */
public interface OrderByStagesDao extends IBaseDao<OrderByStages> {

    /**
     * 更新分期账单状态
     *
     * @param orderId 订单编号
     * @param newStatus 更新状态
     * @param oldStatus 原有状态
     */
    void updateStatusByOrderId(String orderId, EnumOrderByStagesStatus newStatus, EnumOrderByStagesStatus oldStatus);

    /**
     * 根据条件查询分期账单列表
     *
     * @param orderByStages 查询条件
     * @return 账单列表
     */
    List<OrderByStages> queryOrderByStagesList(OrderByStages orderByStages);

    /**
     * 根据orderId查询分期账单列表
     *
     * @param orderId 订单编号
     * @return 账单列表
     */
    List<OrderByStages> queryOrderByOrderId(String orderId);


    /**
     * 获取当前订单的分期账单和续租以前的分期账单
     * @param orderId
     * @return
     */
    List<OrderByStages> queryAllOrderByOrderId(String  orderId);

    /**
     * 根据orderId和当前期数查询分期账单列表
     * @param orderId
     * @param currentPeriod
     * @return
     */
    OrderByStages queryOrderByOrderIdAndPeriod(String orderId,Integer currentPeriod);

    /**
     * 根据条件查询分期账单
     *
     * @param orderByStages 查询条件
     * @return 账单列表
     */
    OrderByStages queryOrderByStagesOne(OrderByStages orderByStages);

    /**
     * 查询待设置逾期列表
     *
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @param status 状态
     * @return 待设置逾期列表
     */
    List<OrderByStages> queryToOverDueList(Date startDate, Date endDate, EnumOrderByStagesStatus status);

    /**
     * 查询待支付列表
     *
     * @param start 开始日期
     * @param end 结束日期
     * @param statusList 代付款状态列表
     * @return 代付款列表
     */
    List<OrderByStages> queryUnPayList(Date start, Date end, List<String> statusList);

    /**
     * 获取到已经支付,但是没有分账的分期订单
     *
     * @return 待分账分期账单列表
     */
    List<OrderByStages> getUnSplitBillOrderStages();
    /**
     * 获取某个订单未分账的分期账单列表
     * @param orderId
     * @return
     */
    List<OrderByStages> getUnSplitListByOrderId(String orderId);
    /**
     * 获取某个订单已经分账的分期账单列表
     * @param orderId
     * @return
     */
    List<OrderByStages> getSplitListByOrderId(String orderId);

    /**
     * 更新分账时间
     * @param id
     * @return
     */
    Boolean updateSplitBillTime(Long id);

    /**
     * 根据订单列表查询订单信息
     * @param orderIdList 订单id列表
     * @return
     */
    Map<String,List<OrderByStages>> queryOrderByStagesByOrders(List<String> orderIdList);

    /**
     * 根据订单列表查询订单信息
     * @param orderIdList 订单id列表
     * @return
     */
    List<OrderByStages> queryOrderByStages(List<String> orderIdList);

    /**
     * 获取当天需要还款的账单
     * @return
     */
    List<OrderByStages> selectRepaymentReminderOrders();



    List<OrderByStages> selectOverdueRepaymentReminder();

    /**
     * 获取到最后一期
     * @param orderId
     * @return
     */
    OrderByStages getLastPeriod(String orderId);

    /**
     * 获取到最早
     * @param orderId
     * @return
     */
    OrderByStages getEarliestPeriod(String orderId);


    List<OrderByStages> queryByOutTradeNo(String outTradeNo);

    BigDecimal getOtherPerdioRent(String orderId);

    /**
     * 获取第二期信息
     * @param orderId
     * @return
     */
    OrderByStages getOtherPeriod(String orderId);

    /**
     * 获取第二期之后的订单
     * @param orderId
     * @return
     */
    List<OrderByStages> getOtherPeriodList(String orderId);

    /**
     * 判断是否有已支付订单
     *
     * @param orderId
     * @return
     */
    Boolean getIsHavePay(String orderId);

    /**
     *
     * @param orderId
     * @return
     */
    Integer getRemainPeriodCount(String orderId);
}
