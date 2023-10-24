
package com.rent.dao.product.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rent.common.dto.product.ShopProductSerachReqDto;
import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.product.ProductDao;
import com.rent.exception.HzsxBizException;
import com.rent.mapper.product.ProductMapper;
import com.rent.model.product.Product;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * ProductDao
 *
 * @author youruo
 * @Date 2020-06-16 15:06
 */
@Repository
public class ProductDaoImpl extends AbstractBaseDaoImpl<Product, ProductMapper> implements ProductDao {

    @Override
    public Map<String, Object> selectProductCounts() {
        return baseMapper.selectProductCounts();
    }

    @Override
    public List<Map<String, Object>> selectShopProductListAndShop(String shopId, Integer pageNumber, Integer pageSize) {
        return baseMapper.selectShopProductListAndShop(shopId, pageNumber, pageSize);
    }

    @Override
    public Integer countShopProductListAndShop(String shopId) {
        return baseMapper.countShopProductListAndShop(shopId);
    }

    @Override
    public Map<String, Object> selectBusinessPrdouctCounts(ShopProductSerachReqDto model) {
         return  baseMapper.selectBusinessPrdouctCounts(model);
    }

    @Override
    public List<Product> getProductNames(List<String> productIdList) {
        return list(new QueryWrapper<Product>().select("name","product_id").in("product_id",productIdList));
    }

    @Override
    public String getProductName(String productId) {
        Product product = getOne(new QueryWrapper<Product>().select("name").eq("product_id",productId).isNull("delete_time"));
        return product.getName();
    }

    @Override
    public Integer updateProductSortScore(String productId,Integer finalScore) {
        Product product = getOne(new QueryWrapper<Product>().select("id,sort_score").eq("product_id",productId).isNull("delete_time"));
        if(product==null){
            throw new HzsxBizException("-1","未查询到对应商品");
        }
        Integer originScore = product.getSortScore();
        product.setSortScore(finalScore);
        updateById(product);
        return originScore;
    }

    @Override
    public List<String> getSortScoreZeroProductIds() {
        List<Product> list  = list(new QueryWrapper<Product>().select("product_id").eq("sort_score",0));
        return list.stream().map(Product::getProductId).collect(Collectors.toList());
    }

    @Override
    public boolean setProductSortScore(String productId, Integer score) {
        Product product = getOne(new QueryWrapper<Product>().select("id").eq("product_id",productId).isNull("delete_time"));
        if(product!=null){
            product.setSortScore(score);
            updateById(product);
        }
        return Boolean.TRUE;
    }

    @Override
    public void cleanSortScore() {
        baseMapper.cleanSortScore();
    }


    @Override
    public void updateProductHidden(Boolean hidden,List<String> productIdList) {
        Product product = new Product();
        product.setHidden(hidden);
        update(product, new QueryWrapper<Product>().in("product_id", productIdList));
    }
}
