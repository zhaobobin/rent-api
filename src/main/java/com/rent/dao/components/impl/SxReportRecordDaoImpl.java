package com.rent.dao.components.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.components.SxReportRecordDao;
import com.rent.mapper.components.SxReportRecordMapper;
import com.rent.model.components.SxReportRecord;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * CtSxReportRecordDao
 *
 * @author xiaoyao
 * @Date 2020-06-24 16:22
 */
@Repository
public class SxReportRecordDaoImpl extends AbstractBaseDaoImpl<SxReportRecord, SxReportRecordMapper>
    implements SxReportRecordDao {

    @Override
    public SxReportRecord selectOneByOrderId(String orderId) {
        return this.getOne(new QueryWrapper<>(SxReportRecord.builder()
            .orderId(orderId)
            .build()));
    }

    @Override
    public List<SxReportRecord> listByOrderIds(List<String> orderIdList) {
        return this.list(new QueryWrapper<>(new SxReportRecord()).in("order_id",orderIdList).select("order_id",
            "multiple_score"));
    }
}
