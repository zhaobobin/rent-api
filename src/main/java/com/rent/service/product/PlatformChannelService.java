        
package com.rent.service.product;

import com.rent.config.outside.PlatformChannelDto;

import java.util.List;


/**
 * 平台来源渠道Service
 *
 * @author youruo
 * @Date 2020-06-16 11:03
 */
public interface PlatformChannelService {

        /**
         * 查询来源渠道集合
         * @return
         */
        List<PlatformChannelDto> queryPlatformChannelDetailList();

        /**
         * 查询来源渠道名称
         * @param channelIds
         * @return
         */
        List<String> getPlatformChannelList(List<String> channelIds);

        /**
         *
         * @param channelId
         * @return
         */
        PlatformChannelDto getPlatFormChannel(String channelId);
}