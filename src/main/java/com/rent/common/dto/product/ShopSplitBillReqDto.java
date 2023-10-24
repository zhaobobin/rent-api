package com.rent.common.dto.product;

import com.rent.common.dto.Page;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;

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
public class ShopSplitBillReqDto extends Page implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "店铺名称")
    private String shopName;

    @Schema(description = "分佣类型")
    private String typeInfo;

    @Schema(description = "状态")
    private String status;

    @Schema(description = "周期")
    private String cycle;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
