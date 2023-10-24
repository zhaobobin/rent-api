package com.rent.common.dto.product;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * 返回添加秒杀商品对应的数据实体
 *
 * @author youruo
 * @Date 2020-06-17 10:46
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "返回添加秒杀商品对应的数据实体")
public class ProductSkusDetailDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "productId")
    private String productId;

    @Schema(description = "商品名称")
    private String productName;


    @Schema(description = "SKUID")
    private Integer skuId;


    @Schema(description = "规格")
    private String skuSpe;

    @Schema(description = "图片")
    private String image;

    @Schema(description = "租期")
    private List<Integer> days;

    @Schema(description = "租期&价格")
    private List<ProductCyclePricesDto> cyclePrices ;




    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
