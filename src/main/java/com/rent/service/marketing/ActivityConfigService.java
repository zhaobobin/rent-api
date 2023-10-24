package com.rent.service.marketing;


import com.rent.common.dto.marketing.ActivityConfigListDto;
import com.rent.common.dto.marketing.ActivityConfigReqDto;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author xiaotong
 */
public interface ActivityConfigService {
    /**
     * 查询列表
     * @param channelId
     * @return
     */
    List<ActivityConfigListDto> list(@RequestParam("channelId")String channelId);

    /**
     * 查询详情
     * @param id
     * @return
     */
    ActivityConfigListDto detail(@RequestParam("id")Long id);

    /**
     * 编辑栏目配置
     * @param dto
     * @return
     */
    Boolean update(@RequestBody ActivityConfigReqDto dto);
}