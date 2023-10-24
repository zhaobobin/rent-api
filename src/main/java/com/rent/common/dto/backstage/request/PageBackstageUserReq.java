package com.rent.common.dto.backstage.request;


import com.rent.common.dto.Page;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;


@Data
@Schema(description = "分页查看后台用户请求参数")
public class PageBackstageUserReq extends Page implements Serializable {

    @Schema(description = "成员姓名")
    private String name;

    @Schema(description = "部门ID")
    private Long departmentId;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
