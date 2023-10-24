package com.rent.common.dto.order;


import com.rent.common.dto.marketing.UserCouponDto;
import com.rent.common.dto.product.OrderProductDetailDto;
import com.rent.common.dto.product.ShopDto;
import com.rent.common.dto.user.UserCertificationDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Schema(description = "订单详情查询响应类")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailResponse implements Serializable {

    private static final long serialVersionUID = -2099298174199089131L;

    /** 订单信息 */
    @Schema(description = "订单信息")
    private UserOrdersDto userOrdersDto;
    /** 收货地址信息 */
    @Schema(description = "收货地址信息")
    private OrderAddressDto orderAddressDto;
    /** 订单金额信息 */
    @Schema(description = "订单金额信息")
    private UserOrderCashesDto userOrderCashesDto;
    /** 订单商品信息 */
    @Schema(description = "订单商品信息")
    private OrderProductDetailDto orderProductDetailDto;
    /** 店铺信息 */
    @Schema(description = "店铺信息")
    private ShopDto shopDto;
    /** 账单信息 */
    @Schema(description = "账单信息")
    private List<OrderByStagesDto> orderByStagesDtoList;
    /** 实名信息 */
    private UserCertificationDto userCertification;
    /** 买断信息 */
    private UserOrderBuyOutDto userOrderBuyOutDto;

    /** 订单唤回优惠券 */
    @Schema(description = "订单唤回优惠券")
    private UserCouponDto userCouponDto;

    private String pacUrl;
    private String pciUrl;
    private String pegUrl;
    private String orderContractUrl;

    /** 信审人员的二维码 */
    private String auditUserQrcodeUrl;
}
