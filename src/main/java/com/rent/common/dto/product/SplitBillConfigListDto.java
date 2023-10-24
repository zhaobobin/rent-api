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
 * 店铺分账规则
 *
 * @author youruo
 * @Date 2020-06-17 10:49
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "佣金设置列表页面数据封装")
public class SplitBillConfigListDto implements Serializable {

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

    @Schema(description = "添加记录的管理员")
    private String addUser;

    @Schema(description = "状态")
    private EnumSplitBillAccountStatus status;

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "周期")
    private List<String> cycle;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
