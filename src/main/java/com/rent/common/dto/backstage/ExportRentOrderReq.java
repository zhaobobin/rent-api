package com.rent.common.dto.backstage;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rent.common.enums.order.EnumOrderStatus;
import com.rent.common.enums.order.EnumOrderType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author zhaowenchao
 */
@Data
@Schema(description = "导出订单")
public class ExportRentOrderReq implements Serializable {

    private static final long serialVersionUID = -5884472235388978586L;

    @Schema(description = "下单开始时间 yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTimeStart;

    @Schema(description = "下单结束时间 yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTimeEnd;

    @Schema(description = "订单状态列表")
    private List<EnumOrderStatus> status;

    @Schema(description = "渠道编号")
    private String channelId;

    @Schema(description = "店铺id",hidden = true)
    private String shopId;

    @Schema(description = "订单类型", hidden = true)
    private EnumOrderType orderType;

    @Schema(description = "操作人员ID", hidden = true)
    private Long backstageUserId;


}
