package com.rent.common.dto.export;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author xiaoyao
 * @version V1.0
 * @date 2020-8-12 上午 11:17:13
 * @since 1.0
 */
@Data
public class BuyOutOrderExportDto {

    @ExcelProperty("买断订单ID")
    private String buyOutOrderId;
    @ExcelProperty("订单ID")
    private String orderId;
    @ExcelProperty("订单状态")
    private String status;
    @ExcelProperty("商品名称")
    private String productName;
    @ExcelProperty("商品id")
    private String productId;
    @ExcelProperty("销售价格")
    private BigDecimal salePrice;
    @ExcelProperty("已支付租金")
    private BigDecimal paidRent;
    @ExcelProperty("应付尾款")
    private BigDecimal endFund;
    @ExcelProperty("下单时间")
    private Date createTime;
    @ExcelProperty("姓名")
    private String userName;
    @ExcelProperty("手机号")
    private String telephone;
    @ExcelProperty("身份证号")
    private String idNo;


}
