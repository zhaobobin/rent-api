package com.rent.common.dto.components.request;

import com.rent.common.dto.Page;
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
 * 支付宝订单支付
 *
 * @author xiaoyao
 * @Date 2020-06-24 16:11
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "支付宝订单支付")
public class AlipayTradePayReqDto extends Page implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @Schema(description = "主键")
    private Long id;

    /**
     * 标题
     */
    @Schema(description = "标题")
    private String subject;

    /**
     * 支付授权码
     */
    @Schema(description = "支付授权码")
    private String authCode;

    /**
     * 商户订单号
     */
    @Schema(description = "商户订单号")
    private String outTradeNo;

    /**
     * 订单号
     */
    @Schema(description = "订单号")
    private String orderId;

    /**
     * 金额
     */
    @Schema(description = "金额")
    private BigDecimal amount;

    /**
     * 返回参数
     */
    @Schema(description = "返回参数")
    private String body;

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
