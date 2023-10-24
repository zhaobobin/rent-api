package com.rent.service.marketing;


import com.rent.common.dto.marketing.CubesConfigAddReqDto;
import com.rent.common.dto.marketing.CubesConfigListDto;

import java.util.List;

/**
 * @author xiaotong
 */
public interface CubesConfigService {

    /**
     * 查询列表
     * @param channelId
     * @return
     */
    List<CubesConfigListDto> list(String channelId, String type);

    /**
     * 查询详情
     * @param id
     * @return
     */
    CubesConfigListDto detail(Long id);

    /**
     * 添加Banner配置
     * @param dto
     * @return
     */
    Boolean add(CubesConfigAddReqDto dto);

    /**
     * 编辑Banner配置
     * @param dto
     * @return
     */
    Boolean update(CubesConfigAddReqDto dto);

    /**
     * 删除
     * @param dto
     * @return
     */
    Boolean delete(CubesConfigAddReqDto dto);
}