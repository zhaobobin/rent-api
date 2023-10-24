package com.rent.common.dto.components.response;

import com.alipay.api.AlipayResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "查询授权操作返回参数")
public class AliPayOperationDetailResponse extends AlipayResponse {

    /**
     * 该笔资金操作流水opertion_id对应的操作金额，单位为：元（人民币）
     */
    @Schema(description = "该笔资金操作流水opertion_id对应的操作金额")
    private String amount;

    /**
     * 支付宝资金授权订单号
     */
    @Schema(description = "支付宝资金授权订单号")
    private String authNo;

    /**
     * 该笔资金操作流水opertion_id对应的操作信用金额
     */
    @Schema(description = "该笔资金操作流水opertion_id对应的操作信用金额")
    private String creditAmount;

    /**
     * 商户请求创建预授权订单时传入的扩展参数，仅返回商户自定义的扩展信息（merchantExt）
     */
    @Schema(description = "商户请求创建预授权订单时传入的扩展参数")
    private String extraParam;

    /**
     * 该笔资金操作流水opertion_id对应的操作自有资金金额
     */
    @Schema(description = "该笔资金操作流水opertion_id对应的操作自有资金金额")
    private String fundAmount;

    /**
     * 资金授权单据操作流水创建时间，
     * 格式：YYYY-MM-DD HH:MM:SS
     */
    @Schema(description = "资金授权单据操作流水创建时间")
    private Date gmtCreate;

    /**
     * 支付宝账务处理成功时间，
     * 格式：YYYY-MM-DD HH:MM:SS
     */
    @Schema(description = "支付宝账务处理成功时间")
    private Date gmtTrans;

    /**
     * 支付宝资金操作流水号
     */
    @Schema(description = "支付宝资金操作流水号")
    private String operationId;

    /**
     * 支付宝资金操作类型，
     * 目前支持：
     * FREEZE：冻结
     * UNFREEZE：解冻
     * PAY：支付
     */
    @Schema(description = "支付宝资金操作类型")
    private String operationType;

    /**
     * 业务订单的简单描述，如商品名称等
     */
    @Schema(description = "业务订单的简单描述，如商品名称等")
    private String orderTitle;

    /**
     * 商户的授权资金订单号
     */
    @Schema(description = "商户的授权资金订单号")
    private String outOrderNo;

    /**
     * 商户资金操作的请求流水号
     */
    @Schema(description = "商户资金操作的请求流水号")
    private String outRequestNo;

    /**
     * 付款方支付宝账号（Email或手机号），仅作展示使用，默认会加“*”号处理
     */
    @Schema(description = "付款方支付宝账号")
    private String payerLogonId;

    /**
     * 付款方支付宝账号对应的支付宝唯一用户号，以2088开头的16位纯数字组成
     */
    @Schema(description = "付款方支付宝账号对应的支付宝唯一用户号")
    private String payerUserId;

    /**
     * 预授权类型，目前支持 CREDIT_AUTH(信用预授权);
     * 商户可根据该标识来判断该笔预授权的类型，当返回值为"CREDIT_AUTH"表明该笔预授权为信用预授权，没有真实冻结资金；当返回值为空或者不为"CREDIT_AUTH"则表明该笔预授权为普通资金预授权，会冻结用户资金。
     */
    @Schema(description = "预授权类型")
    private String preAuthType;

    /**
     * 商户对本次操作的附言描述，长度不超过100个字母或50个汉字
     */
    @Schema(description = "商户对本次操作的附言描述")
    private String remark;

    /**
     * 订单总共剩余的冻结金额，单位为：元（人民币）
     */
    @Schema(description = "订单总共剩余的冻结金额")
    private String restAmount;

    /**
     * 剩余冻结信用金额，单位为：元（人民币），精确到小数点后两位
     */
    @Schema(description = "剩余冻结信用金额")
    private String restCreditAmount;

    /**
     * 剩余冻结自有资金金额，单位为：元（人民币），精确到小数点后两位
     */
    @Schema(description = "剩余冻结自有资金金额")
    private String restFundAmount;

    /**
     * 资金操作流水的状态，
     * 目前支持：
     * INIT：初始
     * SUCCESS：成功
     * CLOSED：关闭
     */
    @Schema(description = "资金操作流水的状态INIT：初始" + "     SUCCESS：成功" + "     CLOSED：关闭")
    private String status;

    /**
     * 订单累计的冻结金额，单位为：元（人民币）
     */
    @Schema(description = "订单累计的冻结金额")
    private String totalFreezeAmount;

    /**
     * 累计冻结信用金额，单位为：元（人民币），精确到小数点后两位
     */
    @Schema(description = "累计冻结信用金额")
    private String totalFreezeCreditAmount;

    /**
     * 累计冻结自有资金金额，单位为：元（人民币），精确到小数点后两位
     */
    @Schema(description = "累计冻结自有资金金额")
    private String totalFreezeFundAmount;

    /**
     * 订单累计用于支付的金额，单位为：元（人民币）
     */
    @Schema(description = "订单累计用于支付的金额")
    private String totalPayAmount;

    /**
     * 累计支付信用金额，单位为：元（人民币），精确到小数点后两位
     */
    @Schema(description = "累计支付信用金额")
    private String totalPayCreditAmount;

    /**
     * 累计支付自有资金金额，单位为：元（人民币），精确到小数点后两位
     */
    @Schema(description = "累计支付自有资金金额")
    private String totalPayFundAmount;

    /**
     * 标价币种,  amount 对应的币种单位。支持澳元：AUD, 新西兰元：NZD, 台币：TWD, 美元：USD, 欧元：EUR, 英镑：GBP
     */
    @Schema(description = "标价币种")
    private String transCurrency;

}
