package com.rent.common.dto.backstage.resp;

import com.rent.common.enums.user.EnumBackstageUserStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "信审管理-查看信审人员列表响应结果")
public class AuditUserPageResp{

    @Schema(description = "信审编号")
    private Long id;

    @Schema(description = "信审名称")
    private String name;

    @Schema(description = "用户ID")
    private Long backstageUserId;

    @Schema(description = "信审手机号")
    private String  mobile;

    @Schema(description = "信审员二维码")
    private String  qrcodeUrl;

    @Schema(description = "信审状态 VALID:启用 INVALID:禁用")
    private EnumBackstageUserStatus status;
}
