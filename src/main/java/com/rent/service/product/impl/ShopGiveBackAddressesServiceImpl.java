
package com.rent.service.product.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.rent.common.converter.product.ShopGiveBackAddressesConverter;
import com.rent.common.dto.product.ShopGiveBackAddressesDto;
import com.rent.common.dto.product.ShopGiveBackAddressesReqDto;
import com.rent.dao.product.ShopGiveBackAddressesDao;
import com.rent.dao.user.DistrictDao;
import com.rent.model.product.ShopGiveBackAddresses;
import com.rent.service.product.ShopGiveBackAddressesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 店铺归还地址表Service
 *
 * @author youruo
 * @Date 2020-06-17 10:47
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ShopGiveBackAddressesServiceImpl implements ShopGiveBackAddressesService {

    private final ShopGiveBackAddressesDao shopGiveBackAddressesDao;
    private final DistrictDao districtDao;

    @Override
    public Boolean addShopGiveBackAddresses(ShopGiveBackAddressesDto request) {
        ShopGiveBackAddresses model = ShopGiveBackAddressesConverter.dto2Model(request);
        model.setCreateTime(new Date());
        if (shopGiveBackAddressesDao.save(model)) {
            return Boolean.TRUE;
        } else {
            throw new MybatisPlusException("保存数据失败");
        }
    }

    @Override
    public Boolean modifyShopGiveBackAddresses(ShopGiveBackAddressesDto request) {
        request.setUpdateTime(new Date());
        return shopGiveBackAddressesDao.updateById(ShopGiveBackAddressesConverter.dto2Model(request));
    }

    @Override
    public ShopGiveBackAddressesDto queryShopGiveBackAddressesDetail(ShopGiveBackAddressesReqDto request) {
        ShopGiveBackAddresses shopGiveBackAddresses = shopGiveBackAddressesDao.getOne(new QueryWrapper<>(ShopGiveBackAddressesConverter.reqDto2Model(request)));
        return ShopGiveBackAddressesConverter.model2Dto(shopGiveBackAddresses);
    }

    @Override
    public List<ShopGiveBackAddressesDto> selectShopGiveBackByShopId(String shopId) {
        List<ShopGiveBackAddresses> list = this.shopGiveBackAddressesDao.list(new QueryWrapper<ShopGiveBackAddresses>()
                .eq("shop_id", shopId)
                .isNull("delete_time")
                .orderByDesc("create_time")
        );
        List<ShopGiveBackAddressesDto> result = ShopGiveBackAddressesConverter.modelList2DtoList(list);
        if (CollectionUtils.isNotEmpty(result)) {
            result = result.stream().map(item -> {
                String province = districtDao.getNameByDistrictId(null != item.getProvinceId() ?item.getProvinceId().toString() :null);
                String city = districtDao.getNameByDistrictId(null != item.getCityId() ?item.getCityId().toString() :null);
                String area = districtDao.getNameByDistrictId(null != item.getAreaId() ?item.getAreaId().toString() :null);
                item.setProvinceStr(province);
                item.setCityStr(city);
                item.setAreaStr(area);
                return item;
            }).collect(Collectors.toList());

        }
        return result;
    }

    @Override
    public Boolean delShopGiveBackAddressById(Integer id,String shopId) {
        ShopGiveBackAddresses address = this.shopGiveBackAddressesDao.getById(id);
        if (null != address) {
            if(address.getShopId().equals(shopId)){
                Date now = new Date();
                address.setUpdateTime(now);
                address.setDeleteTime(now);
                shopGiveBackAddressesDao.updateById(address);
            }
        }
        return Boolean.TRUE;
    }
}