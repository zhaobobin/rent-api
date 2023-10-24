package com.rent.service.marketing;


import com.rent.common.dto.marketing.BannerConfigAddReqDto;
import com.rent.common.dto.marketing.BannerConfigListDto;

import java.util.List;

/**
 * @author xiaotong
 */
public interface BannerConfigService {

    /**
     * 查询列表
     * @param channelId
     * @return
     */
    List<BannerConfigListDto> list(String channelId, String type);

    /**
     * 查询详情
     * @param id
     * @return
     */
    BannerConfigListDto detail(Long id);

    /**
     * 添加Banner配置
     * @param dto
     * @return
     */
    Boolean add(BannerConfigAddReqDto dto);

    /**
     * 编辑Banner配置
     * @param dto
     * @return
     */
    Boolean update(BannerConfigAddReqDto dto);

    /**
     * 开关
     * @param dto
     * @return
     */
    Boolean open(BannerConfigAddReqDto dto);

    /**
     * 删除
     * @param dto
     * @return
     */
    Boolean delete(BannerConfigAddReqDto dto);
}