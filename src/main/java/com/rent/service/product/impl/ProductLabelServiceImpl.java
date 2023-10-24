
package com.rent.service.product.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.rent.common.converter.product.ProductLabelConverter;
import com.rent.common.dto.product.ProductLabelDto;
import com.rent.dao.product.ProductLabelDao;
import com.rent.model.product.ProductLabel;
import com.rent.service.product.ProductLabelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * 商品租赁标签Service
 *
 * @author youruo
 * @Date 2020-06-29 18:32
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ProductLabelServiceImpl implements ProductLabelService {

    private final ProductLabelDao productLabelDao;

    @Override
    public Integer addProductLabel(ProductLabelDto request) {
        ProductLabel model = ProductLabelConverter.dto2Model(request);
        if (productLabelDao.save(model)) {
            return model.getId();
        } else {
            throw new MybatisPlusException("保存数据失败");
        }
    }

    @Override
    public List<String> getProductLabelList(String productId) {
        List<String> result = new ArrayList<>();
        List<ProductLabel> productLabels = this.productLabelDao.list(new QueryWrapper<ProductLabel>()
                .eq("item_id", productId)
                .isNull("delete_time"));
        if (CollectionUtils.isNotEmpty(productLabels)) {
            result = productLabels.stream().map(ProductLabel::getLabel).collect(toList());
        }
        return result;
    }

    @Override
    public void batchProduct(String productId, List<String> labels) {
        if(StringUtils.isNotEmpty(productId)&& CollectionUtils.isNotEmpty(labels)){
            //删除无用的标签
            Date now = new Date();
            ProductLabel ss = new ProductLabel();
            ss.setUpdateTime(now);
            ss.setDeleteTime(now);
            productLabelDao.update(ss, new QueryWrapper<ProductLabel>().eq("item_id", productId)
                    .isNull("delete_time"));
            labels.forEach(item->{
                this.addProductLabel(ProductLabelDto.builder().createTime(now).itemId(productId).label(item).build());
            });

        }else{
            log.warn("标签商品参数为空");
        }
    }

    @Override
    public void deleteLabelProduct(String productId) {
        if(StringUtils.isNotEmpty(productId)){
            //删除无用的标签
            Date now = new Date();
            ProductLabel ss = new ProductLabel();
            ss.setUpdateTime(now);
            ss.setDeleteTime(now);
            productLabelDao.update(ss, new QueryWrapper<ProductLabel>().eq("item_id", productId)
                    .isNull("delete_time"));
        }else{
            log.warn("标签商品参数为空");
        }
    }


}