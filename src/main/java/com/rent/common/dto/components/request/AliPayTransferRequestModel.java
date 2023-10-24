package com.rent.common.dto.components.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author udo
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "现金转账类" )
public class AliPayTransferRequestModel implements Serializable {

    /**
     * 订单号
     */
    @Schema(description = "订单号", name = "outBizNo")
    @NotBlank
    private String outBizNo;

    /**
     * 金额
     */
    @Schema(description = "金额")
    private BigDecimal amount;

    /**
     * 转入账户用户登陆名
     */
    @Schema(description = "转入账户用户登陆名")
    private String identity;

    /**
     * 转入账户用户真实姓名
     */
    @Schema(description = "转入账户用户真实姓名")
    private String name;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;

    /**
     * 展示的转出用户名，没实际意义
     */
    @Schema(description = "展示的转出用户名，没实际意义")
    private String payerShowName;

    /**
     * 租物租uid
     */
    @Schema(description = "租物租uid")
    private String uid;

    /**
     * 支付宝userId
     */
    @Schema(description = "支付宝userId")
    private String userId;


}
