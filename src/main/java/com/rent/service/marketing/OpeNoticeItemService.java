
package com.rent.service.marketing;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.dto.marketing.OpeNoticeItemDto;
import com.rent.common.dto.marketing.OpeNoticeItemReqDto;

import java.util.List;

/**
 * 公告常见问题tab内容Service
 *
 * @author youruo
 * @Date 2021-08-16 15:55
 */
public interface OpeNoticeItemService {

    /**
     * 新增公告常见问题tab内容
     *
     * @param request 条件
     * @return boolean
     */
    Boolean addOpeNoticeItem(OpeNoticeItemDto request);

    /**
     * 根据 ID 修改公告常见问题tab内容
     *
     * @param request 条件
     * @return String
     */
    Boolean modifyOpeNoticeItem(OpeNoticeItemDto request);

    Boolean deleteOpeNoticeItem(Long id);

    /**
     * <p>
     * 根据条件查询一条记录
     * </p>
     *
     * @param request 实体对象
     * @return OpeNoticeItem
     */
    OpeNoticeItemDto queryOpeNoticeItemDetail(OpeNoticeItemReqDto request);

    List<OpeNoticeItemDto> queryOpeNoticeItemList(OpeNoticeItemReqDto request);

    /**
     * <p>
     * 根据条件列表
     * </p>
     *
     * @param request 实体对象
     * @return OpeNoticeItem
     */
    Page<OpeNoticeItemDto> queryOpeNoticeItemPage(OpeNoticeItemReqDto request);


}