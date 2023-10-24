package com.rent.dao.product.impl;

import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.product.PlatformServiceMarksDao;
import com.rent.mapper.product.PlatformServiceMarksMapper;
import com.rent.model.product.PlatformServiceMarks;
import org.springframework.stereotype.Repository;

/**
 * PlatformServiceMarksDao
 *
 * @author youruo
 * @Date 2020-06-22 10:35
 */
@Repository
public class PlatformServiceMarksDaoImpl extends AbstractBaseDaoImpl<PlatformServiceMarks, PlatformServiceMarksMapper>
    implements PlatformServiceMarksDao {

}
