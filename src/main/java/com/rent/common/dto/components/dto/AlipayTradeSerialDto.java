package com.rent.common.dto.components.dto;

import com.rent.common.enums.components.EnumAliPayStatus;
import com.rent.common.enums.components.EnumPayType;
import com.rent.common.enums.components.EnumTradeType;
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
 * 支付流水
 *
 * @author xiaoyao
 * @Date 2020-06-24 16:11
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "支付流水")
public class AlipayTradeSerialDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @Schema(description = "主键")
    private Long id;

    /**
     * 订单编号
     */
    @Schema(description = "订单编号")
    private String orderId;

    /**
     * uid
     */
    @Schema(description = "uid")
    private String uid;

    /**
     * 支付宝user_id
     */
    @Schema(description = "支付宝user_id")
    private String userId;

    /**
     * 流水号
     */
    @Schema(description = "流水号")
    private String serialNo;

    /**
     * 支付流水号
     */
    @Schema(description = "支付流水号")
    private String outOrderNo;

    /**
     * 交易金额
     */
    @Schema(description = "交易金额")
    private BigDecimal amount;

    /**
     * 支付类型 01：预授权冻结 02：预授权转支付 03：订单创建 04：预授权转支付 05：退款 06：解冻 07：转账
     */
    @Schema(description = "支付类型 01：预授权冻结 02：预授权转支付 03：订单创建 04：预授权转支付 05：退款 06：解冻 07：转账")
    private EnumPayType payType;

    /**
     * 交易类型 01:下单 02：关单 03：账单代扣 04：账单主动支付 05：结算单支付 06：充值 07：买断 08：商家分账
     */
    @Schema(description = "交易类型 01:下单 02：关单 03：账单代扣 04：账单主动支付 05：结算单支付 06：充值 07：买断 08：商家分账")
    private EnumTradeType tradeType;

    /**
     * 状态  00:初始化 01:支付中 02：成功 03：失败
     */
    @Schema(description = "状态  00:初始化 01:支付中 02：成功 03：失败")
    private EnumAliPayStatus status;

    /**
     * 渠道编号
     */
    @Schema(description = "渠道编号")
    private String channelId;

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

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
