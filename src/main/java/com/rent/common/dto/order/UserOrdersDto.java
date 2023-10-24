package com.rent.common.dto.order;

import com.rent.common.enums.order.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;
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
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "用户订单")
public class UserOrdersDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Id
     */
    @Schema(description = "Id")
    private Long id;

    /**
     * 下单用户id
     */
    @Schema(description = "下单用户id")
    private String uid;

    /**
     * CreateTime
     */
    @Schema(description = "CreateTime")
    private Date createTime;

    /**
     * UpdateTime
     */
    @Schema(description = "UpdateTime")
    private Date updateTime;

    /**
     * DeleteTime
     */
    @Schema(description = "DeleteTime")
    private Date deleteTime;

    /**
     * 支付订单号
     */
    @Schema(description = "支付订单号")
    private String paymentNo;

    /**
     * 支付时间
     */
    @Schema(description = "支付时间")
    private Date paymentTime;

    /**
     * 订单号
     */
    @Schema(description = "订单号")
    private String orderId;

    /**
     * 用户姓名
     */
    @Schema(description = "用户姓名")
    private String userName;

    /**
     * 发货时间
     */
    @Schema(description = "发货时间")
    private Date deliveryTime;

    /**
     * 收货地址id
     */
    @Schema(description = "收货地址id")
    private Integer addressId;

    /**
     * 订单状态
     */
    @Schema(description = "订单状态")
    private EnumOrderStatus status;
    /**
     * 关单类型:01:未支付用户主动申请,02:支付失败,03:超时支付,04:已支付用户主动申请,05:风控拒绝,06:商家关闭订单,07:商家风控关闭订单,08:商家超时发货
     */
    @Schema(description = "关单类型")
    private EnumOrderCloseType closeType;

    /**
     * 租用时长
     */
    @Schema(description = "租用时长")
    private Integer rentDuration;

    /**
     * 快递单号
     */
    @Schema(description = "快递单号")
    private String expressNo;

    /**
     * 物流公司id
     */
    @Schema(description = "物流公司id")
    private Long expressId;

    /**
     * 租用开始时间
     */
    @Schema(description = "租用开始时间")
    private Date rentStart;

    /**
     * 租用结束时间
     */
    @Schema(description = "租用结束时间")
    private Date unrentTime;

    /**
     * 退租物流单号
     */
    @Schema(description = "退租物流单号")
    private String unrentExpressNo;

    /**
     * 是否违约 01正常 02结算单逾期 03提前归还 04账单逾期
     */
    @Schema(description = "01正常 02结算单逾期 03提前归还 04账单逾期")
    private EnumViolationStatus isViolation;

    /**
     * 退租物流id
     */
    @Schema(description = "退租物流id")
    private Long unrentExpressId;

    /**
     * 用户备注
     */
    @Schema(description = "用户备注")
    private String remark;

    /**
     * 商家备注
     */
    @Schema(description = "商家备注")
    private String shopRemark;

    /**
     * 客服备注
     */
    @Schema(description = "客服备注")
    private String serviceRemark;

    /**
     * 归还备注
     */
    @Schema(description = "归还备注")
    private String giveBackRemark;

    /**
     * 结算备注
     */
    @Schema(description = "结算备注")
    private String settlementRemark;

    /**
     * 订单备注
     */
    @Schema(description = "订单备注")
    private String orderRemark;

    /**
     * 订单类型 0为常规订单 1为拼团订单    2 续租订单  3买断订单
     */
    @Schema(description = "订单类型 0为常规订单 1为拼团订单    2 续租订单  3买断订单")
    private EnumOrderType type;

    /**
     * 店铺id 平台自动生成
     */
    @Schema(description = "店铺id 平台自动生成")
    private String shopId;

    /**
     * 物流方式类型 0为快递 1为上门 2为自提
     */
    @Schema(description = "物流方式类型 0为快递 1为上门 2为自提")
    private EnumOrderLogisticForm logisticForm;

    /**
     * 结算时间
     */
    @Schema(description = "结算时间")
    private Date settlementTime;

    /**
     * 归还时选择的自提点id，默认情况下与提货时选择的自提点相同，如果商品标有异地归还的服务标，则此处为之后选择的自提点id
     */
    @Schema(description = "归还时选择的自提点id，默认情况下与提货时选择的自提点相同，如果商品标有异地归还的服务标，则此处为之后选择的自提点id")
    private Integer giveBackOfflineShopId;

    /**
     * 确认结算时间
     */
    @Schema(description = "确认结算时间")
    private Date confirmSettlementTime;

    /**
     * 收货时间
     */
    @Schema(description = "收货时间")
    private Date receiveTime;

    /**
     * 取消原因
     */
    @Schema(description = "取消原因")
    private String cancelReason;

    /**
     * 取消时间
     */
    @Schema(description = "取消时间")
    private Date cancelTime;

    /**
     * 归还地址id
     */
    @Schema(description = "归还地址id")
    private Long giveBackAddressId;

    /**
     * 关闭时间
     */
    @Schema(description = "关闭时间")
    private Date closeTime;

    /**
     * 归还时间
     */
    @Schema(description = "归还时间")
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
    private EnumOrderAuditLabel auditLabel;

    /**
     * RequestNo
     */
    @Schema(description = "RequestNo")
    private String requestNo;

    /**
     * 渠道来源
     */
    @Schema(description = "渠道来源")
    private String channelId;

    /**
     * 刷脸认证状态 01:未认证 02:认证中 03:已认证
     */
    @Schema(description = "刷脸认证状态 01:未认证 02:认证中 03:已认证")
    private String faceAuthStatus;

    @Schema(description = "总租金")
    private BigDecimal totalRent;
    @Schema(description = "商品id")
    private String productId;
    @Schema(description = "商品名称")
    private String productName;
    @Schema(description = "商家服务电话")
    private String serviceTel;
    @Schema(description = "商品图片列表")
    private Object images;
    @Schema(description = "商品标题")
    private String skuTitle;
    @Schema(description = "用户手机号码")
    private String telephone;
    @Schema(description = "当前期次")
    private Integer currentPeriods;
    @Schema(description = "总期次")
    private Integer totalPeriods;
    @Schema(description = "客户姓名")
    private String realName;
    @Schema(description = "数量")
    private Integer nums;
    @Schema(description = "租金")
    private BigDecimal realRent;
    @Schema(description = "租金")
    private BigDecimal rent;

    @Schema(description = "订单金额id")
    private Long orderCashId;
    @Schema(description = "身份证号")
    private String idCard;

    @Schema(description = "损坏金额")
    private BigDecimal damagePrice;
    @Schema(description = "丢失金额")
    private BigDecimal lostPrice;

    /**
     * 用户完结订单数量
     */
    @Schema(description = "用户完结订单数量")
    private Integer userFinishCount;

    /**
     * 已支付在途订单数量
     */
    @Schema(description = "已支付在途订单数量")
    private Integer userPayCount;

    /**
     * 商铺名字
     */
    @Schema(description = "商铺名字")
    private String shopName;

    /**
     * 分数
     */
    @Schema(description = "分数")
    private String score;

    /**
     * 已付租金
     */
    @Schema(description = "已付租金")
    private BigDecimal payRent;

    /**
     * 用户身份证ocr认证状态
     */
    @Schema(description = "用户身份证ocr认证状态")
    private Boolean userIdCardPhotoCertStatus;

    /**
     * 用户人脸认证状态
     */
    @Schema(description = "用户人脸认证状态")
    private Boolean userFaceCertStatus;

    /**
     * 店铺能否关闭订单
     */
    @Schema(description = "店铺能否关闭订单")
    private Boolean shopCanClose;

    /**
     * 用户订单是否支持买断 0不支持，1支持
     */
    @Schema(description = "用户订单是否支持买断 0不支持，1支持提前买断， 2支持到期买断")
    private Integer orderBuyOutSupport;


    @Schema(description = "归还规则 1支持提前归还 2支持到期归还")
    private Integer returnRule;



    /**
     * 来源名称
     */
    @Schema(description = "渠道名称")
    private String channelName;

    /**
     * 到期买断价
     */
    @Schema(description = "到期买断价")
    private BigDecimal expireBuyOutPrice;
    /**
     * 当前买断价
     */
    @Schema(description = "当前买断价")
    private BigDecimal endFund;

    @Schema(description = "评论状态 true:已评论，false:未评论")
    private Boolean evaluationStatus;

    @Schema(description = "是否展示续租按钮")
    private Boolean showReletButton;

    /* 是否显示归还按钮*/
    @Schema(description = "是否显示归还按钮 0:不显示 1:显示")
    private Integer isShowReturnButton;
    /**
     * 是否显示买断按钮
     */
    @Schema(description = "是否显示买断按钮 0:不显示 1:显示")
    private Integer isShowBuyOutButton;

    /**
     * 0-拒绝，1-通过
     */
    @Schema(description = "风控结果")
    private Boolean rentCredit;


    @Schema(description = "标签用户续租折扣")
    private BigDecimal reletDiscountRate;

    @Schema(description = "标签用户买断折扣")
    private BigDecimal buyoutDiscountRate;



    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
