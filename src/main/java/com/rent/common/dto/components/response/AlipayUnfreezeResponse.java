package com.rent.common.dto.components.response;

import com.alipay.api.AlipayResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author xiaoyao
 * @version V1.0
 * @date 2020-7-1 上午 10:42:56
 * @since 1.0
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "预授权解冻请求类")
public class AlipayUnfreezeResponse extends AlipayResponse {

    private static final long serialVersionUID = 1858551436162282658L;

    /**
     * 本次操作解冻的金额，单位为：元（人民币），精确到小数点后两位，取值范围：[0.01,100000000.00]
     */
    @Schema(description = "本次操作解冻的金额")
    private String amount;

    /**
     * 支付宝资金授权订单号
     */
    @Schema(description = "支付宝资金授权订单号")
    private String authNo;

    /**
     * 本次解冻操作中信用解冻金额，单位为：元（人民币），精确到小数点后两位
     */
    @Schema(description = "本次解冻操作中信用解冻金额")
    private String creditAmount;

    /**
     * 本次解冻操作中自有资金解冻金额，单位为：元（人民币），精确到小数点后两位
     */
    @Schema(description = "本次解冻操作中自有资金解冻金额")
    private String fundAmount;

    /**
     * 授权资金解冻成功时间，格式：YYYY-MM-DD HH:MM:SS
     */
    @Schema(description = "授权资金解冻成功时间")
    private Date gmtTrans;

    /**
     * 支付宝资金操作流水号
     */
    @Schema(description = "operation_id")
    private String operationId;

    /**
     * 商户的授权资金订单号
     */
    @Schema(description = "商户的授权资金订单号")
    private String outOrderNo;

    /**
     * 商户本次资金操作的请求流水号
     */
    @Schema(description = "商户本次资金操作的请求流水号")
    private String outRequestNo;

    /**
     * 资金操作流水的状态
     * 目前支持：  INIT：初始
     * SUCCESS：成功
     * CLOSED：关闭
     */
    @Schema(description = "status")
    private String status;
}
