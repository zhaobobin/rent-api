package com.rent.common.dto.product;

import com.rent.common.dto.Page;
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
 * 店铺优惠券
 *
 * @author youruo
 * @Date 2020-06-17 10:42
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "店铺优惠券")
public class ShopCouponReqDto extends Page implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * Id
     * 
     */
    @Schema(description = "Id")
    private Long id;

    /**
     * CreateTime
     * 
     */
    @Schema(description = "CreateTime")
    private Date createTime;

    /**
     * UpdateTime
     * 
     */
    @Schema(description = "UpdateTime")
    private Date updateTime;

    /**
     * DeleteTime
     * 
     */
    @Schema(description = "DeleteTime")
    private Date deleteTime;

    /**
     * 优惠券所属店铺id
     * 
     */
    @Schema(description = "优惠券所属店铺id")
    private String shopId;

    /**
     * CouponId
     * 
     */
    @Schema(description = "CouponId")
    private String couponId;

    /**
     * 优惠券有效起始时间，如果type为1时，此处为空
     * 
     */
    @Schema(description = "优惠券有效起始时间，如果type为1时，此处为空")
    private Date start;

    /**
     * 优惠券失效时间，如果type为1时，此处为空
     * 
     */
    @Schema(description = "优惠券失效时间，如果type为1时，此处为空")
    private Date end;

    /**
     * 优惠券面值
     * 
     */
    @Schema(description = "优惠券面值")
    private BigDecimal value;

    /**
     * 优惠券满多少可用 0为不限制
     * 
     */
    @Schema(description = "优惠券满多少可用 0为不限制")
    private BigDecimal minPurchase;

    /**
     * 优惠券发放总量
     * 
     */
    @Schema(description = "优惠券发放总量")
    private Integer quantity;

    /**
     * 优惠券活动是否生效，0为未生效 1为生效 2为已失效
     * 
     */
    @Schema(description = "优惠券活动是否生效，0为未生效 1为生效 2为已失效")
    private Boolean status;

    /**
     * 优惠券类型 0为固定时间范围的优惠券 1为按领取日期计算失效时间的优惠券 
     * 
     */
    @Schema(description = "优惠券类型 0为固定时间范围的优惠券 1为按领取日期计算失效时间的优惠券 ")
    private Boolean type;

    /**
     * 如果type为1
     * 如果type为1, 即按领取日期，duration为自领取日期开始多少天后失效
     */
    @Schema(description = "如果type为1")
    private Integer duration;

    /**
     * 优惠券剩余数量
     * 
     */
    @Schema(description = "优惠券剩余数量")
    private Integer num;

    /**
     * 以用户为单位，优惠券限制领取多少张，0为不限制
     * 
     */
    @Schema(description = "以用户为单位，优惠券限制领取多少张，0为不限制")
    private Short limitNum;

    /**
     * 优惠券名称，如果不填，则为 value元优惠券
     * 
     */
    @Schema(description = "优惠券名称，如果不填，则为 value元优惠券")
    private String name;

    /**
     * 渠道值 
     * 
     */
    @Schema(description = "渠道值 ")
    private Integer channel;

    /**
     *  0 常规 1 首月0元租
     * 
     */
    @Schema(description = " 0 常规 1 首月0元租")
    private Boolean couponType;



    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
