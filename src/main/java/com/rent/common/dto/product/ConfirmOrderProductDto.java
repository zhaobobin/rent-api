package com.rent.common.dto.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 下单时商品信息
 * @author zhaowenchao
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConfirmOrderProductDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * sku信息
     */
    private ProductSkusDto productSkus;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 商品主图
     */
    private String mainImage;

    /**
     * 规格信息
     */
    private List<String> specName;

    /**
     * 增值服务信息
     */
    private List<ProductAdditionalServicesDto> additionalServices;

    /**
     * sku 对应的 周期价格
     */
    private BigDecimal skuCyclePrice;

    /**
     * sku 对应的 周期价格
     */
    private BigDecimal skuCycleSalePrice;

    /**
     *
     */
    private String productFreightType;

    private Integer buyOutSupport;

    private String shopId;

    private Integer isOnLine;

}
