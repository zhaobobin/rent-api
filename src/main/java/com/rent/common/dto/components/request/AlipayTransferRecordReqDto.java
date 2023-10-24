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
 * 转账记录
 *
 * @author xiaoyao
 * @Date 2020-07-03 10:49
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "转账记录")
public class AlipayTransferRecordReqDto extends Page implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Id
     */
    @Schema(description = "Id")
    private Long id;

    /**
     * 外部订单号
     */
    @Schema(description = "外部订单号")
    private String outBizNo;

    /**
     * 支付宝账号
     */
    @Schema(description = "支付宝账号")
    private String identity;

    /**
     * 支付宝实名信息
     */
    @Schema(description = "支付宝实名信息")
    private String name;

    /**
     * uid
     */
    @Schema(description = "uid")
    private String uid;

    /**
     * 支付宝userId
     */
    @Schema(description = "支付宝userId")
    private String userId;

    /**
     * 金额
     */
    @Schema(description = "金额")
    private BigDecimal amount;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;

    /**
     * 状态
     */
    @Schema(description = "状态")
    private EnumAliPayStatus status;

    /**
     * 错误信息
     */
    @Schema(description = "错误信息")
    private String errorMsg;

    /**
     * 请求参数
     */
    @Schema(description = "请求参数")
    private String reqParams;

    /**
     * 响应参数
     */
    @Schema(description = "响应参数")
    private String resp;

    /**
     * 查询错误信息
     */
    @Schema(description = "查询错误信息")
    private String queryErrorInfo;

    /**
     * 查询响应结果
     */
    @Schema(description = "查询响应结果")
    private String queryResp;

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
