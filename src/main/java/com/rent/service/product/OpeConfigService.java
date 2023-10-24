
package com.rent.service.product;

import com.rent.model.product.OpeConfig;

import java.util.List;


/**
 * 首页运营配置表Service
 *
 * @author youruo
 * @Date 2020-06-30 15:13
 */
public interface OpeConfigService {


    /**
     * 统一修改不同类型首页配置来源渠道
     * @param indexType
     * @param channelIds
     */
    void updateOpeConfig(Integer indexType,Integer configId,List<String> channelIds);

    /**
     * 查询配置列表
     *
     * @param indexType
     * @param configId
     * @return
     */
    List<OpeConfig> getOpeConfigList(Integer indexType, Integer configId, String channelId);


    /**
     * 删除首页下来源渠道
     * @param configId
     */
    void deleteOpeConfig(Integer indexType,Integer configId);


}