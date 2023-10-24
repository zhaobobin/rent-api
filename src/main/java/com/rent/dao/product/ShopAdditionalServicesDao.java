        
package com.rent.dao.product;

import com.rent.common.dto.order.OrderAdditionalServicesDto;
import com.rent.common.dto.product.AdditonalDto;
import com.rent.config.mybatis.IBaseDao;
import com.rent.model.product.ShopAdditionalServices;

import java.util.List;

/**
 * ShopAdditionalServicesDao
 *
 * @author youruo
 * @Date 2020-06-17 10:35
 */
public interface ShopAdditionalServicesDao extends IBaseDao<ShopAdditionalServices> {


    AdditonalDto saveBatchAdditional(List<Integer> shopAdditionalServicesId, String transferShopId, List<OrderAdditionalServicesDto> orderAdditionalServicesDtos);

}
