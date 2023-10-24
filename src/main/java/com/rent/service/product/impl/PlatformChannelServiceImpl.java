
package com.rent.service.product.impl;

import com.rent.config.outside.PlatformChannelDto;
import com.rent.service.product.PlatformChannelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 平台来源渠道Service
 *
 * @author youruo
 * @Date 2020-06-16 11:03
 */
@Service
@Slf4j
public class PlatformChannelServiceImpl implements PlatformChannelService {


    private static List<PlatformChannelDto> list = null;


    @Override
    public List<PlatformChannelDto> queryPlatformChannelDetailList() {
        if(list == null){
            list = new ArrayList<PlatformChannelDto>(){{
                add(new PlatformChannelDto());
            }};
        }
        return list;
    }

    @Override
    public List<String> getPlatformChannelList(List<String> channelIds) {
        if(list == null){
            list = new ArrayList<PlatformChannelDto>(){{
                add(new PlatformChannelDto());
            }};
        }
        return list.stream().map(PlatformChannelDto::getChannelName).collect(Collectors.toList());
    }

    @Override
    public PlatformChannelDto getPlatFormChannel(String channelId) {
        if(list == null){
            list = new ArrayList<PlatformChannelDto>(){{
                add(new PlatformChannelDto());
            }};
        }
        Map<String,PlatformChannelDto> map = list.stream().collect(Collectors.toMap(PlatformChannelDto::getChannelId, Function.identity()));
        return map.get(channelId);
    }

}