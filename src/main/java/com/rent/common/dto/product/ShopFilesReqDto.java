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
import java.util.Date;

/**
 * 店铺文件表
 *
 * @author youruo
 * @Date 2020-06-17 10:47
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "店铺文件表")
public class ShopFilesReqDto extends Page implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * Id
     * 
     */
    @Schema(description = "Id")
    private Long id;

    /**
     * CreateTime
     * 
     */
    @Schema(description = "CreateTime")
    private Date createTime;

    /**
     * UpdateTime
     * 
     */
    @Schema(description = "UpdateTime")
    private Date updateTime;

    /**
     * DeleteTime
     * 
     */
    @Schema(description = "DeleteTime")
    private Date deleteTime;

    /**
     * 文件名
     * 
     */
    @Schema(description = "文件名")
    private String name;

    /**
     * 链接
     * 
     */
    @Schema(description = "链接")
    private String src;

    /**
     * 店铺id
     * 
     */
    @Schema(description = "店铺id")
    private String shopId;

    /**
     * IsDir
     * 
     */
    @Schema(description = "IsDir")
    private Integer isDir;

    /**
     * ParentId
     * 
     */
    @Schema(description = "ParentId")
    private Long parentId;

    /**
     * 图片宽度
     * 
     */
    @Schema(description = "图片宽度")
    private Integer width;

    /**
     * 图片高度
     * 
     */
    @Schema(description = "图片高度")
    private Integer height;



    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
