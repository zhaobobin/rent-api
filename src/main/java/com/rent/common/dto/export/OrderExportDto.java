package com.rent.common.dto.export;

import com.alibaba.excel.annotation.ExcelProperty;
import com.rent.common.enums.order.EnumOrderStatus;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author xiaoyao
 * @version V1.0
 * @date 2020-8-12 上午 11:17:13
 * @since 1.0
 */
@Data
public class OrderExportDto {

    @ExcelProperty("订单ID")
    private String orderId;
    @ExcelProperty("订单状态")
    private String status;
    @ExcelProperty("商品名称")
    private String productName;
    @ExcelProperty("商品id")
    private String productId;
    @ExcelProperty("创建时间")
    private String createTime;
    @ExcelProperty("起租日期")
    private String rentStart;
    @ExcelProperty("结束日期")
    private String rentEnd;
    @ExcelProperty("渠道名称")
    private String channelName;
    @ExcelProperty("已付期数")
    private int currentPeriods;
    @ExcelProperty("总期数")
    private int totalPeriods;
    @ExcelProperty("已支付租金")
    private BigDecimal payedRent;
    @ExcelProperty("总租金")
    private BigDecimal totalRent;
    @ExcelProperty("平台优惠")
    private BigDecimal platformCouponReduction;
    @ExcelProperty("店铺优惠")
    private BigDecimal shopCouponReduction;
    @ExcelProperty("增值服务费")
    private BigDecimal additionalServicesPrice;
    @ExcelProperty("冻结金额")
    private BigDecimal freezePrice;
    @ExcelProperty("姓名")
    private String userName;
    @ExcelProperty("手机号")
    private String telephone;
    @ExcelProperty("身份证号")
    private String idNo;
    @ExcelProperty("用户备注")
    private String remark;
    @ExcelProperty("逾期待支付期数")
    private int overPaid;
    @ExcelProperty("逾期待支付金额")
    private BigDecimal overAmount;
    @ExcelProperty("运营平台催收记录")
    private String opeHasten;
    @ExcelProperty("商家平台催收记录")
    private String businessHasten;
    @ExcelProperty("店铺名称")
    private String shopName;
    @ExcelProperty("一级类目")
    private String firstCategory;
    @ExcelProperty("二级类目")
    private String secondCategory;
    @ExcelProperty("三级类目")
    private String thirdCategory;
    @ExcelProperty("交易关闭原因")
    private String closeReason;
    @ExcelProperty("风险分")
    private String riskScore;

    public void setStatus(String status) {
        this.status = EnumOrderStatus.find(status).getDescription();
    }


}
