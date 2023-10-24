        
package com.rent.dao.user;

import com.rent.common.dto.user.BackstageFunctionDto;
import com.rent.common.enums.user.EnumBackstageUserPlatform;
import com.rent.config.mybatis.IBaseDao;
import com.rent.model.user.BackstageFunction;

import java.util.List;


/**
 * BackstageFunctionDao
 *
 * @author zhao
 * @Date 2020-06-24 11:47
 */
public interface BackstageFunctionDao extends IBaseDao<BackstageFunction> {

    /**
     * 获取功能点列表
     * @param platform
     * @return
     */
    List<BackstageFunction> getFunctionList(EnumBackstageUserPlatform platform);

    /**
     * 获取所用的功能点
     * @param ids
     * @return
     */
    List<BackstageFunctionDto> getDtoByIds(List<Long> ids);

    List<BackstageFunction> getChildById(Long id);

}
