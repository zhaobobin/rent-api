package com.rent.service.marketing;


import com.rent.common.dto.marketing.CollectionInfo;
import com.rent.common.dto.marketing.UserCollectionDto;
import com.rent.common.dto.marketing.UserCollectionReqDto;

import java.util.List;

/**
 * 用户收藏表Service
 *
 * @author zhao
 * @Date 2020-07-22 15:28
 */
public interface UserCollectionService {

        /**
         * 新增用户收藏表
         * @param request 条件
         * @return boolean
         */
        Long addUserCollection(UserCollectionDto request);

        /**
         * 用户取消收藏
         * @param request
         * @return
         */
        Boolean cancelUserCollection(UserCollectionDto request);



        /**
         * 根据条件列表
         * @param request 实体对象
         * @return UserCollection
         */
        List<CollectionInfo> queryUserCollection(UserCollectionReqDto request);

        /**
         * 检查是否已经收藏
         * @param uid
         * @param resourceId
         * @param resourceType
         * @return
         */
        Boolean checkCollection(String uid,String resourceId,String resourceType);
}