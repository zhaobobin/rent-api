package com.rent.common.dto.backstage.request;


import com.rent.common.dto.user.BackstageFunctionChooseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 后台部门
 *
 * @author zhao
 * @Date 2020-06-24 11:47
 */
@Data
@Schema(description = "更新后台用户拥有的权限请求参数")
public class UpdateUserFunctionReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "用户id")
    @NotNull(message = "用户id不能为空")
    private Long backstageUserId;

    @Schema(description = "权限集合")
    private List<BackstageFunctionChooseDto> backstageFunctionChooseDtoList;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
