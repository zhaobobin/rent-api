package com.rent.common.dto.order;

import com.rent.common.dto.Page;
import com.rent.common.enums.product.EnumSplitBillAppVersion;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.util.Date;

/**
 * 账期表
 *
 * @author xiaotong
 * @Date 2020-06-16 10:09
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "账期表")
public class AccountPeriodItemReqDto extends Page implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "账期ID")
    private Long accountPeriodId;

    @Schema(description = "店铺ID")
    private String shopId;

    @Schema(description = "合作方式")
    private EnumSplitBillAppVersion appVersion;

    @Schema(description = "订单编号")
    private String orderId;

    @Schema(description = "账单生成开始时间")
    private Date startTime;

    @Schema(description = "账单生成结束时间")
    private Date endTime;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
