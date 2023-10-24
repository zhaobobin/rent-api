package com.rent.service.marketing;

import com.rent.common.dto.marketing.HotSearchSaveReqDto;

import java.util.List;

/**
 * @author zhaowenchao
 */
public interface HotSearchConfigService {

    /**
     * 查询
     * @param channelId
     * @return
     */
    List<String> list(String channelId);

    /**
     * 保存热门板块内容
     * @param request
     * @return
     */
    Boolean save(HotSearchSaveReqDto request);

}