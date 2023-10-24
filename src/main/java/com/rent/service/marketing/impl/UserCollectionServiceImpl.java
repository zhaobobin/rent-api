
package com.rent.service.marketing.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rent.common.converter.marketing.UserCollectionConverter;
import com.rent.common.dto.marketing.CollectionInfo;
import com.rent.common.dto.marketing.UserCollectionDto;
import com.rent.common.dto.marketing.UserCollectionReqDto;
import com.rent.dao.marketing.UserCollectionDao;
import com.rent.model.marketing.UserCollection;
import com.rent.service.marketing.UserCollectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 用户收藏表Service
 *
 * @author zhao
 * @Date 2020-07-22 15:28
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserCollectionServiceImpl implements UserCollectionService {

    private final UserCollectionDao userCollectionDao;


    @Override
    public Long addUserCollection(UserCollectionDto request) {
        List<Long> collectionIds = getCollectionId(request.getUid(),request.getResourceId(),request.getResourceType());
        if(CollectionUtils.isNotEmpty(collectionIds)){
            return collectionIds.get(0);
        }
        UserCollection model = UserCollectionConverter.dto2Model(request);
        model.setCreateTime(new Date());
        userCollectionDao.save(model);
        return model.getId();
    }

    @Override
    public Boolean cancelUserCollection(UserCollectionDto request) {
        List<Long> collectionIds = getCollectionId(request.getUid(),request.getResourceId(),request.getResourceType());
        if(!CollectionUtils.isEmpty(collectionIds)){
            List<UserCollection> models = new ArrayList<>(collectionIds.size());
            Date now = new Date();
            for (Long collectionId : collectionIds) {
                UserCollection collection = new UserCollection();
                collection.setId(collectionId);
                collection.setDeleteTime(now);
                models.add(collection);
            }
            userCollectionDao.updateBatchById(models);
        }
        return Boolean.TRUE;
    }

    @Override
    public List<CollectionInfo> queryUserCollection(UserCollectionReqDto request) {
        List<UserCollection> list = userCollectionDao.list(new QueryWrapper<UserCollection>()
                .select("resource_id,delete_time")
                .eq("uid",request.getUid())
                .eq("resource_type",request.getResourceType()));
        List<String> resourceIds = list.stream()
                .filter(e->e.getDeleteTime()==null)
                .map(UserCollection::getResourceId)
                .collect(Collectors.toList());

        if(CollectionUtils.isEmpty(resourceIds)){
            return Collections.emptyList();
        }

        List<UserCollection> collections = userCollectionDao.list(new QueryWrapper<UserCollection>()
                .select("resource_id,delete_time")
                .in("resource_id",resourceIds)
                .eq("resource_type",request.getResourceType()));
        Map<String,List<UserCollection>> group = collections.stream()
                .filter(e->e.getDeleteTime()==null)
                .collect(Collectors.groupingBy(UserCollection::getResourceId));

        List<CollectionInfo> collectionInfos=new ArrayList<>(resourceIds.size());
        for (String resourceId : resourceIds) {
            CollectionInfo collectionInfo = new CollectionInfo();
            collectionInfo.setResourceId(resourceId);
            collectionInfo.setNum(group.get(resourceId).size());
            collectionInfos.add(collectionInfo);
        }
        return collectionInfos;
    }

    @Override
    public Boolean checkCollection(String uid,String resourceId,String resourceType) {
        List<Long> collectionIds = getCollectionId(uid,resourceId,resourceType);
        return CollectionUtils.isNotEmpty(collectionIds);
    }

    /**
     * 判断用户是否已经收藏某个资源
     * @param uid
     * @param resourceId
     * @param resourceType
     * @return 已经收藏记录的ID
     */
    private List<Long> getCollectionId(String uid,String resourceId,String resourceType){
        List<UserCollection> list = userCollectionDao.list(new QueryWrapper<UserCollection>()
                .select("id,delete_time")
                .eq("uid",uid)
                .eq("resource_type",resourceType)
                .eq("resource_id",resourceId)
        );
        return list.stream().filter(e->e.getDeleteTime()==null).map(UserCollection::getId).collect(Collectors.toList());
    }
}