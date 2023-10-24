
package com.rent.service.marketing;

import com.rent.common.dto.marketing.OpeNoticeTabDto;
import com.rent.common.dto.marketing.OpeNoticeTabReqDto;

import java.util.List;

/**
 * 公告常见问题tabService
 *
 * @author youruo
 * @Date 2021-08-16 15:55
 */
public interface OpeNoticeTabService {

    /**
     * 新增公告常见问题tab
     *
     * @param request 条件
     * @return boolean
     */
    Boolean addOpeNoticeTab(OpeNoticeTabDto request);

    /**
     * 根据 ID 修改公告常见问题tab
     *
     * @param request 条件
     * @return String
     */
    Boolean modifyOpeNoticeTab(OpeNoticeTabDto request);

    Boolean deleteOpeNoticeTab(Long id);


    List<OpeNoticeTabDto> queryOpeNoticeTabDetailList(OpeNoticeTabReqDto request);

    List<OpeNoticeTabDto> queryOpeNoticeTabList();



}