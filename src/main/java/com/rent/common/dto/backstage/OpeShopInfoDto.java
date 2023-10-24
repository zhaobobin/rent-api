package com.rent.common.dto.backstage;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author xiaoyao
 * @version V1.0
 * @date 2020-8-4 上午 10:53:17
 * @since 1.0
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "店铺信息")
public class OpeShopInfoDto implements Serializable {

    private static final long serialVersionUID = 1243152998669934361L;

    @Schema(description = "店铺id")
    private String shopId;

    @Schema(description = "店铺名称")
    private String shopName;

    @Schema(description = "商检电话")
    private String telephone;

}
