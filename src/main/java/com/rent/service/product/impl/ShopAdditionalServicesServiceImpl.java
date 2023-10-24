
package com.rent.service.product.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.converter.product.ShopAdditionalServicesConverter;
import com.rent.common.dto.product.ShopAdditionalServicesDto;
import com.rent.dao.product.ProductAdditionalServicesDao;
import com.rent.dao.product.ShopAdditionalServicesDao;
import com.rent.dao.product.ShopDao;
import com.rent.model.product.Shop;
import com.rent.model.product.ShopAdditionalServices;
import com.rent.service.product.ShopAdditionalServicesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 店铺增值服务表Service
 *
 * @author youruo
 * @Date 2020-06-17 10:35
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ShopAdditionalServicesServiceImpl implements ShopAdditionalServicesService {

    private final ShopAdditionalServicesDao shopAdditionalServicesDao;
    private final ProductAdditionalServicesDao productAdditionalServicesDao;
    private final ShopDao shopDao;

    @Override
    public Integer addShopAdditionalServices(ShopAdditionalServicesDto request) {
        ShopAdditionalServices model = ShopAdditionalServicesConverter.dto2Model(request);
        model.setCreateTime(new Date());
        if (shopAdditionalServicesDao.save(model)) {
            return model.getId();
        } else {
            throw new MybatisPlusException("保存数据失败");
        }
    }

    @Override
    public Boolean saveOrUpdateShopAdditionServices(ShopAdditionalServicesDto request) {
        if(null != request.getId()){
            this.modifyShopAdditionalServices(request);
        }else{
            this.addShopAdditionalServices(request);
        }
        return Boolean.TRUE;
    }

    @Override
    public Boolean deleteShopAdditionService(Integer id) {
        ShopAdditionalServices shopAdditionalServices = this.shopAdditionalServicesDao.getById(id);
        if(null != shopAdditionalServices){
            Date now = new Date();
            shopAdditionalServices.setUpdateTime(now);
            shopAdditionalServices.setDeleteTime(now);
            this.shopAdditionalServicesDao.updateById(shopAdditionalServices);
            this.productAdditionalServicesDao.deleteProductAdditionalServices(id);
        }
        return Boolean.TRUE;
    }

    @Override
    public Boolean modifyShopAdditionalServices(ShopAdditionalServicesDto request) {
        request.setCreateTime(new Date());
        return shopAdditionalServicesDao.updateById(ShopAdditionalServicesConverter.dto2Model(request));
    }


    @Override
    public Page<ShopAdditionalServicesDto> selectShopAdditionalServicesList(String name, String shopId,Integer pageNumber, Integer pageSize) {
        List<Shop> shopList = this.shopDao.list(new QueryWrapper<Shop>()
                .eq(StringUtils.isNotEmpty(shopId), "shop_id", shopId)
                .like(StringUtils.isNotEmpty(name), "name", name)
                .isNull("delete_time")
                .orderByDesc("update_time")
        );
        List list = new ArrayList();
        if (CollectionUtils.isNotEmpty(shopList)) {
            for (Shop shop : shopList) {
                list.add(shop.getShopId());
            }
        }
        Page<ShopAdditionalServices> shopAdditionalServicesPage = this.shopAdditionalServicesDao.page(new Page<>(pageNumber, pageSize), new QueryWrapper<ShopAdditionalServices>()
                .in(CollectionUtils.isNotEmpty(list), "shop_id", list)
                .isNull("delete_time")
        );
        List<ShopAdditionalServicesDto> records = ShopAdditionalServicesConverter.modelList2DtoList(shopAdditionalServicesPage.getRecords());
        if (CollectionUtils.isNotEmpty(records)) {
            for (ShopAdditionalServicesDto shopAdditionalServices : records) {
                Shop shop = this.shopDao.getOne(new QueryWrapper<Shop>()
                        .eq("shop_id", shopAdditionalServices.getShopId())
                        .isNull("delete_time")
                );
                if (shop != null) {
                    shopAdditionalServices.setShopName(shop.getName());
                }
            }
        }
        return new Page<ShopAdditionalServicesDto>(shopAdditionalServicesPage.getCurrent(), shopAdditionalServicesPage.getSize(), shopAdditionalServicesPage.getTotal()).setRecords(records);
    }
}