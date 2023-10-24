package com.rent.common.dto.order.resquest;

import com.rent.common.dto.Page;
import com.rent.common.enums.order.EnumOderFeeBillInvoiceStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "分页查看发票申请请求参数")
public class FeeBillTicketPageReq extends Page {

    @Schema(description = "店铺名称")
    private String shopName;

    @Schema(description = "申请状态")
    private EnumOderFeeBillInvoiceStatus ticketStatus ;
}
