package com.rent.common.dto.export;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;


@Data
public class ProductExportDto {

    @ExcelProperty("店铺ID")
    private String  shopId;

    @ExcelProperty("店铺ID")
    private String  shopName;

    @ExcelProperty("商品名称")
    private String  productName;

    @ExcelProperty("商品Id")
    private String  productId;

    @ExcelProperty("类目")
    private String  category;

    @ExcelProperty("创建时间")
    private Date createTime;

    @ExcelProperty("最低租金/天")
    private BigDecimal minRent;
    @ExcelProperty("市场价")
    private BigDecimal marketPrice;
    @ExcelProperty("销售价")
    private BigDecimal salePrice;
    @ExcelProperty("上架状态")
    private String type;

    @ExcelProperty("发货地")
    private String deliveryCity;



}
