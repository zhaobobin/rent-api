    
package com.rent.dao.marketing.impl;

import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.marketing.UserCollectionDao;
import com.rent.mapper.marketing.UserCollectionMapper;
import com.rent.model.marketing.UserCollection;
import org.springframework.stereotype.Repository;


/**
 * UserCollectionDao
 *
 * @author zhao
 * @Date 2020-07-22 15:28
 */
@Repository
public class UserCollectionDaoImpl extends AbstractBaseDaoImpl<UserCollection, UserCollectionMapper> implements UserCollectionDao {


}
