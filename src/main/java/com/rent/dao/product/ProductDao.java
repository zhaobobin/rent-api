        
package com.rent.dao.product;

import com.rent.common.dto.product.ShopProductSerachReqDto;
import com.rent.config.mybatis.IBaseDao;
import com.rent.model.product.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * ProductDao
 *
 * @author youruo
 * @Date 2020-06-16 15:06
 */
public interface ProductDao extends IBaseDao<Product> {


    Map<String, Object> selectProductCounts();

    List<Map<String, Object>>  selectShopProductListAndShop(@Param("shopId") String shopId,@Param("pageNumber") Integer pageNumber,@Param("pageSize") Integer pageSize);

    Integer countShopProductListAndShop(@Param("shopId") String shopId);


    Map<String, Object> selectBusinessPrdouctCounts(ShopProductSerachReqDto model);

    List<Product> getProductNames(List<String> productIdList);

    /**
     * 获取商品名称
     * @param productId
     * @return
     */
    String getProductName(String productId);

    /**
     * 切换展示与隐藏状态
     * @param hidden
     * @param productIdList
     */
    void updateProductHidden(Boolean hidden, List<String> productIdList);

    /**
     * 设置商品排序分数值为0
     */
    void cleanSortScore();

    /**
     * 修改商品排序分数值
     * @param productId
     * @param finalScore
     * @return
     */
    Integer updateProductSortScore(String productId,Integer finalScore);

    /**
     * 获取到排序分值是0的商品
     * @return
     */
    List<String> getSortScoreZeroProductIds();

    /**
     * 设置商品排序分数值
     * @param productId
     * @param score
     * @return
     */
    boolean setProductSortScore(String productId,Integer score);
}
