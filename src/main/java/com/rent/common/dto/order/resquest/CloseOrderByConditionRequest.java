package com.rent.common.dto.order.resquest;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rent.common.enums.order.EnumOrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "运营后台待关闭订单查询条件")
public class CloseOrderByConditionRequest implements Serializable {

    private static final long serialVersionUID = -3140116561581077260L;

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

    @Schema(description = "下单开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTimeStart;

    @Schema(description = "下单时间结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTimeEnd;

    @Schema(description = "订单状态 01:待支付 02:支付中 03:已支付申请关单 04:待发货 05:待确认收货 06:租用中 07:待结算 08:结算待支付 09:订单完成 10:交易关闭")
    @NotNull
    private EnumOrderStatus status;

    @Schema(description = "页码")
    private Integer pageNumber = 1;

    @Schema(description = "页面大小")
    private Integer pageSize = 10;

    /**
     * 店铺名称
     */
    private String shopName;

}
