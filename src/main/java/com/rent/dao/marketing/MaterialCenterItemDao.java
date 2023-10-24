        
package com.rent.dao.marketing;

import com.rent.config.mybatis.IBaseDao;
import com.rent.model.marketing.MaterialCenterItem;

import java.util.List;
import java.util.Map;


/**
 * @author zhaowenchao
 */
public interface MaterialCenterItemDao extends IBaseDao<MaterialCenterItem> {

    /**
     * 根据分类删除素材信息
     * @param id
     */
    void deleteCategoryById(Long id);


    Map<Long,String> getMaterialCenterFileUrl(List<Long> ids);


}
