        
package com.rent.common.converter.marketing;

import com.rent.common.dto.marketing.UserCollectionDto;
import com.rent.model.marketing.UserCollection;


/**
 * 用户收藏表Service
 *
 * @author zhao
 * @Date 2020-07-22 15:28
 */
public class UserCollectionConverter {

    /**
     * dto转do
     * @param dto
     * @return
     */
    public static UserCollection dto2Model(UserCollectionDto dto) {
        if (dto == null) {
            return null;
        }
        UserCollection model = new UserCollection();
        model.setId(dto.getId());
        model.setUid(dto.getUid());
        model.setResourceId(dto.getResourceId());
        model.setResourceType(dto.getResourceType());
        model.setCreateTime(dto.getCreateTime());
        model.setDeleteTime(dto.getDeleteTime());
        return model;
    }
}