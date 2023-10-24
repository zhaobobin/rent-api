package com.rent.common.dto.backstage;

import com.rent.common.dto.order.OpeUserOrderInfoDto;
import com.rent.common.dto.order.UserOrderBuyOutDto;
import com.rent.common.dto.order.UserOrderCashesDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;

/**
 * 分账信息
 *
 * @author zhao
 * @Date 2020-08-11 09:59
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "分账详细信息页面数据")
public class SplitBillDetailBaseDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "订单信息")
    private OpeUserOrderInfoDto orderInfoDto;

    @Schema(description = "商家信息")
    private OpeShopInfoDto shopInfoDto;

    @Schema(description = "商品信息")
    private OpeOrderProductInfo productInfo;

    @Schema(description = "账单信息")
    private UserOrderCashesDto userOrderCashesDto;

    @Schema(description = "买断信息")
    private UserOrderBuyOutDto UserOrderBuyOutDto;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
