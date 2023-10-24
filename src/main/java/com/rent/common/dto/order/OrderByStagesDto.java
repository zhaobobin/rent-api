package com.rent.common.dto.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rent.common.enums.order.EnumOrderByStagesStatus;
import com.rent.common.enums.order.PaymentMethod;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户订单分期
 *
 * @author xiaoyao
 * @Date 2020-06-11 17:25
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "用户订单分期")
public class OrderByStagesDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Id
     */
    @Schema(description = "Id")
    private String id;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private Date createTime;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    private Date updateTime;

    /**
     * 删除时间
     */
    @Schema(description = "删除时间")
    private Date deleteTime;

    /**
     * 租期，单位：天
     */
    @Schema(description = "租期，单位：天")
    private Integer leaseTerm;

    /**
     * 订单号
     */
    @Schema(description = "订单号")
    private String orderId;

    /**
     * 总期数
     */
    @Schema(description = "总期数")
    private Integer totalPeriods;

    /**
     * 当前期数
     */
    @Schema(description = "当前期数")
    private Integer currentPeriods;

    /**
     * 总租金
     */
    @Schema(description = "总租金")
    private BigDecimal totalRent;

    /**
     * 当期应付租额
     */
    @Schema(description = "当期应付租额")
    private BigDecimal currentPeriodsRent;

    /**
     * 状态:  1：待支付 2：已支付 3：逾期已支付 4：逾期待支付 5：已取消  6: 已结算 7:已退款
     */
    @Schema(description = "状态:  1：待支付 2：已支付 3：逾期已支付 4：逾期待支付 5：已取消  6: 已结算 7:已退款")
    private EnumOrderByStagesStatus status;

    /**
     * 逾期天数，单位：天
     */
    @Schema(description = "逾期天数，单位：天")
    private Integer overdueDays;

    /**
     * 还款日
     */
    @Schema(description = "还款日")
    private Date repaymentDate;

    /**
     * 账单日
     */
    @Schema(description = "账单日")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date statementDate;

    /**
     * 商户传入的资金交易号
     */
    @Schema(description = "商户传入的资金交易号")
    private String outTransNo;

    /**
     * 退款资金交易号(已退款金额 部分退款才有)
     */
    @Schema(description = "退款资金交易号(已退款金额 部分退款才有)")
    private String refundTransactionNumber;

    /**
     * 商铺id冗余订单表的字段
     */
    @Schema(description = "商铺id冗余订单表的字段")
    private String shopId;

    /**
     * 渠道来源
     */
    @Schema(description = "渠道来源")
    private String channelId;

    @Schema(description = "商家结算金额")
    private BigDecimal toShopAmount;

    @Schema(description = "平台佣金")
    private BigDecimal toOpeAmount;

    @Schema(description = "结算状态")
    private String splitBillStatus;

    @Schema(description = "结算人")
    private String userName;

    @Schema(description = "结算时间")
    private Date splitBillTime;


    @Schema(description = "扣款次数")
    private Integer deductionTimes;

    @Schema(description = "支付方式")
    private PaymentMethod paymentMethod;

    @Schema(description = "当前账期逾期违约金")
    private BigDecimal overdueChargesAmount;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
