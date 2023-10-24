package com.rent.dao.marketing;

import com.rent.common.enums.marketing.PageElementConfigTypeEnum;
import com.rent.config.mybatis.IBaseDao;
import com.rent.model.marketing.PageElementConfig;

import java.util.List;

/**
 * @author zhaowenchao
 */
public interface PageElementConfigDao extends IBaseDao<PageElementConfig> {

    /**
     * 根据渠道ID和type查询获取
     * @param channelId
     * @param type
     * @return
     */
    List<PageElementConfig> getByChannelIDAndType(String channelId, PageElementConfigTypeEnum type);


}
