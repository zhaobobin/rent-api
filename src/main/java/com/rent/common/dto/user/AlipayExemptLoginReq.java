package com.rent.common.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @author zhaowenchao
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "用户信息请求类")
public class AlipayExemptLoginReq {

    @Schema(description = "支付宝小程序授权码")
    private String authCode;

    private String avatar;
    private String city;
    private String telephone;

    private String province;
    private String nickName;
    private String gender;

    private Map<String, Boolean> noSet;

}
