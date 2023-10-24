package com.rent.common.dto.product;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;

/**
 * 小程序列表页面的商品信息
 * @author youruo
 * @Date 2020-06-16 15:06
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "商家商品列表Tab数量统计信息")
public class ProductCountsDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "全部商品")
    private Integer allCounts;

    @Schema(description = "已上架")
    private Integer putOnCounts;

    @Schema(description = "未上架")
    private Integer notPutOnCounts;

    @Schema(description = "待审核")
    private Integer pendingCounts;

    @Schema(description = "未通过")
    private Integer notPassCounts;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
