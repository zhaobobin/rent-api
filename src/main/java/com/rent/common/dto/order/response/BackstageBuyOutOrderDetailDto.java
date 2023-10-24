package com.rent.common.dto.order.response;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.dto.backstage.OpeExpressInfo;
import com.rent.common.dto.order.OrderAddressDto;
import com.rent.common.dto.order.OrderRemarkDto;
import com.rent.common.dto.order.UserOrderBuyOutDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author xiaoyao
 * @version V1.0
 * @date 2020-8-4 下午 2:14:16
 * @since 1.0
 */
@Data
@Builder
@Schema(description = "买断订单详情")
@NoArgsConstructor
@AllArgsConstructor
public class BackstageBuyOutOrderDetailDto implements Serializable {

    private static final long serialVersionUID = -7784322901410597458L;

    @Schema(description = "买断订单信息")
    private UserOrderBuyOutDto userOrderBuyOutDto;
    @Schema(description = "源订单信息")
    private BuyOutOriginalOrderDto originalOrderDto;
    @Schema(description = "收货地址信息")
    private OrderAddressDto orderAddressDto;
    @Schema(description = "发货物流信息")
    private OpeExpressInfo receiptExpressInfo;
    @Schema(description = "平台备注信息")
    private Page<OrderRemarkDto> opeRemarkDtoPage;
    @Schema(description = "商家备注信息")
    private Page<OrderRemarkDto> businessRemarkDtoPage;
}
