package com.rent.common.dto.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rent.common.enums.order.EnumOrderAuditStatus;
import com.rent.common.enums.order.EnumOrderExamineStatus;
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
@Schema(description = "后台查询条件")
public class OrderByConditionRequest implements Serializable {

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

    @Schema(description = "订单状态 01:待支付 02:支付中 03:已支付申请关单 04:待发货 05:待确认收货 06:租用中 07:待结算 08:结算待支付 09:订单完成 10:交易关闭")
    private String status;

    @Schema(description = "关单类型 01:未支付用户主动申请 02:支付失败 03:超时支付 04:已支付用户主动申请 05:风控拒绝 06:商家关闭(客户要求) 07:商家风控关闭订单 08:商家超时发货 09:平台关闭订单 10:用户主动申请")
    private String closeType;

    @Schema(description = "店铺id")
    private String shopId;

    @Schema(description = "页码")
    private Integer pageNumber = 1;

    @Schema(description = "页面大小")
    private Integer pageSize = 10;

    @Schema(description = "订单类型 01:为常规订单 02:为拼团订单 03:续租订单  04:买断订单")
    private EnumOrderType orderType = EnumOrderType.GENERAL_ORDER;

    /**
     * 店铺名称
     */
    @Schema(description = "店铺名称", required = false)
    private String shopName;

    @Schema(description = "下单人身份证号")
    private String idCard;

    @Schema(description = "收货人电话")
    private String addressUserPhone;
    /**
     * 违约状态列表
     */
    private List<EnumViolationStatus> violationStatusList;

    /**
     * 订单类型列表
     */
    private List<EnumOrderType> orderTypeList;

    @Schema(description = "风控是否通过 ")
    private Boolean rentCredit;


    /**
     * 审核状态列表
     */
    private List<EnumOrderExamineStatus> examineStatusList;

    @Schema(description = "还租结束时间 ")
    private Date unrentDateEnd;

    private String creditLevel;

    /**
     * 审核结果
     */
    @Schema(description = "审核状态 ")
    private EnumOrderAuditStatus approveStatus;

    @Schema(description = "审核人 ")
    private Long approveUid;

    private List<Date> approveTime;

    @Schema(description = "是否是我的审核订单")
    private Boolean isMyAuditOrder = false;

    private Long backstageUserId;

}
