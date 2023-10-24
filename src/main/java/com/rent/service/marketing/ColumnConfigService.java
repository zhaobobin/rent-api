package com.rent.service.marketing;


import com.rent.common.dto.marketing.ColumnConfigListDto;
import com.rent.common.dto.marketing.ColumnConfigReqDto;

import java.util.List;

/**
 * @author xiaotong
 */
public interface ColumnConfigService {
    /**
     * 查询列表
     * @param channelId
     * @return
     */
    List<ColumnConfigListDto> list(String channelId,String type);

    /**
     * 查询详情
     * @param id
     * @return
     */
    ColumnConfigListDto detail(Long id);

    /**
     * 编辑栏目配置
     * @param dto
     * @return
     */
    Boolean update(ColumnConfigReqDto dto);
}