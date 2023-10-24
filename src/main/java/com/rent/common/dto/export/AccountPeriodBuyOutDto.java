package com.rent.common.dto.export;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author zhaowenchao
 */
@Data
public class AccountPeriodBuyOutDto {

    @ExcelIgnore
    private String shopId;

    @ExcelProperty("买断订单号")
    private String orderId;

    @ExcelProperty("原订单号")
    private String originOrderId;

    @ExcelProperty("客户姓名")
    private String userName;

    @ExcelProperty("已付租金")
    private BigDecimal paidRent;

    @ExcelProperty("买断尾款")
    private BigDecimal endFund;

    @ExcelProperty("结算金额")
    private BigDecimal settleAmount ;

    @ExcelIgnore
    private BigDecimal transAmount;

    @ExcelProperty("佣金")
    private BigDecimal brokerage;

    @ExcelProperty("结算日期")
    private Date settleDate;

}
