package com.rent.dao.order.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.order.OrderReportDao;
import com.rent.mapper.order.OrderReportMapper;
import com.rent.model.order.OrderReport;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * OrderReportDao
 *
 * @author xiaoyao
 * @Date 2020-08-11 16:17
 */
@Repository
public class OrderReportDaoImpl extends AbstractBaseDaoImpl<OrderReport, OrderReportMapper> implements OrderReportDao {

    @Override
    public OrderReport queryByStatisticsDate(Long statisticsDate) {
        return getOne(new QueryWrapper<>(OrderReport.builder()
            .statisticsDate(statisticsDate)
            .build()), false);
    }

    @Override
    public List<OrderReport> queryByStatisticsDate(Long beginDate, Long endDate) {
        return list(new QueryWrapper<>(new OrderReport()).between("statistics_date", beginDate, endDate));
    }
}
