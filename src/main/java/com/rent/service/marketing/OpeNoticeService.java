
package com.rent.service.marketing;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.dto.marketing.OpeNoticeDto;
import com.rent.common.dto.marketing.OpeNoticeReqDto;

import java.util.List;

/**
 * 商家中心通知Service
 *
 * @author youruo
 * @Date 2021-08-16 16:10
 */
public interface OpeNoticeService {

    /**
     * 新增商家中心通知
     *
     * @param request 条件
     * @return boolean
     */
    Boolean addOpeNotice(OpeNoticeDto request);

    /**
     * 根据 ID 修改商家中心通知
     *
     * @param request 条件
     * @return String
     */
    Boolean modifyOpeNotice(OpeNoticeDto request);

    Boolean deleteOpeNotice(Long id);


    List<OpeNoticeDto> queryOpeNoticeDetailList(OpeNoticeReqDto request);

    /**
     * <p>
     * 根据条件列表
     * </p>
     *
     * @param request 实体对象
     * @return OpeNotice
     */
    Page<OpeNoticeDto> queryOpeNoticePage(OpeNoticeReqDto request);


}