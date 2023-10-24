
package com.rent.dao.product;

import com.rent.config.mybatis.IBaseDao;
import com.rent.model.product.PlatformExpress;

/**
 * PlatformExpressDao
 *
 * @author youruo
 * @Date 2020-06-16 11:46
 */
public interface PlatformExpressDao extends IBaseDao<PlatformExpress> {
    /**
     *
     * @param id
     * @return
     */
    PlatformExpress selectExpressById(Long id);

}
