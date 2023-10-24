        
package com.rent.dao.product;

import com.rent.common.dto.product.ProductAdditionalServicesDto;
import com.rent.config.mybatis.IBaseDao;
import com.rent.model.product.ProductAdditionalServices;
import com.rent.model.product.ShopAdditionalServices;

import java.util.List;

/**
 * ProductAdditionalServicesDao
 *
 * @author youruo
 * @Date 2020-06-16 15:07
 */
public interface ProductAdditionalServicesDao extends IBaseDao<ProductAdditionalServices> {

    /**
     * 删除店铺增值服务商品
     * @param shopAdditionalServicesId
     */
    Boolean deleteProductAdditionalServices(Integer shopAdditionalServicesId);

    List<ProductAdditionalServices> batchSaveProductAdditional(List<ProductAdditionalServicesDto> servicesDtos, List<ShopAdditionalServices> afterAdditionals, String newestProductId);
}
