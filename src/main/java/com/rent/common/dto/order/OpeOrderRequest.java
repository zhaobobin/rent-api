package com.rent.common.dto.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rent.common.enums.order.EnumOrderType;
import com.rent.common.enums.order.EnumViolationStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "运营后台查询条件")
public class OpeOrderRequest implements Serializable {

    private static final long serialVersionUID = -5884472235388978586L;

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

    @Schema(description = "订单状态")
    private String status;

    @Schema(description = "关单类型 01:未支付用户主动申请 02:支付失败 03:超时支付 04:已支付用户主动申请 05:风控拒绝 06:商家关闭(客户要求) 07:商家风控关闭订单 08:商家超时发货 09:平台关闭订单 10:用户主动申请")
    private String closeType;

     @Schema(description = "店铺id")
    private String shopId;

    @Schema(description = "页码")
    private Integer pageNumber = 1;

    @Schema(description = "页面大小")
    private Integer pageSize = 10;

    @Schema(description = "订单类型", required = true)
    private EnumOrderType orderType = EnumOrderType.GENERAL_ORDER;

    /**
     * 店铺名称
     */
    @Schema(description = "店铺名称")
    private String shopName;

    /**
     * 违约状态列表
     */
    private List<EnumViolationStatus> violationStatusList;

    @Schema(description = "收货人手机号 ")
    private String addressUserPhone;

    @Schema(description = "身份证号")
    private String idCard;

    @Schema(description = "还租结束时间 ",hidden = true)
    private Date unrentDateEnd;

    @Schema(description = "风控是否通过 ",hidden = true)
    private Boolean rentCredit;

    @Schema(description = "审核人 ",hidden = true)
    private Long approveUid;

    private String creditLevel;
    /**
     * 订单类型列表
     */
    private List<EnumOrderType> orderTypeList;

}
