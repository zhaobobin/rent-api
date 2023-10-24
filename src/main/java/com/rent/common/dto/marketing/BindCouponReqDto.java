package com.rent.common.dto.marketing;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.util.Date;

/**
 * @author zhaowenchao
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "用户领取优惠券")
public class BindCouponReqDto {

    @Schema(description = "用户ID")
    private String uid;

    @Schema(description = "优惠券模板ID")
    private Long templateId;

    @Schema(description = "用户手机号码")
    private String phone;

    @Schema(description = "是否是新用户")
    private Boolean isNewUser;


    /**
     * 优惠券有效开始时间
     */
    private Date startTime;
    /**
     * 优惠券有效结束时间
     */
    private Date endTime;
    /**
     * 优惠券使用订单
     */
    private String orderId;



    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
