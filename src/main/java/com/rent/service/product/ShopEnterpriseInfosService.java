
package com.rent.service.product;

import com.rent.common.dto.product.ShopEnterpriseInfosDto;

/**
 * 店铺资质表Service
 *
 * @author youruo
 * @Date 2020-06-17 10:46
 */
public interface ShopEnterpriseInfosService {


    ShopEnterpriseInfosDto queryShopEnterpriseInfosDetailByshopId(String shopId);

    /**
     * 企业资质确认
     * @param id,status,reason 参数
     * @return ShopEnterpriseInfos
     */
    Boolean toShopEnterpriseExamineConform(Integer id, Integer status, String reason);

}