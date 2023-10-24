package com.rent.common.dto.marketing;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 * @author zhaowenchao
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "用户领取大礼包")
public class BindCouponPackageReqDto {

    @Schema(description = "用户ID")
    private String uid;

    @Schema(description = "大礼包ID")
    private Long packageId;

    @Schema(description = "用户手机号码")
    private String phone;

    @Schema(description = "是否是新用户")
    private Boolean isNewUser;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
