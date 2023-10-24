
package com.rent.service.product.impl;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.rent.common.converter.product.ProductSkuValuesConverter;
import com.rent.common.dto.product.ProductSkuValuesDto;
import com.rent.dao.product.ProductSkuValuesDao;
import com.rent.model.product.ProductSkuValues;
import com.rent.service.product.ProductSkuValuesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 商品sku规格属性value表Service
 *
 * @author youruo
 * @Date 2020-06-16 15:28
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ProductSkuValuesServiceImpl implements ProductSkuValuesService {

    private final ProductSkuValuesDao productSkuValuesDao;

    @Override
    public Integer addProductSkuValues(ProductSkuValuesDto request) {
        ProductSkuValues model = ProductSkuValuesConverter.dto2Model(request);
        if (productSkuValuesDao.save(model)) {
            return model.getId();
        } else {
            throw new MybatisPlusException("保存数据失败");
        }
    }
}