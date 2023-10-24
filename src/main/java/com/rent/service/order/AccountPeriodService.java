package com.rent.service.order;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.dto.order.AccountPeriodDetailDto;
import com.rent.common.dto.order.AccountPeriodDto;
import com.rent.common.dto.order.AccountPeriodSubmitAuditDto;
import com.rent.common.dto.order.AccountPeriodSubmitSettleDto;
import com.rent.common.dto.order.resquest.AccountPeriodReqDto;

/**
 * @author zhaowenchao
 */
public interface AccountPeriodService {

    /**
     * 生成账期
     */
    void generateAccountPeriod();

    /**
     * 查询
     * @param request
     * @return
     */
    Page<AccountPeriodDto> queryAccountPeriodPage(AccountPeriodReqDto request);

    /**
     * 获取详细信息
     * @param id
     * @param shopId
     * @return
     */
    AccountPeriodDetailDto detail(Long id, String shopId);

    /**
     * 提交结算
     * @param req
     */
    void submitSettle(AccountPeriodSubmitSettleDto req);

    /**
     * 提交审核
     * @param req
     */
    void submitAudit(AccountPeriodSubmitAuditDto req);

}
