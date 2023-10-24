package com.rent.common.dto.order.response;

import com.rent.common.enums.order.EnumFeeBillType;
import com.rent.common.enums.order.EnumOderFeeBillInvoiceStatus;
import com.rent.common.enums.order.EnumSettleStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Schema(description = "分页查看费用结算响应结果")
public class FeeBillPageResp implements Serializable {

    @Schema(description = "费用记录ID")
    private Long id;

    @Schema(description = "店铺名称")
    private String shopName;

    @Schema(description = "订单编号")
    private String orderId;

    @Schema(description = "金额")
    private BigDecimal amount;

    @Schema(description = "结算状态")
    private EnumSettleStatus status;

    @Schema(description = "费用类型")
    private EnumFeeBillType type;

    @Schema(description = "账单生成时间")
    private Date createTime;

    @Schema(description = "开票状态")
    private EnumOderFeeBillInvoiceStatus ticketStatus;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
