    
package com.rent.dao.marketing.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.marketing.MaterialCenterItemDao;
import com.rent.mapper.marketing.MaterialCenterItemMapper;
import com.rent.model.marketing.MaterialCenterItem;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository
public class MaterCenterItemDaoImpl extends AbstractBaseDaoImpl<MaterialCenterItem, MaterialCenterItemMapper> implements MaterialCenterItemDao {


    @Override
    public void deleteCategoryById(Long id) {
        MaterialCenterItem item = new MaterialCenterItem();
        item.setDeleteTime(new Date());
        update(item,new QueryWrapper<MaterialCenterItem>().eq("category_id",id));

    }

    @Override
    public Map<Long,String> getMaterialCenterFileUrl(List<Long> ids) {
        Map<Long,String> params = new HashMap<Long,String>();
        if (CollectionUtils.isNotEmpty(ids)) {
            List<MaterialCenterItem> materials = this.list(new QueryWrapper<MaterialCenterItem>().select("id", "file_url")
                    .in("id", ids)
                    .isNull("delete_time")
                    .orderByDesc("create_time"));
            if (CollectionUtils.isNotEmpty(materials)) {
                materials.forEach(item -> {
                    params.put(item.getId(), item.getFileUrl());
                });
            }
        }
        return params;

    }
}
