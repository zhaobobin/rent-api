package com.rent.dao.components.impl;

import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.components.AlipayFaceAuthRecordDao;
import com.rent.mapper.components.AlipayFaceAuthRecordMapper;
import com.rent.model.components.AlipayFaceAuthRecord;
import org.springframework.stereotype.Repository;

/**
 * AlipayFaceAuthRecordDao
 *
 * @author xiaoyao
 * @Date 2020-06-24 16:22
 */
@Repository
public class AlipayFaceAuthRecordDaoImpl extends AbstractBaseDaoImpl<AlipayFaceAuthRecord, AlipayFaceAuthRecordMapper>
    implements AlipayFaceAuthRecordDao {

}
