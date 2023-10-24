package com.rent.common.dto.backstage;

import com.rent.common.dto.order.response.BuyOutOriginalOrderDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Builder
@Schema(description = "分账详细信息页面数据")
public class SplitBillDetailBuyOutDto extends SplitBillDetailBaseDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "原订单信息")
    private BuyOutOriginalOrderDto originalOrderInfo;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
