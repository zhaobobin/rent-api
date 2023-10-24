package com.rent.common.dto.order.response;

import com.rent.common.dto.order.ReletCyclePricesDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author xiaoyao
 * @version V1.0
 * @date 2020-8-10 上午 11:57:22
 * @since 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "续租订单展示页响应类")
public class UserOrderReletPageResponse implements Serializable {

    private static final long serialVersionUID = -2361973668482018938L;

    @Schema(description = "订单编号")
    private String orderId;

    @Schema(description = "起租时间")
    private Date rentStartDate;

    @Schema(description = "商品id")
    private String productId;

    @Schema(description = "skuId")
    private Long skuId;

    @Schema(description = "原订单编号")
    private String originalOrderId;

    /** 商品名称 */
    @Schema(description = "商品名称")
    private String productName;

    /** 商品主图片 */
    @Schema(description = "商品主图片")
    private String mainImageUrl;

    @Schema(description = "商品规格")
    private String skuTitle;

    @Schema(description = "周期价格")
    private List<ReletCyclePricesDto> reletCyclePricesDtoList;
}
