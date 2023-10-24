        
package com.rent.mapper.product;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rent.model.product.OpeCategoryProduct;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * OpeCategoryProductDao
 *
 * @author youruo
 * @Date 2020-06-15 10:28
 */
public interface OpeCategoryProductMapper extends BaseMapper<OpeCategoryProduct>{
    /**
     * 设置商品排序分数值
     * @param productId
     * @param score
     */
    @Update("update ct_ope_category_product set sort_score = #{score} where item_id=#{itemId}")
    void setProductSortScore(@Param("itemId") String productId, @Param("score") Integer score);

    /**
     * 清零排序分数
     */
    @Update("update ct_ope_category_product set sort_score = 0")
    void cleanSortScore();
}