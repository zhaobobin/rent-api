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
 * 平台物流表
 *
 * @author youruo
 * @Date 2020-06-16 11:46
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "平台物流表")
public class PlatformExpressDto implements Serializable {

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
     * 物流公司名称
     * 
     */
    @Schema(description = "物流公司名称")
    private String name;

    /**
     * 物流公司logo
     * 
     */
    @Schema(description = "物流公司logo")
    private String logo;

    /**
     * 物流公司简称
     * 
     */
    @Schema(description = "物流公司简称")
    private String shortName;

    /**
     * 物流公司官方电话
     * 
     */
    @Schema(description = "物流公司官方电话")
    private String telephone;

    /**
     * 排位，越大越靠前
     * 
     */
    @Schema(description = "排位，越大越靠前")
    private Integer index;

    /**
     * 支付宝对应的的code
     * 
     */
    @Schema(description = "支付宝对应的的code")
    private String aliCode;



    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
