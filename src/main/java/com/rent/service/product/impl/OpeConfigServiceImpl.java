
package com.rent.service.product.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rent.dao.product.OpeConfigDao;
import com.rent.model.product.OpeConfig;
import com.rent.service.product.OpeConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 首页运营配置表Service
 *
 * @author youruo
 * @Date 2020-06-30 15:13
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class OpeConfigServiceImpl implements OpeConfigService {

    private final OpeConfigDao opeConfigDao;

    @Override
    public void updateOpeConfig(Integer indexType, Integer configId, List<String> channelIds) {
        //删除原渠道来源，录入新渠道来源
        Date now = new Date();
        OpeConfig opeConfig = new OpeConfig();
        opeConfig.setDeleteTime(now);
        opeConfigDao.update(opeConfig,new QueryWrapper<OpeConfig>().eq("index_type", indexType).eq("config_id", configId).isNull("delete_time"));

        for (String channelId : channelIds) {
            OpeConfig ope = new OpeConfig();
            ope.setCreateTime(now);
            ope.setChannelId(channelId);
            ope.setConfigId(configId);
            ope.setIndexType(indexType);
            opeConfigDao.save(ope);
        }
    }

    @Override
    public List<OpeConfig> getOpeConfigList(Integer indexType, Integer configId, String channelId) {
        return this.opeConfigDao.list(new QueryWrapper<OpeConfig>()
                .eq(null != indexType, "index_type", indexType)
                .eq(null != configId,"config_id", configId)
                .eq(StringUtils.isNotEmpty(channelId), "channel_id", channelId)
                .isNull("delete_time")
                .groupBy("channel_id"));
    }

    @Override
    public void deleteOpeConfig(Integer indexType, Integer configId) {
        Date now = new Date();
        OpeConfig opeConfig = new OpeConfig();
        opeConfig.setDeleteTime(now);
        opeConfigDao.update(opeConfig, new QueryWrapper<OpeConfig>().eq("index_type", indexType)
                .eq("config_id", configId)
                .isNull("delete_time"));
    }

}