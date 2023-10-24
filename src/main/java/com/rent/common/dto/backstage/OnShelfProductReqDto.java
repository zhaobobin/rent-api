package com.rent.common.dto.backstage;

import com.rent.common.dto.Page;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.util.Date;


/**
 * @author zhaowenchao
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "已上架商品")
public class OnShelfProductReqDto extends Page implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "所属店铺id")
    private String shopId;

    @Schema(description = "商店名称")
    private String shopName;

    @Schema(description = "商品id")
    private String productId;

    @Schema(description = "商品名称")
    private String name;

    @Schema(description = "系统分值")
    private Integer sysScore;

    @Schema(description = "添加分值")
    private Integer addScore;

    @Schema(description = "过期时间")
    private Date expireTime;

    @Schema(description = "是否隐藏")
    private Boolean hidden;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
