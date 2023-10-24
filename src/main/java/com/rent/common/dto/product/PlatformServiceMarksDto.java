package com.rent.common.dto.product;

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
 * 平台服务标表
 *
 * @author youruo
 * @Date 2020-06-22 10:35
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "平台服务标表")
public class PlatformServiceMarksDto implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * Id
     * 
     */
    @Schema(description = "Id")
    private Integer id;

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
     * 服务标说明
     * 
     */
    @Schema(description = "服务标说明")
    private String description;

    /**
     * 服务标类型 0为包邮 1为免押金 2为免赔 3为随租随还 4为全新品 5为分期支付
     * 
     */
    @Schema(description = "服务标类型 0为包邮 1为免押金 2为免赔 3为随租随还 4为全新品 5为分期支付")
    private Short type;

    /**
     * 图标
     * 
     */
    @Schema(description = "图标")
    private String icon;



    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
