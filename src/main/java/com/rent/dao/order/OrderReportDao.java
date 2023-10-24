        
package com.rent.dao.order;

import com.rent.config.mybatis.IBaseDao;
import com.rent.model.order.OrderReport;

import java.util.List;

/**
 * OrderReportDao
 *
 * @author xiaoyao
 * @Date 2020-08-11 16:17
 */
public interface OrderReportDao extends IBaseDao<OrderReport> {

    /**
     * 根据统计时间查询统计数据
     * @param statisticsDate 统计时间
     * @return 报表数据
     */
    OrderReport queryByStatisticsDate(Long statisticsDate);

    /**
     * 根据统计时间查询统计数据
     * @param beginDate 统计时间
     * @param endDate 统计时间
     * @return 报表数据
     */
    List<OrderReport> queryByStatisticsDate(Long beginDate, Long endDate);
}
