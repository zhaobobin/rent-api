package com.rent.common.dto.backstage.resp;


import com.rent.common.enums.user.EnumBackstageUserPlatform;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.util.List;


@Data
@Schema(description = "查看[后台用户|部门]拥有的权限返回结果")
public class AuthPageResp implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "权限ID")
    private Long id;

    @Schema(description = "权限父级ID")
    private Long parentId;

    @Schema(description = "权限名称")
    private String name;

    @Schema(description = "权限code")
    private String code;

    @Schema(description = "权限类型")
    private String type;

    @Schema(description = "权限平台")
    private EnumBackstageUserPlatform platform;

    @Schema(description = "用户是否已拥有权限，true表示拥有")
    private Boolean chosen;

    @Schema(description = "权限子节点")
    private List<AuthPageResp> child;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
