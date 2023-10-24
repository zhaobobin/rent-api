package com.rent.common.dto.order.resquest;

import com.rent.common.dto.Page;
import com.rent.common.enums.order.EnumFeeBillType;
import com.rent.common.enums.order.EnumOderFeeBillInvoiceStatus;
import com.rent.common.enums.order.EnumSettleStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.util.Date;


@Data
@Schema(description = "分页查询费用账单 请求参数")
public class FeeBillReqDto extends Page implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "店铺名称")
    private String shopName;

    @Schema(description = "店铺ID")
    private String shopId;

    @Schema(description = "结算状态")
    private EnumSettleStatus status;

    @Schema(description = "开票状态")
    private EnumOderFeeBillInvoiceStatus ticketStatus ;

    @Schema(description = "费用类型")
    private EnumFeeBillType type;

    @Schema(description = "账单生成时间-开始时间")
    private Date startTime;

    @Schema(description = "账单生成时间-结束时间")
    private Date endTime;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
