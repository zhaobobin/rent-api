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
 * 商品租赁标签
 *
 * @author youruo
 * @Date 2020-06-29 18:32
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "商品租赁标签")
public class ProductLabelDto implements Serializable {

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
     * 租赁标签
     * 
     */
    @Schema(description = "租赁标签")
    private String label;

    /**
     * 所属产品id
     * 
     */
    @Schema(description = "所属产品id")
    private String itemId;



    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
