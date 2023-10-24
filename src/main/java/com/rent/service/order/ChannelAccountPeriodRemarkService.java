package com.rent.service.order;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.dto.order.AccountPeriodRemarkDto;
import com.rent.common.dto.order.resquest.AccountPeriodRemarkReqDto;

/**
 * @author zhaowenchao
 */
public interface ChannelAccountPeriodRemarkService {

    /**
     * 新增备注
     * @param reqDto
     */
    void add(AccountPeriodRemarkReqDto reqDto);

    /**
     * 根据账期获取备注信息
     * @param request
     * @return
     */
    Page<AccountPeriodRemarkDto> listByAccountPeriodId(AccountPeriodRemarkReqDto request);
}
