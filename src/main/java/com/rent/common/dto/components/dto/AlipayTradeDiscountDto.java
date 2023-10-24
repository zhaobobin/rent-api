package com.rent.common.dto.components.dto;

import com.rent.common.enums.components.EnumAliPayStatus;
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
 * 订单交易支持叠加减免金额
 *
 * @author youruo
 * @Date 2021-05-27 11:00
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "订单交易支持叠加减免金额")
public class AlipayTradeDiscountDto implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 主键
     * 
     */
    @Schema(description = "主键")
    private Integer id;

    /**
     * 订单号
     * 
     */
    @Schema(description = "订单号")
    private String orderId;

    /**
     * 交易金额
     * 
     */
    @Schema(description = "交易金额")
    private BigDecimal totalAmount;

    /**
     * 交易减免金额
     * 
     */
    @Schema(description = "交易减免金额")
    private BigDecimal discountAmount;

    /**
     * 实际交易金额
     * 
     */
    @Schema(description = "实际交易金额")
    private BigDecimal actualAmount;

    /**
     * 交易优惠券模板id
     * 
     */
    @Schema(description = "交易优惠券模板id")
    private Long templateId;

    /**
     * 状态
     * 
     */
    @Schema(description = "状态")
    private EnumAliPayStatus status;

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



    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
