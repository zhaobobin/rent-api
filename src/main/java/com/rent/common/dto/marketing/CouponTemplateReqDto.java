package com.rent.common.dto.marketing;


import com.rent.common.dto.Page;
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
public class CouponTemplateReqDto extends Page implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * Id
     * 
     */
    @Schema(description = "Id")
    private Long id;

    /**
     * 标题
     * 
     */
    @Schema(description = "标题")
    private String title;

    /**
     * 使用场景 RENT=租赁 BUY_OUT=买断
     * 
     */
    @Schema(description = "使用场景 RENT=租赁 BUY_OUT=买断")
    private String scene;

    /**
     * 展示说明
     * 
     */
    @Schema(description = "展示说明")
    private String displayNote;

    /**
     * 发放数量
     * 
     */
    @Schema(description = "发放数量")
    private Integer num;

    /**
     * 剩余数量
     * 
     */
    @Schema(description = "剩余数量")
    private Integer leftNum;

    /**
     * 最小使用金额
     * 
     */
    @Schema(description = "最小使用金额")
    private BigDecimal minAmount;

    /**
     * 减免金额
     * 
     */
    @Schema(description = "减免金额")
    private BigDecimal discountAmount;

    /**
     * 限制用户最多拥有数量
     * 
     */
    @Schema(description = "限制用户最多拥有数量")
    private Integer userLimitNum;

    /**
     * 有效期开始时间
     * 
     */
    @Schema(description = "有效期开始时间")
    private Date startTime;

    /**
     * 有效期结束时间
     * 
     */
    @Schema(description = "有效期结束时间")
    private Date endTime;

    /**
     * 有效期，以用户领取时间+delay_day_num
     * 
     */
    @Schema(description = "有效期，以用户领取时间+delay_day_num")
    private Integer delayDayNum;

    /**
     * 添加优惠券的店铺ID ，OPE表示是运营端添加的
     * 
     */
    @Schema(description = "添加优惠券的店铺ID ，OPE表示是运营端添加的")
    private String sourceShopId;

    @Schema(description = "添加优惠券的店铺名称")
    private String sourceShopName;

    /**
     * 是否针对新人 T：是 F：不是
     * 
     */
    @Schema(description = "是否针对新人 T：是 F：不是")
    private String forNew;

    /**
     * 是否是礼包优惠券 T：是 F：不是
     * 
     */
    @Schema(description = "是否是礼包优惠券 T：是 F：不是")
    private String forPackage;

    /**
     * 所属大礼包的ID
     * 
     */
    @Schema(description = "所属大礼包的ID")
    private Long packageId;

    /**
     * VALID：有效 INVALID：失效 RUN_OUT:已经领取完 UNASSIGNED:未配大礼包，ASSIGNED:已经分配大礼包
     * 
     */
    @Schema(description = "VALID：有效 INVALID：失效 RUN_OUT:已经领取完 UNASSIGNED:未配大礼包，ASSIGNED:已经分配大礼包")
    private String status;

    @Schema(description = "渠道编号")
    private String channelId;

    /**
     *  创建时间
     * 
     */
    @Schema(description = " 创建时间")
    private Date createTime;

    /**
     * DeleteTime
     * 
     */
    @Schema(description = "DeleteTime")
    private Date deleteTime;

    /**
     * UpdateTime
     * 
     */
    @Schema(description = "UpdateTime")
    private Date updateTime;

    private CouponTemplateTypeEnum type;

    private Boolean hasAssign;



    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
