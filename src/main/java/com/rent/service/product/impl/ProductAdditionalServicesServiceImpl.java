
package com.rent.service.product.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.rent.common.converter.product.ProductAdditionalServicesConverter;
import com.rent.common.converter.product.ShopAdditionalServicesConverter;
import com.rent.common.dto.product.ProductAdditionalServicesDto;
import com.rent.common.dto.product.ShopAdditionalServicesDto;
import com.rent.dao.product.ProductAdditionalServicesDao;
import com.rent.dao.product.ShopAdditionalServicesDao;
import com.rent.model.product.ProductAdditionalServices;
import com.rent.model.product.ShopAdditionalServices;
import com.rent.service.product.ProductAdditionalServicesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 商品增值服务表Service
 *
 * @author youruo
 * @Date 2020-06-16 15:07
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ProductAdditionalServicesServiceImpl implements ProductAdditionalServicesService {

    private final ProductAdditionalServicesDao productAdditionalServicesDao;
    private final ShopAdditionalServicesDao shopAdditionalServicesDao;

    @Override
    public void insertAdditionalService(String productId, List<Integer> shopAdditionals) {
        if (StringUtils.isNotEmpty(productId) && CollectionUtils.isNotEmpty(shopAdditionals)) {
            Date now = new Date();
            shopAdditionals.forEach(item -> {
                addProductAdditionalServices(ProductAdditionalServicesDto.builder()
                        .createTime(now).productId(productId)
                        .shopAdditionalServicesId(item).build());
            });
        }
    }

    @Override
    public void updateAdditionalService(String productId, List<Integer> shopAdditionals) {

        ProductAdditionalServices additionalServices = new ProductAdditionalServices();
        additionalServices.setDeleteTime(new Date());
        productAdditionalServicesDao.update(additionalServices,
                new QueryWrapper<ProductAdditionalServices>().eq("product_id", productId));
        this.insertAdditionalService(productId, shopAdditionals);
    }

    @Override
    public Integer addProductAdditionalServices(ProductAdditionalServicesDto request) {
        ProductAdditionalServices model = ProductAdditionalServicesConverter.dto2Model(request);
        if (productAdditionalServicesDao.save(model)) {
            return model.getId();
        } else {
            throw new MybatisPlusException("保存数据失败");
        }
    }

    @Override
    public List<ProductAdditionalServicesDto> queryProductAdditionalServicesByProductId(String produnctId) {
        List<ProductAdditionalServices> list = productAdditionalServicesDao.list(
                new QueryWrapper<ProductAdditionalServices>()
                        .eq("product_id", produnctId)
                        .isNull("delete_time"));
        List<ProductAdditionalServicesDto> result = ProductAdditionalServicesConverter.modelList2DtoList(list);
        if(CollectionUtils.isNotEmpty(result)){
            result = result.stream().map(item ->{
                ShopAdditionalServicesDto dto = ShopAdditionalServicesConverter.model2Dto(shopAdditionalServicesDao.getById(item.getShopAdditionalServicesId())) ;
                item.setShopAdditionalServices(dto);
                return item;
            }).collect(Collectors.toList());
        }
        return result;
    }

    @Override
    public List<Integer> queryShopAdditionals(String produnctId) {
        List<Integer> result = new ArrayList<>();
        List<ProductAdditionalServices> list = productAdditionalServicesDao.list(
                new QueryWrapper<ProductAdditionalServices>()
                        .eq("product_id", produnctId)
                        .isNull("delete_time"));
        if(CollectionUtils.isNotEmpty(list)){
            result = list.stream().map(item ->{
                ShopAdditionalServices shop = shopAdditionalServicesDao.getOne(new QueryWrapper<ShopAdditionalServices>().eq("id",item.getShopAdditionalServicesId()).isNull("delete_time")) ;
                if(null!= shop){
                    return shop.getId();
                }
                return null;
            }).collect(Collectors.toList());
        }
        return result;
    }

}