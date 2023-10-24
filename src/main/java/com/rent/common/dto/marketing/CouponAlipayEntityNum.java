package com.rent.common.dto.marketing;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class CouponAlipayEntityNum {
    @ExcelProperty("用户手机号码")
    private String entityNo;
}
