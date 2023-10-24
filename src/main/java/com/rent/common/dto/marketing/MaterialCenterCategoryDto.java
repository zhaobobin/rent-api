
package com.rent.common.dto.marketing;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.util.Date;


/**
 * 素材中心-素材分类
 * @author zhaowenchao
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class MaterialCenterCategoryDto {

    /**
     * ID
     */
    private Long id;

    /**
     * 素材分类名称
     */
    private String name;

    /**
     * 宽度
     */
    private Integer width;

    /**
     * 高度
     */
    private Integer height;


    private Date createTime;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
