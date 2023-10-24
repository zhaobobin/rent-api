package com.rent.common.dto.order;

import com.rent.common.dto.Page;
import com.rent.common.enums.order.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户订单
 *
 * @author xiaoyao
 * @Date 2020-06-10 17:02
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserOrdersReqDto extends Page implements Serializable {

    private static final long serialVersionUID = 6753499257948038540L;


    /**
     * Id
     * 
     */
    private Long id;

    /**
     * 下单用户id
     * 
     */
    private String uid;

    /**
     * CreateTime
     * 
     */
    private Date createTime;

    /**
     * UpdateTime
     * 
     */
    private Date updateTime;

    /**
     * DeleteTime
     * 
     */
    private Date deleteTime;

    /**
     * 支付订单号
     * 
     */
    private String paymentNo;

    /**
     * 支付时间
     * 
     */
    private Date paymentTime;

    /**
     * 订单号
     * 
     */
    private String orderId;

    /**
     * 发货时间
     * 
     */
    private Date deliveryTime;

    /**
     * 收货地址id
     *
     */
    private Integer addressId;

    /**
     * 订单状态
     */
    private EnumOrderStatus status;

    /**
     * 关单类型:01:未支付用户主动申请,02:支付失败,03:超时支付,04:已支付用户主动申请,05:风控拒绝,06:商家关闭订单,07:商家风控关闭订单,08:商家超时发货
     */
    @Schema(description = "关单类型")
    private EnumOrderCloseType closeType;

    /**
     * 租用时长
     */
    private Integer rentDuration;

    /**
     * 快递单号
     * 
     */
    private String expressNo;

    /**
     * 物流公司id
     */
    private Long expressId;

    /**
     * 租用开始时间
     * 
     */
    private Date rentStart;

    /**
     * 租用结束时间
     * 
     */
    private Date unrentTime;

    /**
     * 退租物流单号
     * 
     */
    private String unrentExpressNo;

    /**
     * 是否违约 0否 1逾期 2提前归还
     *
     */
    private EnumViolationStatus isViolation;

    /**
     * 退租物流id
     * 
     */
    private Long unrentExpressId;

    /**
     * 用户备注
     * 
     */
    private String remark;

    /**
     * 商家备注
     * 
     */
    private String shopRemark;

    /**
     * 客服备注
     * 
     */
    private String serviceRemark;

    /**
     * 归还备注
     * 
     */
    private String giveBackRemark;

    /**
     * 结算备注
     * 
     */
    private String settlementRemark;

    /**
     * 订单备注
     * 
     */
    private String orderRemark;

    /**
     * 订单类型 0为常规订单 1为拼团订单    2 续租订单  3买断订单
     *
     */
    private EnumOrderType type;

    /**
     * 店铺id 平台自动生成
     * 
     */
    private String shopId;

    /**
     * 物流方式类型 0为快递 1为上门 2为自提
     *
     */
    private EnumOrderLogisticForm logisticForm;

    /**
     * 结算时间
     * 
     */
    private Date settlementTime;

    /**
     * 归还时选择的自提点id，默认情况下与提货时选择的自提点相同，如果商品标有异地归还的服务标，则此处为之后选择的自提点id
     * 
     */
    private Integer giveBackOfflineShopId;

    /**
     * 确认结算时间
     * 
     */
    private Date confirmSettlementTime;

    /**
     * 收货时间
     * 
     */
    private Date receiveTime;

    /**
     * 取消原因
     * 
     */
    private String cancelReason;

    /**
     * 取消时间
     * 
     */
    private Date cancelTime;

    /**
     * 归还地址id
     * 
     */
    private Long giveBackAddressId;

    /**
     * 关闭时间
     * 
     */
    private Date closeTime;

    /**
     * 归还时间
     * 
     */
    private Date returnTime;

    /**
     * 00待平台审核，01待商家审核，02已审核
     */
    @Schema(description = "00待平台审核，01待商家审核，02已审核")
    private EnumOrderExamineStatus examineStatus;

    /**
     * RequestNo
     * 
     */
    private String requestNo;

    /**
     * 渠道来源
     * 
     */
    private String channelId;

    /**
     * 刷脸认证状态 01:未认证 02:认证中 03:已认证
     * 
     */
    private String faceAuthStatus;



    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
