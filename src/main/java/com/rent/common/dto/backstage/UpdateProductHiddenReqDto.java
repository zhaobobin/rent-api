package com.rent.common.dto.backstage;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.util.List;


/**
 * @author zhaowenchao
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "修改已上架商品展示与隐藏状态")
public class UpdateProductHiddenReqDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "商品ID")
    private Boolean hidden;

    @Schema(description = "商品ID")
    private List<String> productIds;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
