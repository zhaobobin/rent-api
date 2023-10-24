package com.rent.common.dto.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rent.common.enums.order.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author xiaoyao
 * @version V1.0
 * @date 2020-8-4 上午 10:42:38
 * @since 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "订单信息")
public class OpeUserOrderInfoDto implements Serializable {

    private static final long serialVersionUID = -1185259453679714668L;

    /**
     * 下单用户id
     */
    @Schema(description = "下单用户id")
    private String uid;

    /**
     * CreateTime
     */
    @Schema(description = "下单时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 支付时间
     */
    @Schema(description = "支付时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
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

    @Schema(description = "是否签约周期扣款")
    private Boolean cyclePaySigned;

    @Schema(description = "已支付押金")
    private BigDecimal paidDeposit;

    /**
     * 订单状态
     */
    @Schema(description = "订单状态 01:待支付 02:支付中 03:已支付申请关单 04:待发货 05:待确认收货 06:租用中 07:待结算 08:结算待支付 09:订单完成 10:交易关闭")
    private EnumOrderStatus status;
    /**
     * 关单类型:01:未支付用户主动申请,02:支付失败,03:超时支付,04:已支付用户主动申请,05:风控拒绝,06:商家关闭订单,07:商家风控关闭订单,08:商家超时发货
     */
    @Schema(description = "关单类型 01:未支付用户主动申请 02:支付失败 03:超时支付 04:已支付用户主动申请 05:风控拒绝 06:商家关闭订单 07:商家风控关闭订单 08:商家超时发货 09:平台关闭订单")
    private EnumOrderCloseType closeType;

    /**
     * 租用时长
     */
    @Schema(description = "租用时长")
    private Integer rentDuration;

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
     * 是否违约 0否 1逾期 2提前归还
     */
    @Schema(description = "01正常 02逾期 03提前归还")
    private EnumViolationStatus isViolation;

    /**
     * 用户备注
     */
    @Schema(description = "用户备注")
    private String remark;

    /**
     * 订单备注
     */
    @Schema(description = "订单备注")
    private String orderRemark;

    /**
     * 订单类型 01为常规订单 02为拼团订单 03续租订单  04买断订单
     */
    @Schema(description = "订单类型 01:为常规订单 02:为拼团订单 03:续租订单  04:买断订单")
    private EnumOrderType type;

    /**
     * 店铺id 平台自动生成
     */
    @Schema(description = "店铺id 平台自动生成")
    private String shopId;
    /**
     * 渠道来源
     */
    @Schema(description = "渠道来源")
    private String channelId;
    @Schema(description = "商品id")
    private String productId;
    @Schema(description = "用户手机号码")
    private String telephone;
    @Schema(description = "客户姓名")
    private String realName;
    @Schema(description = "数量")
    private Integer nums;
    @Schema(description = "身份证号")
    private String idCard;

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
     * 分数
     */
    @Schema(description = "分数")
    private String score;

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
     * 身份证照片 前面
     *
     */
    @Schema(description = "身份证照片 前面")
    private String idCardFrontUrl;

    /**
     * 身份证照片背面
     *
     */
    @Schema(description = "身份证照片背面")
    private String idCardBackUrl;

    /**
     * 来源名称
     */
    @Schema(description = "渠道名称")
    private String channelName;

    /**
     * 关单原因
     */
    @Schema(description = "关单原因")
    private String cancelReason;

    @Schema(description = "年龄")
    private int age;

    @Schema(description = "性别")
    private String gender;

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
     * 串号
     */
    private String serialNumber;

    /**
     * 成本价
     */
    private BigDecimal costPrice;

    /**
     * 成本价
     */
    private String supplier;

    /**
     * 销售价
     */
    private BigDecimal salePrice;

    /**
     * 到期买断价
     */
    private BigDecimal expireBuyOutPrice;

    /** 市场价 */
    private BigDecimal marketPrice;

    /** 市场价 */
    private BigDecimal firstMonth;

}
