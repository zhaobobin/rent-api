package com.rent.common.dto.order.response;

import com.rent.common.enums.order.EnumOderFeeBillInvoiceStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Schema(description = "分页查看发票申请响应结果")
public class FeeBillTicketPageResp {

    @Schema(description = "id")
    private Long id;

    @Schema(description = "申请时间")
    private Date createTime;

    @Schema(description = "店铺名称")
    private String shopName;

    @Schema(description = "开票金额")
    private BigDecimal amount;

    @Schema(description = "申请状态")
    private EnumOderFeeBillInvoiceStatus ticketStatus ;

    @Schema(description = "审核时间")
    private Date updateTime;

    @Schema(description = "审核操作人")
    private String auditUser;

}
