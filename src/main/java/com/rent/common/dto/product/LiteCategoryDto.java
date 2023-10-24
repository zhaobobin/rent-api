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
 * 小程序lite版本分类信息
 * @author zhaowenchao
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "小程序查看类目信息响应类")
public class LiteCategoryDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "类目ID")
    private Integer id;

    @Schema(description = "类目名称")
    private String name;


    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
