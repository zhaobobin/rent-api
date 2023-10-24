package com.rent.common.dto.order.resquest;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rent.common.enums.order.EnumOrderExamineStatus;
import com.rent.common.enums.order.EnumOrderType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "电审订单查询条件")
public class QueryTelephoneOrderAuditRequest implements Serializable {

    private static final long serialVersionUID = -2876741972895856085L;

    @Schema(description = "商品名称")
    private String productName;

    @Schema(description = "渠道id")
    private String channelId;

    @Schema(description = "订单号")
    private String orderId;

    @Schema(description = "下单人手机号")
    private String telephone;

    @Schema(description = "下单人姓名")
    private String userName;

    @Schema(description = "审核状态")
    private EnumOrderExamineStatus examineStatus;

    @Schema(description = "下单开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTimeStart;

    @Schema(description = "下单时间结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTimeEnd;

    @Schema(description = "页码")
    private Integer pageNumber = 1;

    @Schema(description = "页面大小")
    private Integer pageSize = 10;

    @Schema(description = "订单类型")
    private EnumOrderType orderType = EnumOrderType.GENERAL_ORDER;

    @Schema(description = "风控是否通过 ",hidden = true)
    private Boolean rentCredit;

    @Schema(description = "审核人 ")
    private String approveUid;

    /**
     * 店铺名称
     */
    @Schema(description = "店铺名称", required = false)
    private String shopName;

    @Schema(description = "店铺名称", required = false)
    private String creditLevel;

}
