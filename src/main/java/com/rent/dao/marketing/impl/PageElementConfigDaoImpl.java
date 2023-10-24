package com.rent.dao.marketing.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rent.common.enums.marketing.PageElementConfigTypeEnum;
import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.marketing.PageElementConfigDao;
import com.rent.mapper.marketing.PageElementConfigMapper;
import com.rent.model.marketing.PageElementConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author zhaowenchao
 */
@Service
@Slf4j
public class PageElementConfigDaoImpl extends AbstractBaseDaoImpl<PageElementConfig, PageElementConfigMapper> implements PageElementConfigDao {


    @Override
    public List<PageElementConfig> getByChannelIDAndType(String channelId, PageElementConfigTypeEnum type) {
        return list(new QueryWrapper<PageElementConfig>()
                .eq("channel_id",channelId)
                .eq("type",type)
                .isNull("delete_time").orderByAsc("sort_num")
        );
    }
}
