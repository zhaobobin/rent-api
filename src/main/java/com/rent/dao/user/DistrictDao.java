        
package com.rent.dao.user;

import com.rent.config.mybatis.IBaseDao;
import com.rent.model.user.District;

import java.util.List;
import java.util.Map;

/**
 * DistrictDao
 *
 * @author zhao
 * @Date 2020-07-06 15:29
 */
public interface DistrictDao extends IBaseDao<District> {

    /**
     * 根据districtId获取区域名称
     * @param districtIds
     * @return
     */
    Map<String,District> getByDistrictIdList(List<String> districtIds);


    /**
     * 根据districtId获取区域名称
     * @param districtId
     * @return
     */
    String getNameByDistrictId(String districtId);

    /**
     * 根据名称和父类ID获取地区ID
     * @param name
     * @param parentId
     * @return
     */
    String getDistrictId(String name, String parentId);
}
