
package com.rent.service.product;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.dto.product.ChannelSplitBillDto;
import com.rent.common.dto.product.ChannelSplitBillReqDto;
import com.rent.common.dto.product.SplitBillAuditDto;

import java.util.List;

/**
 * 渠道账号分佣表Service
 *
 * @author xiaotong
 * @Date 2020-06-17 10:33
 */
public interface ChannelSplitBillService {

    /**
     * 渠道分佣列表页面
     * @param request
     * @return
     */
    Page<ChannelSplitBillDto> page(ChannelSplitBillReqDto request);

    /**
     * 添加渠道信息
     * @param channelSplitBillDto
     * @return
     */
    Long add(ChannelSplitBillDto channelSplitBillDto);

    /**
     * 修改渠道信息
     * @param channelSplitBillDto
     * @return
     */
    Boolean update(ChannelSplitBillDto channelSplitBillDto);

    /**
     * 获取渠道详情
     * @param uid
     * @return
     */
    ChannelSplitBillDto getOne(String uid);

    /**
     * 财务人员审核
     * @param auditDto
     * @return
     */
    Boolean channelAudit(SplitBillAuditDto auditDto);

    /**
     * 获取渠道详情
     * @param uid
     * @return
     */
    ChannelSplitBillDto getOneForScale(String uid);

    /**
     * 模糊查询名称
     * @param name
     * @return
     */
    List<String> getUidList(String name);
}