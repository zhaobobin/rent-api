
package com.rent.service.product.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.rent.common.converter.product.ProductGiveBackAddressesConverter;
import com.rent.common.converter.product.ShopGiveBackAddressesConverter;
import com.rent.common.dto.product.ProductGiveBackAddressesDto;
import com.rent.common.dto.product.ShopGiveBackAddressesDto;
import com.rent.dao.product.ProductGiveBackAddressesDao;
import com.rent.dao.product.ShopGiveBackAddressesDao;
import com.rent.dao.user.DistrictDao;
import com.rent.model.product.ProductGiveBackAddresses;
import com.rent.model.product.ShopGiveBackAddresses;
import com.rent.service.product.ProductGiveBackAddressesService;
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
 * 商品归还地址Service
 *
 * @author youruo
 * @Date 2020-06-16 15:12
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ProductGiveBackAddressesServiceImpl implements ProductGiveBackAddressesService {

    private final ProductGiveBackAddressesDao productGiveBackAddressesDao;
    private final ShopGiveBackAddressesDao shopGiveBackAddressesDao;
    private final DistrictDao districtDao;

    @Override
    public List<Integer> getAddIds(String productId) {
        List<Integer> result = productGiveBackAddressesDao.list(new QueryWrapper<ProductGiveBackAddresses>()
                .eq("item_id", productId)
                .isNull("delete_time")).stream().map(addresses -> addresses.getAddressId()).collect(Collectors.toList());
        return result;
    }

    @Override
    public void insertGiveBackAddress(String productId, List<Integer> addIds) {
        if (StringUtils.isNotEmpty(productId) && CollectionUtils.isNotEmpty(addIds)) {
            Date now = new Date();
            addIds.forEach(item -> {
                addProductGiveBackAddresses(ProductGiveBackAddressesDto.builder()
                        .createTime(now)
                        .itemId(productId)
                        .addressId(item)
                        .build());
            });
        }
    }

    @Override
    public void updateGiveBackAddress(String productId, List<Integer> addIds) {
        ProductGiveBackAddresses productGiveBackAddresses = new ProductGiveBackAddresses();
        productGiveBackAddresses.setDeleteTime(new Date());
        productGiveBackAddressesDao.update(productGiveBackAddresses,
                new QueryWrapper<ProductGiveBackAddresses>().eq("item_id", productId));
        insertGiveBackAddress(productId, addIds);
    }

    @Override
    public Integer addProductGiveBackAddresses(ProductGiveBackAddressesDto request) {
        ProductGiveBackAddresses model = ProductGiveBackAddressesConverter.dto2Model(request);
        if (productGiveBackAddressesDao.save(model)) {
            return model.getId();
        } else {
            throw new MybatisPlusException("保存数据失败");
        }
    }

    @Override
    public List<ShopGiveBackAddressesDto> queryProductGiveBackList(String itemId) {
        List<ProductGiveBackAddresses> addresses = productGiveBackAddressesDao.list(new QueryWrapper<ProductGiveBackAddresses>().eq("item_id",itemId).isNull("delete_time"));
        List<ShopGiveBackAddressesDto> result = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(addresses)){
            addresses.forEach(item->{
                ShopGiveBackAddressesDto dto = ShopGiveBackAddressesConverter.model2Dto(shopGiveBackAddressesDao.getById(item.getAddressId()));
                result.add(dto);
            });
        }
        return result;
    }

    @Override
    public List<ShopGiveBackAddressesDto> getGiveBack(String productId) {
        List<ShopGiveBackAddressesDto> result = new ArrayList<>();
        List<Integer> addressIds = this.getAddIds(productId);
        if (CollectionUtils.isNotEmpty(addressIds)) {
            //归还方式
            List<ShopGiveBackAddresses> shopGiveBackAddresses = this.shopGiveBackAddressesDao.list(new QueryWrapper<ShopGiveBackAddresses>()
                    .in("id", addressIds)
                    .isNull("delete_time")
            );
            if(CollectionUtils.isNotEmpty(shopGiveBackAddresses)){
                result =  ShopGiveBackAddressesConverter.modelList2DtoList(shopGiveBackAddresses);
                result.stream().map(item->{
                    String provinceStr = districtDao.getNameByDistrictId(null != item.getProvinceId() ?item.getProvinceId().toString() :null);
                    String cityStr = districtDao.getNameByDistrictId(null != item.getCityId() ?item.getCityId().toString() :null);
                    String areaStr = districtDao.getNameByDistrictId(null != item.getAreaId() ?item.getAreaId().toString() :null);
                    item.setProvinceStr(provinceStr);
                    item.setCityStr(cityStr);
                    item.setAreaStr(areaStr);
                    return item;
                }).collect(Collectors.toList());

            }
        }
        return result;
    }


}