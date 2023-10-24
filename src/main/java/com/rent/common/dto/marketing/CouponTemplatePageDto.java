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
public class CouponTemplatePageDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "Id")
    private Long id;

    @Schema(description = "bindId")
    private Long bindId;

    @Schema(description = "优惠券名称")
    private String title;

    @Schema(description = "使用场景 RENT=租赁 BUY_OUT=买断 FIRST:首期租赁券")
    private String scene;

    @Schema(description = "使用范围")
    private String rangeStr;

    @Schema(description = "最低使用金额")
    private BigDecimal minAmount;

    @Schema(description = "减免金额")
    private BigDecimal discountAmount;

    @Schema(description = "VALID：有效 INVALID：失效 RUN_OUT:已经领取完 UNASSIGNED:未配大礼包，ASSIGNED:已经分配大礼包")
    private String status;

    @Schema(description = "有效期开始时间")
    private Date startTime;

    @Schema(description = "有效期结束时间")
    private Date endTime;

    @Schema(description = "有效期，以用户领取时间+delay_day_num")
    private Integer delayDayNum;

    @Schema(description = "发放数量")
    private Integer num;

    @Schema(description = "已经领取的数量")
    private Integer bindNum;

    @Schema(description = "剩余的数量")
    private Integer leftNum;

    @Schema(description = "已经使用的数量")
    private Integer usedNum;

    @Schema(description = "未使用的数量")
    private Integer unusedNum;

    @Schema(description = "删除时间")
    private Date deleteTime;

    private CouponTemplateTypeEnum type;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
