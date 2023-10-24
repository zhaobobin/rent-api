package com.rent.common.dto.backstage.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @author udo
 */
@Data
@Schema(description = "后台用户登录返回结果")
public class LoginRespDto {

    @Schema(description = "用户登录token")
    private String token;

    @Schema(description = "用户拥有权限的菜单名称")
    private List<String> menus;

    @Schema(description = "用户对应的店铺ID")
    private String shopId;



}
