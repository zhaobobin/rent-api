    
package com.rent.dao.marketing.impl;

import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.marketing.ColumnConfigDao;
import com.rent.mapper.marketing.ColumnConfigMapper;
import com.rent.model.marketing.ColumnConfig;
import org.springframework.stereotype.Repository;


/**
 * @author xiaotong
 */
@Repository
public class ColumnConfigDaoImpl extends AbstractBaseDaoImpl<ColumnConfig, ColumnConfigMapper> implements ColumnConfigDao {


}
