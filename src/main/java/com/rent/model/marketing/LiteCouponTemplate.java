
package com.rent.model.marketing;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rent.common.enums.marketing.CouponTemplateTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

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
@TableName("ct_lite_coupon_template")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class LiteCouponTemplate {


    /**
     * Id
     * 
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    /**
     * bind_id
     */
    @TableField(value = "bind_id")
    private Long bindId;
    /**
     * 标题
     * 
     */
    @TableField(value="title")
    private String title;
    /**
     * 使用场景 RENT=租赁 BUY_OUT=买断
     * 
     */
    @TableField(value="scene")
    private String scene;
    /**
     * 展示说明
     * 
     */
    @TableField(value="display_note")
    private String displayNote;
    /**
     * 发放数量
     * 
     */
    @TableField(value="num")
    private Integer num;
    /**
     * 剩余数量
     * 
     */
    @TableField(value="left_num")
    private Integer leftNum;
    /**
     * 最小使用金额
     * 
     */
    @TableField(value="min_amount")
    private BigDecimal minAmount;
    /**
     * 减免金额
     * 
     */
    @TableField(value="discount_amount")
    private BigDecimal discountAmount;
    /**
     * 限制用户最多拥有数量
     * 
     */
    @TableField(value="user_limit_num")
    private Integer userLimitNum;
    /**
     * 有效期开始时间
     * 
     */
    @TableField(value="start_time")
    private Date startTime;
    /**
     * 有效期结束时间
     * 
     */
    @TableField(value="end_time")
    private Date endTime;
    /**
     * 有效期，以用户领取时间+delay_day_num
     * 
     */
    @TableField(value="delay_day_num")
    private Integer delayDayNum;

    @TableField(value="source_shop_id")
    private String sourceShopId;

    @TableField(value="source_shop_name")
    private String sourceShopName;

    /**
     * 是否针对新人 T：是 F：不是
     * 
     */
    @TableField(value="for_new")
    private String forNew;

    /**
     * 用法 SINGLE：独立   PACKAGE：大礼包 ALIPAY：券码券
     */
    @TableField(value="type")
    private CouponTemplateTypeEnum type;

    /**
     * 所属大礼包的ID
     * 
     */
    @TableField(value="package_id")
    private Long packageId;


    @TableField(value="ali_code_file")
    private String aliCodeFile;

    @TableField(value="bind_url")
    private String bindUrl;


    /**
     * VALID：有效 INVALID：失效 RUN_OUT:已经领取完 UNASSIGNED:未配大礼包，ASSIGNED:已经分配大礼包
     * 
     */
    @TableField(value="status")
    private String status;


    @TableField(value="channel_id")
    private String channelId;

    /**
     *  创建时间
     * 
     */
    @TableField(value="create_time")
    private Date createTime;
    /**
     * DeleteTime
     * 
     */
    @TableField(value="delete_time")
    private Date deleteTime;
    /**
     * UpdateTime
     * 
     */
    @TableField(value="update_time")
    private Date updateTime;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
