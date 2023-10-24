        
package com.rent.mapper.order;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rent.common.dto.export.AccountPeriodBuyOutDto;
import com.rent.common.dto.export.AccountPeriodRentDto;
import com.rent.model.order.SplitBill;

import java.util.List;

/**
 * SplitBillDao
 *
 * @author zhao
 * @Date 2020-08-11 09:59
 */
public interface SplitBillMapper extends BaseMapper<SplitBill>{

    /**
     * 买断导出
     * @param accountPeriodId
     * @return
     */
    List<AccountPeriodBuyOutDto> buyOutExport(Long accountPeriodId);

    /**
     * 租赁导出
     */
    List<AccountPeriodRentDto> rentExport(Long accountPeriodId);

}