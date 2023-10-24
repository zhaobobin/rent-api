        
package com.rent.service.product;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.dto.backstage.resp.SplitBillShopResp;
import com.rent.common.dto.product.*;

import java.util.Date;
import java.util.List;

/**
 * @author zhaowenchao
 */
public interface SplitBillConfigService {


    /**
     * 佣金设置列表页面
     * @param request
     * @return
     */
    Page<SplitBillConfigListDto> page(ShopSplitBillReqDto request);

    /**
     * 添加分账信息页面 店铺列表
     * @param shopName
     * @return
     */
    List<SplitBillShopResp> getShopList(String shopName);


    /**
     * 添加店铺的分账信息
     * @param spiltBillConfigDto
     * @return
     */
    Boolean add(SpiltBillConfigDto spiltBillConfigDto);


    /**
     * 编辑店铺的分账信息
     * @param spiltBillConfigDto
     * @return
     */
    Boolean update(SpiltBillConfigDto spiltBillConfigDto);

    /**
     * 获取有效时间内的商家分账规则
     * @param shopId
     * @param time
     * @param type
     * @return
     */
    SpiltBillRuleConfigDto getUseAbleConfigByType(String shopId, Date time, String type, String channelId);

    /**
     * 根据ID获取分账配置详情
     * @param id
     * @return
     */
    SpiltBillConfigDto detail(Long id);

    /**
     * 财务人员审核
     * @param auditDto
     * @return
     */
    Boolean audit(SplitBillAuditDto auditDto);



}