package com.rent.common.dto.marketing;

import com.rent.common.enums.marketing.CouponTemplateTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 优惠券模版
 *
 * @author zhao
 * @Date 2020-07-07 15:04
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "优惠券模版")
public class CouponTemplateDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "Id")
    private Long id;

    @Schema(description = "bindId")
    private Long bindId;

    @Schema(description = "标题")
    private String title;

    @Schema(description = "使用场景 RENT=租赁 BUY_OUT=买断")
    private String scene;

    @Schema(description = "展示说明")
    private String displayNote;

    @Schema(description = "发放数量")
    private Integer num;

    @Schema(description = "剩余数量")
    private Integer leftNum;

    @Schema(description = "最小使用金额")
    private BigDecimal minAmount;

    @Schema(description = "减免金额")
    private BigDecimal discountAmount;

    @Schema(description = "限制用户最多拥有数量")
    private Integer userLimitNum;

    @Schema(description = "有效期开始时间")
    private Date startTime;

    @Schema(description = "有效期结束时间")
    private Date endTime;

    @Schema(description = "有效期，以用户领取时间+delay_day_num")
    private Integer delayDayNum;

    @Schema(description = "添加优惠券的店铺ID ，OPE表示是运营端添加的")
    private String sourceShopId;

    @Schema(description = "添加优惠券的店铺名称")
    private String sourceShopName;

    @Schema(description = "是否针对新人 T：是 F：不是")
    private String forNew;

    @Schema(description = "是否是礼包优惠券 T：是 F：不是")
    private String forPackage;

    @Schema(description = "所属大礼包的ID")
    private Long packageId;

    private CouponTemplateTypeEnum type;

    @Schema(description = "VALID：有效 INVALID：失效 RUN_OUT:已经领取完 UNASSIGNED:未配大礼包，ASSIGNED:已经分配大礼包")
    private String status;

    @Schema(description = "使用范围描述字符")
    private String rangeStr;

    @Schema(description = "领取链接")
    private String bindUrl;

    @Schema(description = " 创建时间")
    private Date createTime;

    @Schema(description = "DeleteTime")
    private Date deleteTime;

    @Schema(description = "UpdateTime")
    private Date updateTime;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
