package com.rent.common.dto.order;

import com.rent.common.enums.order.EnumAccountPeriodPayInfoStatus;
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
 * 凭证表
 *
 * @author xiaotong
 * @Date 2020-06-16 10:09
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "凭证表")
public class AccountPeriodPayInfoDto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Schema(description = "ID")
    private Long id;
    /**
     * 账期ID
     */
    @Schema(description = "账期ID")
    private Long accountPeriodId;
    /**
     * 支付订单号
     */
    @Schema(description = "支付订单号")
    private String orderNo;
    /**
     * 金额
     */
    @Schema(description = "金额")
    private BigDecimal amount;
    /**
     * 服务费
     */
    @Schema(description = "服务费")
    private BigDecimal serviceFee;
    /**
     * 收款账户
     */
    @Schema(description = "收款账户")
    private String toAccountIdentity;
    /**
     * 收款账户
     */
    @Schema(description = "收款账户")
    private String toAccountName;
    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;
    /**
     * 标题
     */
    @Schema(description = "标题")
    private String title;
    /**
     * 状态
     */
    @Schema(description = "状态")
    private EnumAccountPeriodPayInfoStatus status;
    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private Date createTime;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}