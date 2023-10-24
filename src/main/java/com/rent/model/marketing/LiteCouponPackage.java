
package com.rent.model.marketing;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rent.common.enums.marketing.CouponPackageTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.util.Date;


/**
 * 优惠券大礼包
 *
 * @author zhao
 * @Date 2020-07-07 15:04
 */
@Data
@Accessors(chain = true)
@TableName("ct_lite_coupon_package")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class LiteCouponPackage {

    /**
     * Id
     * 
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    /**
     * Name
     *
     */
    @TableField(value="bind_id")
    private Long bindId;
    /**
     * Name
     * 
     */
    @TableField(value="name")
    private String name;

    /**
     * 包含的优惠券的使用场景
     */
    @TableField(value="contain_scene")
    private String containScene;


    /**
     * 优惠券模版ID，多个以"
     * 优惠券模版ID，多个以","分隔开
     */
    @TableField(value="template_ids")
    private String templateIds;
    /**
     * 是否针对新用户：T：是。 F：否
     * 
     */
    @TableField(value="for_new")
    private String forNew;
    /**
     * VALID：有效 INVALID：失效 RUN_OUT:已经领取完
     * 
     */
    @TableField(value="status")
    private String status;


    @TableField(value="type")
    private CouponPackageTypeEnum type;
    /**
     * LeftNum
     * 
     */
    @TableField(value="left_num")
    private Integer leftNum;
    /**
     * Num
     * 
     */
    @TableField(value="num")
    private Integer num;

    @TableField(value="channel_id")
    private String channelId;
    /**
     * UserLimitNum
     * 
     */
    @TableField(value="user_limit_num")
    private Integer userLimitNum;

    /**
     * CreateTime
     * 
     */
    @TableField(value="create_time")
    private Date createTime;
    /**
     * UpdateTime
     * 
     */
    @TableField(value="update_time")
    private Date updateTime;
    /**
     * DeleteTime
     * 
     */
    @TableField(value="delete_time")
    private Date deleteTime;

    @TableField(value="source_shop_id")
    private String sourceShopId;

    @TableField(value="source_shop_name")
    private String sourceShopName;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
