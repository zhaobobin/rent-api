package com.rent.service.export;


import com.rent.common.dto.export.AccountPeriodBuyOutDto;
import com.rent.common.dto.export.AccountPeriodRentDto;
import com.rent.common.dto.export.FeeBillDto;
import com.rent.common.dto.order.AccountPeriodItemReqDto;
import com.rent.common.dto.order.FeeBillDetailReqDto;

import java.util.List;

/**
 * @author zhaowenchao
 */
public interface AccountPeriodExportService {

    /**
     * 账期-买断订单导出
     * @param reqDto
     * @return
     */
    List<AccountPeriodBuyOutDto> buyOut(AccountPeriodItemReqDto reqDto);

    /**
     * 账期-常规订单导出
     * @param reqDto
     * @return
     */
    List<AccountPeriodRentDto> rent(AccountPeriodItemReqDto reqDto);

    /**
     * 费用结算明细导出
     * @param request
     * @return
     */
    List<FeeBillDto> feeBillDetail(FeeBillDetailReqDto request);

}
