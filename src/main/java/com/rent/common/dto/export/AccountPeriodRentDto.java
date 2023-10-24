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
public class AccountPeriodRentDto {
    @ExcelIgnore
    private String shopId;

    @ExcelProperty("订单号")
    private String orderId;

    @ExcelProperty("客户姓名")
    private String userName;

    @ExcelProperty("订单租金")
    private BigDecimal totalRent;

    @ExcelIgnore
    private Integer period;

    @ExcelProperty("结算第几期")
    private String stageInfo;

    @ExcelProperty("结算金额")
    private BigDecimal settleAmount;

    @ExcelIgnore
    private BigDecimal transAmount;

    @ExcelProperty("佣金")
    private BigDecimal brokerage;

    @ExcelProperty("结算日期")
    private Date settleDate;

}
