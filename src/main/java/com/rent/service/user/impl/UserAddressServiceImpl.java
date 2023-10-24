        
package com.rent.service.user.impl;

import com.rent.common.converter.user.UserAddressConverter;
import com.rent.common.dto.api.request.AddUserAddressReq;
import com.rent.common.dto.api.request.ModifyUserAddressReq;
import com.rent.common.dto.api.resp.ListUserAddressResp;
import com.rent.common.dto.user.UserAddressDto;
import com.rent.common.util.StringUtil;
import com.rent.dao.user.UserAddressDao;
import com.rent.exception.HzsxBizException;
import com.rent.model.user.UserAddress;
import com.rent.service.user.DistrictService;
import com.rent.service.user.UserAddressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserAddressServiceImpl implements UserAddressService {

    private final UserAddressDao userAddressDao;
    private final DistrictService districtService;

    @Override
    public List<ListUserAddressResp> getUserAddress(String uid) {
        List<ListUserAddressResp> respList = UserAddressConverter.modelList2ListResp(userAddressDao.getUserAddress(uid));
        for (ListUserAddressResp resp : respList) {
            resp.setProvinceStr(districtService.getNameByDistrictId(resp.getProvince().toString()));
            resp.setCityStr(districtService.getNameByDistrictId(resp.getCity().toString()));
            resp.setAreaStr(districtService.getNameByDistrictId(resp.getArea().toString()));
        }
        return respList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long addUserAddress(AddUserAddressReq request) {
        UserAddress model = UserAddressConverter.addReq2Model(request);
        if(model.getProvince()==null){
            String provinceId = districtService.getDistrictId(request.getProvinceStr(),"100000");
            if(StringUtil.isEmpty(provinceId)){
                throw new HzsxBizException("-1","查询省份失败");
            }
            String cityId = districtService.getDistrictId(request.getCityStr(),provinceId);
            if(StringUtil.isEmpty(cityId)){
                throw new HzsxBizException("-1","查询城市失败");
            }
            String areaId = districtService.getDistrictId(request.getAreaStr(),cityId);
            if(!StringUtil.isEmpty(areaId)){
                model.setArea(Integer.parseInt(areaId));
            }
            model.setProvince(Integer.parseInt(provinceId));
            model.setCity(Integer.parseInt(cityId));
            model.setIsDefault(request.getIsDefault());
        }
        if (model.getIsDefault()){
            userAddressDao.updateUserAddressNoneDefault(model.getUid());
        }
        userAddressDao.save(model);
        return model.getId();
     }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean modifyUserAddress(ModifyUserAddressReq request) {
        UserAddress model = UserAddressConverter.modifyReq2Model(request);
        if (model.getIsDefault()){
            userAddressDao.updateUserAddressNoneDefault(model.getUid());
        }
        return userAddressDao.updateById(model);
    }

    @Override
    public UserAddressDto getUserAddressById(Long id) {
        UserAddressDto dto = UserAddressConverter.model2Dto(userAddressDao.getById(id));
        dto.setProvinceStr(districtService.getNameByDistrictId(dto.getProvince().toString()));
        dto.setCityStr(districtService.getNameByDistrictId(dto.getCity().toString()));
        dto.setAreaStr(districtService.getNameByDistrictId(dto.getArea().toString()));
        return dto;
    }

    @Override
    public Boolean deleteUserAddress(Long id) {
        UserAddress userAddress = new UserAddress();
        userAddress.setId(id).setDeleteTime(new Date());
        return userAddressDao.updateById(userAddress);
    }
}