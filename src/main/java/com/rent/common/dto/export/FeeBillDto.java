package com.rent.common.dto.export;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author zhaowenchao
 */
@Data
public class FeeBillDto {

    @ExcelProperty("订单号")
    private String orderId;

    @ExcelProperty("客户姓名")
    private String userName;

    @ExcelProperty("下单手机号")
    private String phone;

    @ExcelProperty("费用")
    private BigDecimal amount;

    @ExcelProperty("结算状态")
    private String status;

    @ExcelProperty("账单生成时间")
    private Date createTime;

    @ExcelProperty("结算日期")
    private Date settleDate;

}
