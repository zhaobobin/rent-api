
package com.rent.model.product;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.util.Date;

/**
 * 渠道下的营销码汇总表
 *
 * @author xiaotong
 * @Date 2020-06-17 10:49
 */
@Data
@Accessors(chain = true)
@TableName("ct_marketing_channel_link")
public class ChannelStore {

    private static final long serialVersionUID = 1L;


    /**
     * Id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 首页链接
     */
    @TableField(value = "link")
    private String link;

    /**
     * 营销码
     */
    @TableField(value = "marketing_id")
    private String marketingId;

    /**
     * 渠道id
     */
    @TableField(value = "marketing_channel_id")
    private String marketingChannelId;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 删除时间
     */
    @TableField(value = "delete_time")
    private Date deleteTime;

    /**
     * 失效保护天数
     */
    @TableField(value = "expire_day")
    private Integer expireDay;

    /**
     * 是否开启绑定保护，若为1:则用户只要是扫这个码进来就是这个链接的，0:则有expire_day的保护期天数
     */
    @TableField(value = "expire_switch")
    private Integer expireSwitch;

    @TableField(value = "shop_name")
    private String shopName;

    @TableField(value = "telephone")
    private String telephone;

    @TableField(value = "realname")
    private String realName;

    @TableField(value = "uid")
    private String uid;


    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
