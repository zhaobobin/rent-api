
package com.rent.service.product.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.rent.common.converter.product.ProductParameterConverter;
import com.rent.common.dto.product.ProductParameterDto;
import com.rent.dao.product.ProductParameterDao;
import com.rent.model.product.ProductParameter;
import com.rent.service.product.ProductParameterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 商品参数信息表Service
 *
 * @author youruo
 * @Date 2020-06-29 18:33
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ProductParameterServiceImpl implements ProductParameterService {

    private final ProductParameterDao productParameterDao;

    @Override
    public Long addProductParameter(ProductParameterDto request) {
        ProductParameter model = ProductParameterConverter.dto2Model(request);
        if (productParameterDao.save(model)) {
            return model.getId();
        } else {
            throw new MybatisPlusException("保存数据失败");
        }
    }


    @Override
    public List<ProductParameterDto> queryProductParameterList(String productId) {
        List<ProductParameter> productParameter = this.productParameterDao.list(new QueryWrapper<ProductParameter>().eq("product_id", productId).isNull("delete_time"));
        if (CollectionUtils.isNotEmpty(productParameter)) {
            return ProductParameterConverter.modelList2DtoList(productParameter);
        }
        return new ArrayList<>();
    }

    @Override
    public void batchinsert(String productId, List<ProductParameterDto> params) {
        if (StringUtils.isNotEmpty(productId) && CollectionUtils.isNotEmpty(params)) {
            //删除无用的标签
            Date now = new Date();
            ProductParameter ss = new ProductParameter();
            ss.setUpdateTime(now);
            ss.setDeleteTime(now);
            productParameterDao.update(ss, new QueryWrapper<ProductParameter>().eq("product_id", productId)
                    .isNull("delete_time"));
            params.forEach(item -> {
                this.addProductParameter(ProductParameterDto.builder().createTime(now).productId(productId).name(item.getName()).value(item.getValue()).build());
            });
        } else {
            log.warn("商品参数值录入数据为空");
        }
    }

    @Override
    public void deleteParamsProduct(String productId) {
        if (StringUtils.isNotEmpty(productId)) {
            //删除无用的标签
            Date now = new Date();
            ProductParameter ss = new ProductParameter();
            ss.setUpdateTime(now);
            ss.setDeleteTime(now);
            productParameterDao.update(ss, new QueryWrapper<ProductParameter>().eq("product_id", productId)
                    .isNull("delete_time"));
        } else {
            log.warn("商品参数值录入数据为空");
        }
    }


}