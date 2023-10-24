package com.rent.common.dto.marketing;

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
 * 用户优惠券表
 *
 * @author zhao
 * @Date 2020-07-07 15:04
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "用户优惠券表")
public class UserCouponDto implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * Id
     * 
     */
    @Schema(description = "Id")
    private Long id;

    /**
     * TemplateId
     * 
     */
    @Schema(description = "TemplateId")
    private Long templateId;
    /**
     * templateBindId
     *
     */
    @Schema(description = "templateBindId")
    private Long templateBindId;

    /**
     * 优惠金额
     * 
     */
    @Schema(description = "优惠金额")
    private BigDecimal discountAmount;

    /**
     * 编号
     * 
     */
    @Schema(description = "编号")
    private String code;

    /**
     * 领取用户ID
     * 
     */
    @Schema(description = "领取用户ID")
    private String uid;

    /**
     * 领取用户的手机号码
     * 
     */
    @Schema(description = "领取用户的手机号码")
    private String phone;

    /**
     * 用户领取时间
     * 
     */
    @Schema(description = "用户领取时间")
    private Date receiveTime;

    /**
     * 领取方式 REQUEST：用户主动领取 ASSIGN：系统派发
     * 
     */
    @Schema(description = "领取方式 REQUEST：用户主动领取 ASSIGN：系统派发")
    private String receiveType;

    /**
     * 有效期开始时间
     * 
     */
    @Schema(description = "有效期开始时间")
    private Date startTime;

    /**
     * 有效期结束时间
     * 
     */
    @Schema(description = "有效期结束时间")
    private Date endTime;

    /**
     * 订单ID
     * 
     */
    @Schema(description = "订单ID")
    private String orderId;

    /**
     * 用户使用时间
     * 
     */
    @Schema(description = "用户使用时间")
    private Date useTime;

    /**
     * PackageId
     * 
     */
    @Schema(description = "PackageId")
    private Long packageId;

    /**
     * 来自的大礼包名称
     * 
     */
    @Schema(description = "来自的大礼包名称")
    private String packageName;

    /**
     * 状态：UNUSED：未使用 USED：已使用
     * 
     */
    @Schema(description = "状态：UNUSED：未使用 USED：已使用")
    private String status;



    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
