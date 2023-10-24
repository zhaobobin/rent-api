package com.rent.dao.components.impl;

import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.components.AlipayTransferRecordDao;
import com.rent.mapper.components.AlipayTransferRecordMapper;
import com.rent.model.components.AlipayTransferRecord;
import org.springframework.stereotype.Repository;

/**
 * AlipayTransferRecordDao
 *
 * @author xiaoyao
 * @Date 2020-07-03 10:49
 */
@Repository
public class AlipayTransferRecordDaoImpl extends AbstractBaseDaoImpl<AlipayTransferRecord, AlipayTransferRecordMapper>
    implements AlipayTransferRecordDao {

}
