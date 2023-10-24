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
 * 商品sku规格属性value表
 *
 * @author youruo
 * @Date 2020-06-16 15:28
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "商品sku规格属性value表")
public class ProductSkuValuesDto implements Serializable {

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
     * 商品参数值id 是product_spec_value中的id
     * 
     */
    @Schema(description = "商品参数值id 是product_spec_value中的id")
    private Long specValueId;

    /**
     * 所属sku值组的id
     * 
     */
    @Schema(description = "所属sku值组的id")
    private Long skuId;



    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
