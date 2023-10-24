package com.rent.common.dto.order;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 用户买断
 *
 * @author xiaoyao
 * @Date 2020-06-23 14:50
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "后台买断价格展示买断")
public class BackstageOrderBuyOutDto implements Serializable {

    private static final long serialVersionUID = -5209352611626014391L;


    /**
     * 租赁订单ID
     */
    @Schema(description = "租赁订单ID")
    private String orderId;

    /**
     * 已支付租金
     */
    @Schema(description = "已支付租金")
    private BigDecimal paidRent;

    /**
     * 买断支付状态
     */
    @Schema(description = "买断支付状态")
    private Boolean payFlag;

    /**
     * 到期买断价格
     */
    @Schema(description = "到期买断价格（未支付才有值）")
    private BigDecimal dueBuyOutAmount;

    /**
     * 当前买断价格
     */
    @Schema(description = "当前买断价格（未支付才有值）")
    private BigDecimal currentBuyOutAmount;

    /**
     * 买断价格（已支付才有值）
     */
    @Schema(description = "买断价格（已支付才有值）")
    private BigDecimal buyOutAmount;


}
