package com.rent.common.dto.marketing;

import com.rent.common.enums.marketing.CouponPackageTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 优惠券大礼包
 *
 * @author zhao
 * @Date 2020-07-07 15:04
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "优惠券大礼包")
public class CouponPackageDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "Id")
    private Long id;

    @Schema(description = "Name")
    private String name;

    @Schema(description = "优惠券模版ID，多个以,分割开")
    private String templateIds;

    @Schema(description = "是否针对新用户：T：是。 F：否")
    private String forNew;

    @Schema(description = "VALID：有效 INVALID：失效 RUN_OUT:已经领取完")
    private String status;

    @Schema(description = "添加大礼包的商家ID，运营端为OPE")
    private String sourceShopId;

    @Schema(description = "添加大礼包的店铺名称，运营端为OPE")
    private String sourceShopName;


    @Schema(description = "优惠券用法 SINGLE-独立优惠券|ACTIVITY—营销活动")
    private CouponPackageTypeEnum type;

    @Schema(description = "LeftNum")
    private Integer leftNum;

    @Schema(description = "Num")
    private Integer num;

    @Schema(description = "UserLimitNum")
    private Integer userLimitNum;


    @Schema(description = "渠道编号")
    private String channelId;

    @Schema(description = "优惠券列表")
    private List<CouponTemplateDto> couponTemplateList;

    @Schema(description = "CreateTime")
    private Date createTime;



    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
