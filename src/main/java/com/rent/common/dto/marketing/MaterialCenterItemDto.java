
package com.rent.common.dto.marketing;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;


/**
 * 素材中心文件
 * @author zhaowenchao
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class MaterialCenterItemDto {

    /**
     * id
     */
    private Long id;

    /**
     * 素材所属分类ID
     */
    private Long categoryId;

    /**
     * 名称
     */
    private String name;

    /**
     * 文件路径
     */
    private String fileUrl;

    private Long createTime;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
