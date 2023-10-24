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
import java.util.Date;
import java.util.List;

/**
 * 店铺分账账户
 *
 * @author youruo
 * @Date 2020-06-17 10:49
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "店铺分账账户")
public class ShopSplitBillAccountDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "Id")
    private Long id;

    @Schema(description = "店铺ID")
    private String shopId;

    @Schema(description = "店铺名称")
    private String shopName;

    @Schema(description = "店铺企业资质信息")
    private String shopFirmInfo;

    @Schema(description = "分佣类型")
    private String typeInfo;

    @Schema(description = "商家支付宝账号")
    private String identity;

    @Schema(description = "商家支付宝实名认证姓名")
    private String name;

    @Schema(description = "添加记录的管理员")
    private String addUser;

    @Schema(description = "状态")
    private EnumSplitBillAccountStatus status;

    @Schema(description = "审核的管理员")
    private String auditUser;

    @Schema(description = "审核备注")
    private String auditRemark;

    @Schema(description = "审核时间")
    private Date auditTime;

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "更新时间")
    private Date updateTime;

    @Schema(description = "删除时间")
    private Date deleteTime;

    @Schema(description = "周期")
    private List<String> cycle;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
