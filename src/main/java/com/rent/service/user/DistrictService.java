        
package com.rent.service.user;



import com.rent.common.dto.user.ProvinceDto;

import java.util.List;
import java.util.Map;

/**
 * 区域表Service
 *
 * @author zhao
 * @Date 2020-07-06 15:29
 */
public interface DistrictService {


        /**
         * 根据districtId获取区域名称
         * @param districtId 条件
         * @return boolean
         */
        String getNameByDistrictId(String districtId);

        /**
         * 根据名称和父类ID获取地区ID
         * @param name
         * @param parentId
         * @return
         */
        String getDistrictId(String name,String parentId);


        /**
         * 根据区域ID获取区域名称
         * @param districtIds
         * @return map key:distinctId--->value:distinctName
         */
        Map<String,String> getDistinctName(List<String> districtIds);

        /**
         * 获取地区名称信息
         * @return
         */
        List<ProvinceDto> selectDistrict();








}