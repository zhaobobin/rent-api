        
package com.rent.dao.product;

import com.rent.common.dto.product.EnterpriseInfoDto;
import com.rent.config.mybatis.IBaseDao;
import com.rent.model.product.ShopEnterpriseInfos;

/**
 * ShopEnterpriseInfosDao
 *
 * @author youruo
 * @Date 2020-06-17 10:46
 */
public interface ShopEnterpriseInfosDao extends IBaseDao<ShopEnterpriseInfos> {


    /**
     * 获取商家企业信息
     * @param shopId
     * @return
     */
    EnterpriseInfoDto getShopEnterpriseInfosByShopId(String shopId);

    /**
     * 根据店铺ID获取企业信息
     * @param shopId
     * @return
     */
    ShopEnterpriseInfos getByShopId(String shopId);



}
