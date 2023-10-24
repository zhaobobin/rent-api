package com.rent.common.dto.backstage;


import com.rent.common.enums.order.EnumOrderStatus;
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
 * 分账信息
 *
 * @author zhao
 * @Date 2020-08-11 09:59
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "租金分账信息")
public class SplitBilRentlDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "Id")
    private Long id;

    @Schema(description = "分账规则ID")
    private Long splitBillRuleId;

    @Schema(description = "分账单号")
    private String orderNo;

    @Schema(description = "分账订单ID")
    private String orderId;

    @Schema(description = "当前期数")
    private Integer period;

    @Schema(description = "总期数")
    private Integer totalPeriod;

    @Schema(description = "分账订单所属用户ID")
    private String uid;

    @Schema(description = "分账订单所属用户手机号码")
    private String phone;

    @Schema(description = "店铺ID")
    private String shopId;

    @Schema(description = "店铺名称")
    private String shopName;

    @Schema(description = "分账类型")
    private String type;

    @Schema(description = "支付宝账号")
    private String identity;

    @Schema(description = "支付宝实名信息")
    private String name;

    @Schema(description = "分账比例")
    private BigDecimal scale;

    @Schema(description = "用户支付金额")
    private BigDecimal userPayAmount;

    @Schema(description = "商家结算金额")
    private BigDecimal toShopAmount;

    @Schema(description = "平台佣金")
    private BigDecimal toOpeAmount;

    @Schema(description = "订单状态")
    private EnumOrderStatus orderStatus;

    @Schema(description = "结算状态")
    private String status;

    @Schema(description = "用户支付时间")
    private Date userPayTime;

    @Schema(description = "本期账单日")
    private Date statementDate;

    @Schema(description = "计划支付时间")
    private Date planPayTime;

    @Schema(description = "实际支付时间")
    private Date realPayTime;

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "更新时间")
    private Date updateTime;


    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
