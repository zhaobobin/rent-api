package com.rent.common.dto.product;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author 12132
 */
@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "商品详情页面推荐商品")
public class ProductDetailRecommendResp implements Serializable {

	private static final long serialVersionUID = 4191915000995212741L;

	private String productId;

	private Integer oldNewDegree;

	private String imageSrc;

	private String name;

	private BigDecimal lowestSalePrice;

	private Integer salesVolume;

}
