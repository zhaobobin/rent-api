package com.rent.common.dto.product;

import com.rent.common.dto.order.OrderAdditionalServicesDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Schema(description = "转单商品参数")
public class TransferProductDto implements Serializable {

    private static final long serialVersionUID = -7033763585239002673L;

    /**
     * 商品Id-被转
     */
    @Schema(description = "商品Id-被转")
    private String transferredProductId;
    /**
     * 快照id-被转
     */
    @Schema(description = "快照id-被转")
    private Long transferredSnapShotId;
    /**
     * 商品sku_id-被转
     */
    @Schema(description = "商品sku_id-被转")
    private Long transferredSkuId;

    /**
     * 店铺id-被转
     */
    @Schema(description = "店铺id-被转")
    private String transferredShopId;

    /**
     * 商品sku_id-被转
     */
    @Schema(description = "增值服务-被转")
    private List<Integer> transferredAdditionalIds;

    /**
     * 商品sku_id-被转
     */
    @Schema(description = "增值服务-被转-实体")
    private List<OrderAdditionalServicesDto> orderAdditionalServicesDtos;


    /**
     * 店铺id-接手
     */
    @Schema(description = "店铺id-接手")
    private String transferShopId;
}
