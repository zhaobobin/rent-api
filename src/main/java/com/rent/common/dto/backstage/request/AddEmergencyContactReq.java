package com.rent.common.dto.backstage.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "用户紧急联系人")
public class AddEmergencyContactReq {

    @Schema(description = "订单编号")
    private String orderId;

    @Schema(description = "姓名")
    private String name;

    @Schema(description = "关系")
    private String relationship;

    @Schema(description = "手机号")
    private String mobile;
}
