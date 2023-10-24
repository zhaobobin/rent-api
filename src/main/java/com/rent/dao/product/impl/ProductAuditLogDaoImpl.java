    
package com.rent.dao.product.impl;

import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.product.ProductAuditLogDao;
import com.rent.mapper.product.ProductAuditLogMapper;
import com.rent.model.product.ProductAuditLog;
import org.springframework.stereotype.Repository;

/**
 * ProductAuditLogDao
 *
 * @author youruo
 * @Date 2020-06-29 18:32
 */
@Repository
public class ProductAuditLogDaoImpl extends AbstractBaseDaoImpl<ProductAuditLog, ProductAuditLogMapper> implements
    ProductAuditLogDao {


}
