package com.rent.common.dto.components.request;

import com.rent.common.dto.Page;
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
 * 支付宝资金授权解冻
 *
 * @author xiaoyao
 * @Date 2020-06-24 16:12
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "支付宝资金授权解冻")
public class AlipayUnfreezeReqDto extends Page implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Id
     */
    @Schema(description = "Id")
    private Long id;

    /**
     * CreateTime
     */
    @Schema(description = "CreateTime")
    private Date createTime;

    /**
     * UpdateTime
     */
    @Schema(description = "UpdateTime")
    private Date updateTime;

    /**
     * DeleteTime
     */
    @Schema(description = "DeleteTime")
    private Date deleteTime;

    /**
     * 支付宝资金授权订单号
     */
    @Schema(description = "支付宝资金授权订单号")
    private String authNo;

    /**
     * 所属租单订单号
     */
    @Schema(description = "所属租单订单号")
    private String orderId;

    /**
     * 支付宝资金操作流水号
     */
    @Schema(description = "支付宝资金操作流水号")
    private String operationId;

    /**
     * Uid
     */
    @Schema(description = "Uid")
    private String uid;

    /**
     * 解冻流水号
     */
    @Schema(description = "解冻流水号")
    private String unfreezeRequestNo;

    /**
     * 解冻金额
     */
    @Schema(description = "解冻金额")
    private BigDecimal amount;

    /**
     * 解冻说明
     */
    @Schema(description = "解冻说明")
    private String remark;

    /**
     * 订单状态 1 等待解冻
     * 订单状态 1 等待解冻,2 解冻成功
     */
    @Schema(description = "订单状态 1 等待解冻")
    private EnumAliPayStatus status;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
