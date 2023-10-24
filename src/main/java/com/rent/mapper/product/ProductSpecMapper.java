
package com.rent.mapper.product;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rent.model.product.ProductSpec;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * ProductSpecDao
 *
 * @author youruo
 * @Date 2020-06-16 15:32
 */
public interface ProductSpecMapper extends BaseMapper<ProductSpec> {
    List<Map> selectByItemId(@Param("itemId") String itemId);

    List<Map> selectProductopeSpecId(@Param("itemId") String itemId);

    List<Integer> selectProductopeSpecInfo(@Param("itemId") String itemId,@Param("opeSpecId") Integer opeSpecId);


    List<ProductSpec> selectRepairProductInfo(@Param("itemId") String itemId);

    List<Map> selectRepairProductInfoV1();
}