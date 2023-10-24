package com.rent.common.dto.product;

import com.rent.common.enums.product.EnumSplitBillAccountStatus;
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
 * 渠道账号分佣
 *
 * @author xiaotong
 * @Date 2020-06-17 10:49
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "渠道账号分佣")
public class ChannelSplitBillDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    private Long id;

    @Schema(description = "渠道名称")
    private String name;

    @Schema(description = "渠道编码")
    private String uid;

    @Schema(description = "分账比例，小于等于1")
    private BigDecimal scale;

    @Schema(description = "状态")
    private EnumSplitBillAccountStatus status;

    @Schema(description = "渠道账号")
    private String account;

    @Schema(description = "商家支付宝账号")
    private String identity;

    @Schema(description = "商家支付宝实名认证姓名")
    private String aliName;

    @Schema(description = "添加信息的人员")
    private String addUser;

    @Schema(description = "审核人员")
    private String auditUser;

    @Schema(description = "审核时间")
    private Date auditTime;

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "更新时间")
    private Date updateTime;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
