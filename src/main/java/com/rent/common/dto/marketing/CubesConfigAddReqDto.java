package com.rent.common.dto.marketing;

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
 * 商品分类配置
 *
 * @author xiaotong
 * @Date 2020-12-21 17:21
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "商品分类配置")
public class CubesConfigAddReqDto implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * Id
     *
     */
    @Schema(description = "Id")
    private Long id;

    /**
     * 主标题
     *
     */
    @Schema(description = "主标题")
    private String paperwork;

    /**
     * 副标题
     *
     */
    @Schema(description = "副标题")
    private String paperworkCopy;

    /**
     * 跳转链接
     *
     */
    @Schema(description = "跳转链接")
    private String jumpUrl;

    /**
     * 排序
     *
     */
    @Schema(description = "背景图")
    private String url;

    /**
     * 商品id
     *
     */
    @Schema(description = "商品id")
    private List<String> productIds;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
