        
package com.rent.mapper.product;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rent.common.dto.product.ProductSpecValueDto;
import com.rent.model.product.ProductSpecValue;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * ProductSpecValueDao
 *
 * @author youruo
 * @Date 2020-06-16 15:33
 */
public interface ProductSpecValueMapper extends BaseMapper<ProductSpecValue>{

    List<ProductSpecValueDto> selectBySpecId(@Param("specId") Integer specId);

    List<ProductSpecValueDto> selectBySpecIds(@Param("specIds") List<Integer> specIds);

}