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
 * 商品参数信息表
 *
 * @author youruo
 * @Date 2020-06-29 18:33
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "商品参数信息表")
public class ProductParameterDto implements Serializable {

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
     * 参数名称
     * 
     */
    @Schema(description = "参数名称")
    private String name;

    /**
     * 参数值
     * 
     */
    @Schema(description = "参数值")
    private String value;

    /**
     * 商品Id
     * 
     */
    @Schema(description = "商品Id")
    private String productId;



    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
