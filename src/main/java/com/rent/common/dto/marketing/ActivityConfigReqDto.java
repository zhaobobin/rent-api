package com.rent.common.dto.marketing;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;

/**
 * 活动专区配置
 *
 * @author xiaotong
 * @Date 2020-12-21 17:21
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "活动专区配置")
public class ActivityConfigReqDto implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * Id
     * 
     */
    @Schema(description = "Id")
    private Long id;

    /**
     * 背景图片
     * 
     */
    @Schema(description = "背景图片")
    private String url;

    /**
     * 跳转链接
     *
     */
    @Schema(description = "跳转链接")
    private String jumpUrl;




    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
