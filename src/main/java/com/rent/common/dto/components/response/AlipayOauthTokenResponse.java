package com.rent.common.dto.components.response;

import com.alipay.api.AlipayResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xiaoyao
 * @version V1.0
 * @date 2020-7-3 下午 1:46:22
 * @since 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "获取授权令牌")
public class AlipayOauthTokenResponse extends AlipayResponse {
    /**
     * 访问令牌。通过该令牌调用需要授权类接口
     */
    @Schema(description = "访问令牌")
    private String accessToken;

    /**
     * 令牌类型，permanent表示返回的access_token和refresh_token永久有效，非永久令牌不返回该字段
     */
    @Schema(description = "令牌类型，permanent表示返回的access_token和refresh_token永久有效，非永久令牌不返回该字段")
    private String authTokenType;

    /**
     * 访问令牌的有效时间，单位是秒。
     */
    @Schema(description = "访问令牌的有效时间，单位是秒。")
    private String expiresIn;

    /**
     * 刷新令牌的有效时间，单位是秒。
     */
    @Schema(description = "刷新令牌的有效时间，单位是秒。")
    private String reExpiresIn;

    /**
     * 刷新令牌。通过该令牌可以刷新access_token
     */
    @Schema(description = "刷新令牌。通过该令牌可以刷新access_token")
    private String refreshToken;

    /**
     * 支付宝用户的唯一userId
     */
    @Schema(description = "付宝用户的唯一userId")
    private String userId;

    @Schema(description = "付宝用户的唯一userId")
    private String openId;
}
