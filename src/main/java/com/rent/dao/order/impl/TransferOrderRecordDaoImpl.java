
package com.rent.dao.order.impl;

import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.order.TransferOrderRecordDao;
import com.rent.mapper.order.TransferOrderRecordMapper;
import com.rent.model.order.TransferOrderRecord;
import org.springframework.stereotype.Repository;

/**
 * TransferOrderRecordDao
 *
 * @author youruo
 * @Date 2021-12-22 17:55
 */
@Repository
public class TransferOrderRecordDaoImpl extends AbstractBaseDaoImpl<TransferOrderRecord, TransferOrderRecordMapper> implements TransferOrderRecordDao {


}
