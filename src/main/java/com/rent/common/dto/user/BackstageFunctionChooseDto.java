package com.rent.common.dto.user;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.util.List;


@Data
@Schema(description = "修改[用户|部门]权限，权限集合")
public class BackstageFunctionChooseDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "权限ID")
    private Long id;

    @Schema(description = "权限父级ID")
    private Long parentId;

    @Schema(description = "权限名称")
    private String name;

    @Schema(description = "权限code")
    private String code;

    @Schema(description = "用户是否已拥有权限，true表示拥有")
    private Boolean chosen;

    @Schema(description = "权限子节点")
    private List<BackstageFunctionChooseDto> child;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
