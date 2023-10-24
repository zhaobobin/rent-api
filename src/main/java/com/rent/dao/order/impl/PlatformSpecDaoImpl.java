package com.rent.dao.order.impl;

import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.order.PlatformSpecDao;
import com.rent.mapper.product.PlatformSpecMapper;
import com.rent.model.product.PlatformSpec;
import org.springframework.stereotype.Repository;

/**
 * PlatformSpecDao
 *
 * @author youruo
 * @Date 2020-06-16 11:50
 */
@Repository
public class PlatformSpecDaoImpl extends AbstractBaseDaoImpl<PlatformSpec, PlatformSpecMapper>
    implements PlatformSpecDao {

}
