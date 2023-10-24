package com.rent.common.dto.backstage.resp;


import com.rent.common.enums.user.EnumBackstageUserStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.util.Date;


@Data
@Schema(description = "分页查看后台用户返回结果")
public class PageBackstageUserResp  implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    private Long id;

    @Schema(description = "成员账号")
    private String mobile;

    @Schema(description = "成员姓名")
    private String name;

    @Schema(description = "部门ID")
    private Long departmentId;

    @Schema(description = "部门名称")
    private String departmentName;

    @Schema(description = "邮箱地址")
    private String email;

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "是否启用")
    private EnumBackstageUserStatus status;

    @Schema(description = "最后登入")
    private Date updateTime;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
