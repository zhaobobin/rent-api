    
package com.rent.dao.components.impl;

import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.components.AliyunSmsSendSerialDao;
import com.rent.dao.components.BuyunSmsSendSerialDao;
import com.rent.mapper.components.AliyunSmsSendSerialMapper;
import com.rent.mapper.components.BuyunSmsSendSerialMapper;
import com.rent.model.components.AliyunSmsSendSerial;
import com.rent.model.components.BuyunSmsSendSerial;
import org.springframework.stereotype.Repository;

/**
 * CtBuyunSmsSendSerialDao
 *
 * @author youruo
 * @Date 2021-01-18 10:40
 */
@Repository
public class AliyunSmsSendSerialDaoImpl extends AbstractBaseDaoImpl<AliyunSmsSendSerial, AliyunSmsSendSerialMapper> implements AliyunSmsSendSerialDao {


}
