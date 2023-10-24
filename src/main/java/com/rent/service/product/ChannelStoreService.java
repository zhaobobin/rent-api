
package com.rent.service.product;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.dto.product.ChannelSplitBillDto;
import com.rent.common.dto.product.ChannelSplitBillReqDto;
import com.rent.common.dto.product.ChannelStoreDto;
import com.rent.common.dto.product.ChannelStoreReqDto;

import java.util.List;

/**
 * 营销码汇总表Service
 *
 * @author xiaotong
 * @Date 2020-06-17 10:33
 */
public interface ChannelStoreService {

    /**
     * 营销码汇总页面
     * @param channelSplitId
     * @return
     */
    List<ChannelStoreDto> list(String channelSplitId);

    Page<ChannelStoreDto> page(ChannelStoreReqDto request);

    ChannelStoreDto getByMarketingId(String uid);


    /**
     * 添加渠道营销码
     * @param channelLinkReqDto
     * @return
     */
    Boolean add(ChannelStoreDto channelLinkReqDto);

    /**
     *
     * @param id
     * @return
     */
    Boolean delete(String id);

    /**
     * 根据营销码获取渠道编号
     * @param marketingId
     * @return
     */
    String getChannelSplitId(String marketingId);
}