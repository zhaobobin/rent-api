package com.rent.common.dto.marketing;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.util.Date;

/**
 * banner配置
 *
 * @author xiaotong
 * @Date 2020-12-21 17:21
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "banner配置")
public class BannerConfigAddReqDto implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * Id
     *
     */
    @Schema(description = "Id")
    private Long id;

    /**
     * Banner图片
     *
     */
    @Schema(description = "Banner图片")
    private String url;

    /**
     * 渠道
     *
     */
    @Schema(description = "渠道")
    private String channelId;

    /**
     * 开关
     *
     */
    @Schema(description = "开关")
    private String openStatus;

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
    @Schema(description = "排序")
    private Integer sort;

    /**
     * 展示开始时间
     *
     */
    @Schema(description = "展示开始时间")
    private Date beginTime;

    /**
     * 展示结束时间
     *
     */
    @Schema(description = "展示结束时间")
    private Date endTime;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
