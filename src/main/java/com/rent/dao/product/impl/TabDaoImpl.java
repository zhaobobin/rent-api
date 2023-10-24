    
package com.rent.dao.product.impl;

import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.product.TabDao;
import com.rent.mapper.product.TabMapper;
import com.rent.model.product.Tab;
import org.springframework.stereotype.Repository;


/**
 * @author zhaowenchao
 */
@Repository
public class TabDaoImpl extends AbstractBaseDaoImpl<Tab, TabMapper> implements TabDao {


}
