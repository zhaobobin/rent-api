package com.rent.dao.components;

import com.rent.config.mybatis.IBaseDao;
import com.rent.model.components.SxReportRecord;

import java.util.List;

/**
 * CtSxReportRecordDao
 *
 * @author xiaoyao
 * @Date 2020-06-24 16:22
 */
public interface SxReportRecordDao extends IBaseDao<SxReportRecord> {

    /**
     * 根据orderId查询最近一条记录
     *
     * @param orderId
     * @return
     */
    SxReportRecord selectOneByOrderId(String orderId);

    /**
     * 根据orderIds查询列表
     *
     * @param orderIdList
     * @return
     */
    List<SxReportRecord> listByOrderIds(List<String> orderIdList);
}
