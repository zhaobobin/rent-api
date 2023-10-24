        
package com.rent.mapper.product;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rent.common.dto.product.ShopProductSerachReqDto;
import com.rent.model.product.Product;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * ProductDao
 *
 * @author youruo
 * @Date 2020-06-16 15:06
 */
public interface ProductMapper extends BaseMapper<Product> {



    Map<String, Object> selectProductCounts();

    Map<String, Object> selectBusinessPrdouctCounts(@Param("model") ShopProductSerachReqDto model);

    List<Map<String, Object>>  selectShopProductListAndShop(@Param("shopId") String shopId,@Param("pageNumber") Integer pageNumber,@Param("pageSize") Integer pageSize);

    Integer countShopProductListAndShop(@Param("shopId") String shopId);

    /**
     * 设置商品排序分数值为0
     */
    @Update("update ct_product set sort_score = 0")
    void cleanSortScore();

}