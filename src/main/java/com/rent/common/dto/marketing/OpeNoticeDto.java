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
 * 商家中心通知
 *
 * @author youruo
 * @Date 2021-08-16 16:10
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "商家中心通知")
public class OpeNoticeDto implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 主键id
     * 
     */
    @Schema(description = "主键id")
    private Long id;

    /**
     * 素材图片ID
     * 
     */
    @Schema(description = "素材图片ID")
    private Long materialItemId;

    @Schema(description = "素材图片路径")
    private String materialItemFileUrl;

    /**
     * 跳转链接
     * 
     */
    @Schema(description = "跳转链接")
    private String jumpUrl;

    /**
     * 创建时间
     * 
     */
    @Schema(description = "创建时间")
    private Date createTime;

    /**
     * DeleteTime
     * 
     */
    @Schema(description = "DeleteTime")
    private Date deleteTime;

    /**
     * UpdateTime
     * 
     */
    @Schema(description = "UpdateTime")
    private Date updateTime;

    /**
     * 序列
     * 
     */
    @Schema(description = "序列")
    private Integer indexSort;

    /**
     * 标题
     * 
     */
    @Schema(description = "标题")
    private String name;

    /**
     * 备注
     * 
     */
    @Schema(description = "备注")
    private String remark;



    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
