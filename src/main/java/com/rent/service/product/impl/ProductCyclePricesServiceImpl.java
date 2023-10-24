
package com.rent.service.product.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.rent.common.converter.product.ProductCyclePricesConverter;
import com.rent.common.dto.product.ProductCyclePricesDto;
import com.rent.dao.product.ProductCyclePricesDao;
import com.rent.exception.HzsxBizException;
import com.rent.model.product.ProductCyclePrices;
import com.rent.service.product.ProductCyclePricesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 商品周期价格表Service
 *
 * @author youruo
 * @Date 2020-06-16 15:08
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ProductCyclePricesServiceImpl implements ProductCyclePricesService {

    private final ProductCyclePricesDao productCyclePricesDao;

    @Override
    public ProductCyclePrices getSkuCyclePrice(Long skuId, int days) {

        ProductCyclePrices productCyclePrices = productCyclePricesDao.getOne(new QueryWrapper<ProductCyclePrices>()
                .eq("days", days)
                .eq("sku_id", skuId)
                .isNull("delete_time"));

        if (productCyclePrices == null) {
            throw new HzsxBizException("-1", "未查询到周期价格信息");
        }
        return productCyclePrices;
    }

    @Override
    public List<ProductCyclePricesDto> selectCyclePricesBySkuId(Long skuId) {
        List<ProductCyclePrices> productCyclePrices = this.productCyclePricesDao.list(
                new QueryWrapper<ProductCyclePrices>().eq("sku_id", skuId).isNull("delete_time").orderByAsc("price"));
        return ProductCyclePricesConverter.modelList2DtoList(productCyclePrices);
    }

    @Override
    public ProductCyclePricesDto getLowestProductCyclePricesByItemId(String itemId) {
        ProductCyclePrices productCyclePrices = this.productCyclePricesDao.getOne(new QueryWrapper<ProductCyclePrices>()
                .eq("item_id", itemId).isNull("delete_time")
                .orderByAsc("price")
                .last("limit 0,1")
        );
        return ProductCyclePricesConverter.model2Dto(productCyclePrices);
    }

    @Override
    public void insertCyclePrices(List<ProductCyclePricesDto> cycs, Integer skuId, String productId) {
        if (CollectionUtils.isNotEmpty(cycs)) {
            Date now = new Date();
            cycs.forEach(item -> {
                addProductCyclePrices(ProductCyclePricesDto.builder()
                        .skuId(Long.valueOf(skuId))
                        .itemId(productId)
                        .createTime(now)
                        .days(item.getDays())
                        .price(item.getPrice())
                        .salePrice(null == item.getSalePrice() ? new BigDecimal(0) : item.getSalePrice())
                        .sesameDeposit(item.getSesameDeposit())
                        .build());
            });
        }
    }

    @Override
    public void deleteCyclePrices(String productId) {
        QueryWrapper<ProductCyclePrices> wh = new QueryWrapper<>();
        wh.eq("item_id", productId);
        wh.isNull("delete_time");
        ProductCyclePrices ss = new ProductCyclePrices();
        ss.setDeleteTime(new Date());
        productCyclePricesDao.update(ss, wh);
    }

    @Override
    public Integer addProductCyclePrices(ProductCyclePricesDto request) {
        ProductCyclePrices model = ProductCyclePricesConverter.dto2Model(request);
        if (productCyclePricesDao.save(model)) {
            return model.getId();
        } else {
            throw new MybatisPlusException("保存数据失败");
        }
    }
}