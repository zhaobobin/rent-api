package com.rent.service.marketing;


import com.rent.common.dto.marketing.IconConfigAddReqDto;
import com.rent.common.dto.marketing.IconConfigListDto;

import java.util.List;

/**
 * @author xiaotong
 */
public interface IconConfigService {

    /**
     * 金刚区列表
     * @param channelId
     * @return
     */
    List<IconConfigListDto> list(String channelId);

    /**
     * 编辑公告
     * @param id
     * @return
     */
    IconConfigListDto detail(Long id);

    /**
     * 增加金刚区配置
     * @param dto
     * @return
     */
    Boolean add(IconConfigAddReqDto dto);

    /**
     * 编辑金刚区
     * @param dto
     * @return
     */
    Boolean update(IconConfigAddReqDto dto);

    /**
     * 删除
     * @param id
     * @return
     */
    Boolean delete(Long id);
}