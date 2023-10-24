package com.rent.service.marketing;

import com.rent.common.dto.marketing.PageElementConfigDto;
import com.rent.common.dto.marketing.PageElementConfigRequest;
import com.rent.common.dto.marketing.PageElementConfigResp;

import java.util.List;

/**
 * @author zhaowenchao
 */
public interface PageElementConfigService {

    /**
     * 新增
     * @param request
     * @return
     */
    Long add(PageElementConfigRequest request);

    /**
     * 删除
     * @param id
     * @return
     */
    Long delete(Long id);

    /**
     * 更新
     * @param request
     * @return
     */
    Long update(PageElementConfigRequest request);

    /**
     * 查询列表
     * @param request
     * @return
     */
    List<PageElementConfigResp> list(PageElementConfigRequest request);

    /**
     * 查询首页Banner
     * @return
     */
    List<PageElementConfigDto> listBanner();

    /**
     * 专区主
     * @return
     */
    List<PageElementConfigDto> listIconArea();

    /**
     * 专区主
     * @return
     */
    List<PageElementConfigDto> listSpecialAreaMain();


    /**
     * 专区副
     * @return
     */
    List<PageElementConfigDto> listSpecialAreaSub();

    /**
     * 专区主标题
     * @return
     */
    String getSpecialTitleMain();

    /**
     * 专区副标题
     * @return
     */
    String getSpecialTitleSub();

    /**
     * 我的服务
     * @return
     */
    List<PageElementConfigDto> listMyService();

    /**
     * 我的订单
     * @return
     */
    List<PageElementConfigDto> listMyOrder();
}