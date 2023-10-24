package com.rent.common.dto.product;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

/**
 * @author yr
 */
@Data
@Schema(description = "转单商品返回实体")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransferProductResponse implements Serializable {

    private static final long serialVersionUID = 9129675550421135874L;

    @Schema(description = "转单商品ID")
    private String newestProductId;
    @Schema(description = "转单商品对应的skuId")
    private Long newestSkuId;
    @Schema(description = "转单快照id")
    private Long newestSnapShotId;
    @Schema(description = "转单增值服务ID")
    private Map<Integer, Integer> newestShopAdditionalServicesId;


}
