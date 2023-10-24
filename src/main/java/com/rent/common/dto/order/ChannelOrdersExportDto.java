package com.rent.common.dto.order;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author xiaotong
 */
@Data
public class ChannelOrdersExportDto {

    @ExcelProperty("订单号")
    private String orderId;
    @ExcelProperty("渠道名称")
    private String channelName;
    @ExcelProperty("商品名称")
    private String productName;
    @ExcelProperty("当前期次")
    private Integer currentPeriods;
    @ExcelProperty("总期次")
    private Integer totalPeriods;
    @ExcelProperty("总租金")
    private BigDecimal totalRent;
    @ExcelProperty("已付租金")
    private BigDecimal payRent;
    @ExcelProperty("客户姓名")
    private String userName;
    @ExcelProperty("用户手机号码")
    private String telephone;
    @ExcelProperty("下单时间")
    private Date createTime;
    @ExcelProperty("订单状态")
    private String status;
    @ExcelProperty("渠道结算金额")
    private BigDecimal settleAmount;
    @ExcelIgnore
    private BigDecimal scale;


}
