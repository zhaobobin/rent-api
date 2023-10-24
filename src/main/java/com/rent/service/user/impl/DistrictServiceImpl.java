        
package com.rent.service.user.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rent.common.dto.user.AreaDto;
import com.rent.common.dto.user.CityDto;
import com.rent.common.dto.user.ProvinceDto;
import com.rent.dao.user.DistrictDao;
import com.rent.model.user.District;
import com.rent.service.user.DistrictService;
import com.rent.util.RedisUtil;
import com.rent.util.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 区域表Service
 *
 * @author zhao
 * @Date 2020-07-06 15:29
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class DistrictServiceImpl implements DistrictService {

    private static final String redisKey = "district";
    private static final String redisItem = "item::";

    private final DistrictDao districtDao;

    @Override
    public String getNameByDistrictId(String districtId) {
        if(StringUtils.isEmpty(districtId)){
            return null;
        }
        String name = (String) RedisUtil.hget(redisKey,redisItem+districtId);
        if(!StringUtil.isEmpty(name)){
            return name;
        }
        name =  districtDao.getNameByDistrictId(districtId);
        if(StringUtil.isNotEmpty(name)){
            RedisUtil.hset(redisKey,redisItem+districtId,name);
        }
        return name;
    }

    @Override
    public String getDistrictId(String name, String parentId) {
        return districtDao.getDistrictId(name,parentId);
    }

    @Override
    public Map<String, String> getDistinctName(List<String> districtIds) {
        Map<String, String> map = new HashMap<>();
        for (String districtId : districtIds) {
            map.put(districtId,getNameByDistrictId(districtId));
        }
        return map;
    }

    @Override
    public List<ProvinceDto> selectDistrict() {
        List<ProvinceDto> provinceDTOS = new ArrayList<>();
        try {
            List<District> provinces = districtDao.list(new QueryWrapper<District>().eq("parent_id", "100000"));
            for (District d : provinces) {
                List<CityDto> cityDTOS = new ArrayList<>();
                ProvinceDto provinceDTO = new ProvinceDto();
                provinceDTO.setValue(d.getDistrictId());
                provinceDTO.setName(d.getName());
                List<District> city =  districtDao.list(new QueryWrapper<District>()
                        .eq("parent_id", d.getDistrictId()));
                for (District c : city) {
                    List<AreaDto> areaDTOS = new ArrayList<>();
                    CityDto cityDTO = new CityDto();
                    cityDTO.setValue(c.getDistrictId());
                    cityDTO.setName(c.getName());
                    List<District> area = districtDao.list(new QueryWrapper<District>()
                            .eq("parent_id", c.getDistrictId()));
                    for (District a : area) {
                        AreaDto areaDTO = new AreaDto();
                        areaDTO.setValue(a.getDistrictId());
                        areaDTO.setName(a.getName());
                        areaDTOS.add(areaDTO);
                    }
                    cityDTO.setSubList(areaDTOS);
                    cityDTOS.add(cityDTO);
                    provinceDTO.setSubList(cityDTOS);
                }
                provinceDTOS.add(provinceDTO);
            }
            return provinceDTOS;
        } catch (Exception e) {
            log.error("业务出现异常", e);
        }
        return provinceDTOS;
    }
}