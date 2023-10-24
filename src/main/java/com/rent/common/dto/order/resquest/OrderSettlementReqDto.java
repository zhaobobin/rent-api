package com.rent.common.dto.order.resquest;

import com.rent.common.dto.Page;
import com.rent.common.enums.order.EnumOrderSettlementStatus;
import com.rent.common.enums.order.EnumOrderSettlementType;
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
 * 订单结算记录
 *
 * @author xiaoyao
 * @Date 2020-06-19 10:49
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "订单结算记录")
public class OrderSettlementReqDto extends Page implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @Schema(description = " ")
    private Long id;

    /**
     * 创建时间
     *
     */
    @Schema(description = "创建时间")
    private Date createTime;

    /**
     * 更新时间
     *
     */
    @Schema(description = "更新时间")
    private Date updateTime;

    /**
     * DeleteTime
     *
     */
    @Schema(description = "DeleteTime")
    private Date deleteTime;

    /**
     * OrderId
     */
    @Schema(description = "OrderId")
    private String orderId;

    /**
     * 结算状态 01：待支付，02:支付中 03:已结算 04：用户申请修改
     */
    @Schema(description = "结算状态 01：待支付，02:支付中 03:已结算 04：用户申请修改")
    private EnumOrderSettlementStatus settlementStatus;

    /**
     * 结算类型 01:完好 02:损坏 03:丢失 04:违约
     */
    @Schema(description = "结算类型 01:完好 02:损坏 03:丢失 04:违约")
    private EnumOrderSettlementType settlementType;

    /**
     * 用户申请修改次数
     */
    @Schema(description = "用户申请修改次数")
    private Integer applyModifyTimes;

    /**
     * 丢失金额
     */
    @Schema(description = "丢失金额")
    private BigDecimal loseAmount;

    /**
     * 损坏金额
     */
    @Schema(description = "损坏金额")
    private BigDecimal damageAmount;

    /**
     * 违约金
     * 
     */
    @Schema(description = "违约金")
    private BigDecimal penaltyAmount;

    /**
     * 解冻押金
     * 
     */
    @Schema(description = "解冻押金")
    private BigDecimal deposit;

    /**
     * 已退租金
     * 
     */
    @Schema(description = "已退租金")
    private BigDecimal rent;

    /**
     * 应付租金
     * 
     */
    @Schema(description = "应付租金")
    private BigDecimal paymentRent;

    /**
     * 冻结转支付金额
     * 
     */
    @Schema(description = "冻结转支付金额")
    private BigDecimal transferPayment;

    /**
     * 冻结押金
     * 
     */
    @Schema(description = "冻结押金")
    private BigDecimal realDeposit;

    /**
     * 提前支付租金
     * 
     */
    @Schema(description = "提前支付租金")
    private BigDecimal beforeRent;

    /**
     * 请求商户订单号
     * 
     */
    @Schema(description = "请求商户订单号")
    private String outTrandNo;

    /**
     * 支付宝交易号
     * 
     */
    @Schema(description = "支付宝交易号")
    private String tradeNo;

    /**
     * 支付时间
     * 
     */
    @Schema(description = "支付时间")
    private Date paymentTime;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
