package com.rent.common.dto.components.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xiaoyao
 * @version V1.0
 * @date 2020-9-2 上午 11:55:27
 * @since 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RiskParamsDto implements Serializable {

    private static final long serialVersionUID = -7141794716820906281L;

    /** 姓名 */
    private String name;
    /** 手机号码 */
    private String mobileNumber;
    /** 身份证号码 */
    private String identNumber;
    /** 订单号 */
    private String orderNumber;
    /** 商品类目 */
    private int goodCategory;
    /** 商品名称 */
    private String goodName;
    /** 租赁期数 */
    private int rentalLong;
    /** 总租金 */
    private float rentalTotal;
    /** 月租金 */
    private float monthlyPay;
    /** 日租金 */
    private float rentalMoneyDay;
    /** 收货人姓名 */
    private String recipientName;
    /** 收货人手机号 */
    private String recipientMobile;
    /** 下单时间 */
    private Date orderTime;
    /** 租赁期限 */
    private int orderOverTime;
    /** 在途订单数 */
    private int orderNumbers;
    /** 免押金额 */
    private float freeRentalMoney;
    /** 实付押金 */
    private float realPayMoney;
    /** 商品现价 */
    private float goodNowPrice;
}
