package com.rent.common.dto.order.resquest;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "买断订单列表查询条件")
public class QueryBuyOutOrdersRequest implements Serializable {

    private static final long serialVersionUID = -5884472235388978586L;

    @Schema(description = "渠道id")
    private String channelId;

    @Schema(description = "原订单编号")
    private String orderId;

    @Schema(description = "订单编号")
    private String buyOutOrderId;

    @Schema(description = "商品名称")
    private String productName;

    @Schema(description = "订单子状态  01:取消 02:关闭 03:完成 04:待支付 05:支付中")
    private String status;

    @Schema(description = "下单人姓名")
    private String userName;

    @Schema(description = "下单人手机号")
    private String telephone;

    @Schema(description = "创建开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTimeStart;

    @Schema(description = "创建结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTimeEnd;

    @Schema(description = "页码")
    private Integer pageNumber = 1;

    @Schema(description = "页面大小")
    private Integer pageSize = 10;

    /** 店铺id */
    private String shopId;

    public Integer getPageLimit() {
        return (pageNumber - 1) * pageSize;
    }

}
