package com.rent.common.dto.backstage.request;

import com.rent.common.dto.Page;
import com.rent.common.enums.user.EnumBackstageUserPlatform;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "菜单管理-菜单列表")
public class MenuPageReq extends Page {

    @Schema(description = "权限父级ID")
    private Long parentId;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "功能代码")
    private String code;

    @Schema(description = "类型")
    private String type;

    @Schema(description = "平台")
    private EnumBackstageUserPlatform platform;
}
