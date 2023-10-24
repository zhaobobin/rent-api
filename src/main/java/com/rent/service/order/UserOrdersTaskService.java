package com.rent.service.order;

/**
 * @author xiaoyao
 * @version V1.0
 * @date 2020-6-18 15:47
 * @since 1.0
 */
public interface UserOrdersTaskService {

    /**
     * 违约金设置逾期
     *
     * @return 结果
     */
    boolean orderOverdueTask();

    /**
     * 账单设置逾期
     *
     * @return 结果
     */
    boolean billMaturity();

    /**
     * 分期账单支付批量
     *
     * @return
     */
    boolean stageOrderPay();

    /**
     * 生成分账记录
     */
    void generateSplitBillTask();

    /**
     * 自动确认收货
     */
    void confirmOrderReceipt();

    /**
     * 订单统计定时任务
     */
    void orderStatisticsTask();



    /**
     * 初始化商品分值
     */
     void initProductScore();
}
