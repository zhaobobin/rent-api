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
 * icon配置
 *
 * @author xiaotong
 * @Date 2020-12-21 17:21
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "icon配置")
public class IconConfigAddReqDto implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * Id
     * 
     */
    @Schema(description = "Id")
    private Long id;

    /**
     * 渠道
     *
     */
    @Schema(description = "渠道")
    private String channelId;

    /**
     * 金刚区icon
     * 
     */
    @Schema(description = "金刚区icon")
    private String url;

    /**
     * 标题
     *
     */
    @Schema(description = "标题")
    private String title;

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
     * 创建时间
     * 
     */
    @Schema(description = "创建时间")
    private Date createTime;

    /**
     * 角标
     *
     */
    @Schema(description = "角标")
    private String tag;



    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
