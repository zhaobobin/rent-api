package com.rent.model.order;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rent.common.enums.order.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户订单
 *
 * @author xiaoyao
 * @Date 2020-06-10 17:02
 */
@Data
@Accessors(chain = true)
@TableName("ct_user_orders")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserOrders {

    private static final long serialVersionUID = 1L;

    /**
     * Id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 下单用户id
     */
    @TableField(value = "uid")
    private String uid;
    /**
     * 下单用户姓名
     */
    @TableField(value = "user_name")
    private String userName;
    /**
     * CreateTime
     */
    @TableField(value = "create_time")
    private Date createTime;
    /**
     * UpdateTime
     */
    @TableField(value = "update_time")
    private Date updateTime;
    /**
     * DeleteTime
     */
    @TableField(value = "delete_time")
    private Date deleteTime;
    /**
     * 支付订单号
     */
    @TableField(value = "payment_no")
    private String paymentNo;
    /**
     * 支付时间
     */
    @TableField(value = "payment_time")
    private Date paymentTime;
    /**
     * 订单号
     */
    @TableField(value = "order_id")
    private String orderId;
    /**
     * 发货时间
     */
    @TableField(value = "delivery_time")
    private Date deliveryTime;

    /**
     * 订单状态
     */
    @TableField(value = "status")
    private EnumOrderStatus status;
    /**
     * 关单类型:01:未支付用户主动申请,02:支付失败,03:超时支付,04:已支付用户主动申请,05:风控拒绝,06:商家关闭订单,07:商家风控关闭订单,08:商家超时发货
     */
    @TableField(value = "close_type")
    private EnumOrderCloseType closeType;
    /**
     * 商品id
     */
    @TableField(value = "product_id")
    private String productId;
    /**
     * 租用时长
     */
    @TableField(value = "rent_duration")
    private Integer rentDuration;
    /**
     * 单价（日租金）
     */
    @TableField("price")
    private BigDecimal price;
    /**
     * 快递单号
     */
    @TableField(value = "express_no")
    private String expressNo;
    /**
     * 物流公司id
     */
    @TableField(value = "express_id")
    private Long expressId;
    /**
     * 租用开始时间
     */
    @TableField(value = "rent_start")
    private Date rentStart;
    /**
     * 租用结束时间
     */
    @TableField(value = "unrent_time")
    private Date unrentTime;
    /**
     * 退租物流单号
     */
    @TableField(value = "unrent_express_no")
    private String unrentExpressNo;
    /**
     * 是否违约 01正常 02结算单逾期 03提前归还 04账单逾期
     */
    @TableField(value = "is_violation")
    private EnumViolationStatus isViolation;
    /**
     * 退租物流id
     */
    @TableField(value = "unrent_express_id")
    private Long unrentExpressId;
    /**
     * 用户备注
     */
    @TableField(value = "remark")
    private String remark;
    /**
     * 商家备注
     */
    @TableField(value = "shop_remark")
    private String shopRemark;
    /**
     * 客服备注
     */
    @TableField(value = "service_remark")
    private String serviceRemark;
    /**
     * 归还备注
     */
    @TableField(value = "give_back_remark")
    private String giveBackRemark;
    /**
     * 结算备注
     */
    @TableField(value = "settlement_remark")
    private String settlementRemark;
    /**
     * 订单备注
     */
    @TableField(value = "order_remark")
    private String orderRemark;
    /**
     * 订单类型 01为常规订单 02为拼团订单    03 续租订单  04买断订单
     */
    @TableField(value = "type")
    private EnumOrderType type;
    /**
     * 店铺id 平台自动生成
     */
    @TableField(value = "shop_id")
    private String shopId;
    /**
     * 物流方式类型 01为快递 02为上门 03为自提
     */
    @TableField(value = "logistic_form")
    private EnumOrderLogisticForm logisticForm;
    /**
     * 结算时间
     */
    @TableField(value = "settlement_time")
    private Date settlementTime;
    /**
     * 归还时选择的自提点id，默认情况下与提货时选择的自提点相同，如果商品标有异地归还的服务标，则此处为之后选择的自提点id
     */
    @TableField(value = "give_back_offline_shop_id")
    private Integer giveBackOfflineShopId;
    /**
     * 确认结算时间
     */
    @TableField(value = "confirm_settlement_time")
    private Date confirmSettlementTime;
    /**
     * 收货时间
     */
    @TableField(value = "receive_time")
    private Date receiveTime;
    /**
     * 取消原因
     */
    @TableField(value = "cancel_reason")
    private String cancelReason;
    /**
     * 取消时间
     */
    @TableField(value = "cancel_time")
    private Date cancelTime;
    /**
     * 归还地址id
     */
    @TableField(value = "give_back_address_id")
    private Long giveBackAddressId;
    /**
     * 关闭时间
     */
    @TableField(value = "close_time")
    private Date closeTime;
    /**
     * 归还时间
     */
    @TableField(value = "return_time")
    private Date returnTime;
    /**
     * 00待平台审核，01待商家审核，02已审核
     */
    @Schema(description = "00待平台审核，01待商家审核，02已审核")
    private EnumOrderExamineStatus examineStatus;
    /**
     * 审核标签
     */
    @Schema(description = "00平台审核，01商家审核")
    @TableField(value = "audit_label")
    private EnumOrderAuditLabel auditLabel;
    /**
     * RequestNo
     */
    @TableField(value = "request_no")
    private String requestNo;
    /**
     * 渠道来源
     */
    @TableField(value = "channel_id")
    private String channelId;
    /**
     * 刷脸认证状态 01:未认证 02:认证中 03:已认证
     */
    @TableField(value = "face_auth_status")
    private String faceAuthStatus;
    /**
     * 付款方支付宝id
     */
    @TableField(value = "payer_user_id")
    private String payerUserId;
    /**
     * 原订单id
     */
    @TableField(value = "original_order_id")
    private String originalOrderId;

    /**
     * 延期归还天数
     */
    @TableField(value = "rent_credit")
    private Boolean rentCredit;

    /**
     * 延期归还天数
     */
    @TableField(value = "credit_level")
    private String creditLevel;
    /**
     * 延期归还天数
     */
    @TableField(value = "nsf_level")
    private String nsfLevel;



    /**
     * 串号
     */
    @TableField(value = "serial_number")
    private String serialNumber;

    /**
     * 成本价
     */
    @TableField(value = "cost_price")
    private BigDecimal costPrice;


    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

}
