
package com.rent.service.product.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rent.common.dto.product.ProductImageDto;
import com.rent.common.dto.product.ProductImageReqDto;
import com.rent.dao.product.OpeCustomProductDao;
import com.rent.model.product.OpeCustomProduct;
import com.rent.model.product.Product;
import com.rent.service.product.OpeCustomProductService;
import com.rent.service.product.ProductImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 自定义tab产品挂载表Service
 *
 * @author youruo
 * @Date 2020-06-16 10:00
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class OpeCustomProductServiceImpl implements OpeCustomProductService {

    private final OpeCustomProductDao opeCustomProductDao;
    private final ProductImageService productImageService;

    @Override
    public Boolean deleteProduct(String itemId) {
        Date now = new Date();
        QueryWrapper<OpeCustomProduct> wh = new QueryWrapper<>();
        wh.eq("item_id", itemId);
        wh.isNull("delete_time");
        OpeCustomProduct ss = new OpeCustomProduct();
        ss.setUpdateTime(now);
        ss.setDeleteTime(now);
        this.opeCustomProductDao.update(ss, wh);
        return Boolean.TRUE;
    }


    @Override
    public void repairCusProduct(Integer status, Product product, String image) {
        log.info("修复tab下商品数据");
        Date now = new Date();
        QueryWrapper<OpeCustomProduct> wh = new QueryWrapper<>();
        wh.eq("item_id", product.getProductId());
        wh.isNull("delete_time");
        OpeCustomProduct ss = new OpeCustomProduct();
        ss.setUpdateTime(now);
        ss.setStatus(status);
        //生效-修复商品数据,失效-删除商品数据
        ss.setName(product.getName());
        ss.setMinDays(null != product.getMinRentCycle() ? product.getMinRentCycle().toString() : null);
        ss.setSale(null != product.getSale() ? product.getSale().toString() : null);
        ss.setPrice(null != product.getSale() ? product.getSale().toString() : null);
        ss.setImage(image);
        ss.setOldNewDegree(null != product.getOldNewDegree() ? product.getOldNewDegree().toString() : null);
        if (StringUtils.isEmpty(image)) {
            ProductImageDto dto = productImageService.queryProductImageDetail(ProductImageReqDto.builder().productId(product.getProductId()).build());
            if (null != dto) {
                ss.setImage(dto.getSrc());
            }
        }
        this.opeCustomProductDao.update(ss, wh);

    }

}