        
package com.rent.dao.product;

import com.rent.common.dto.product.ProductSpecValueDto;
import com.rent.config.mybatis.IBaseDao;
import com.rent.model.product.ProductSpecValue;

import java.util.List;

/**
 * ProductSpecValueDao
 *
 * @author youruo
 * @Date 2020-06-16 15:33
 */
public interface ProductSpecValueDao extends IBaseDao<ProductSpecValue> {

    List<ProductSpecValueDto> selectBySpecId(Integer specId);

    List<ProductSpecValueDto> selectBySpecIds(List<Integer> specIds);


}
