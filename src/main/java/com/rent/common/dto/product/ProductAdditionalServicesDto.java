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
 * 商品增值服务表
 *
 * @author youruo
 * @Date 2020-06-16 15:07
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "商品增值服务表")
public class ProductAdditionalServicesDto implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * Id
     * 
     */
    @Schema(description = "Id")
    private Integer id;

    /**
     * 创建时间
     * 
     */
    @Schema(description = "创建时间")
    private Date createTime;

    /**
     * 更新时间
     * 
     */
    @Schema(description = "更新时间")
    private Date updateTime;

    /**
     * 删除时间
     * 
     */
    @Schema(description = "删除时间")
    private Date deleteTime;

    /**
     * 商品id
     * 
     */
    @Schema(description = "商品id")
    private String productId;

    /**
     * 店铺增值服务id
     * 
     */
    @Schema(description = "店铺增值服务id")
    private Integer shopAdditionalServicesId;


  /**
     * 店铺增值服务类
     *
     */
    @Schema(description = "店铺增值服务类")
    private ShopAdditionalServicesDto shopAdditionalServices;


    @Schema(description = "商品名称")
    private String productName;




    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
