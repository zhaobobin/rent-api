package com.rent.dao.user.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.user.DistrictDao;
import com.rent.mapper.user.DistrictMapper;
import com.rent.model.user.District;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * DistrictDao
 *
 * @author zhao
 * @Date 2020-07-06 15:29
 */
@Repository
public class DistrictDaoImpl extends AbstractBaseDaoImpl<District, DistrictMapper> implements DistrictDao {


    @Override
    public Map<String, District> getByDistrictIdList(List<String> districtIds) {
        List<District> models = list(new QueryWrapper<District>().in("district_id", districtIds));
        return models.stream().collect(Collectors.toMap(District::getDistrictId, Function.identity()));
    }

    @Override
    public String getNameByDistrictId(String districtId) {
        District district = getOne(new QueryWrapper<District>().eq("district_id",districtId));
        return district==null ? null :district.getName();
    }

    @Override
    public String getDistrictId(String name, String parentId) {
        District district = getOne(new QueryWrapper<District>().select("district_id").eq("name",name).eq("parent_id",parentId));
        return district==null ? null :district.getDistrictId();
    }
}
