
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
 * 首页运营配置表
 *
 * @author youruo
 * @Date 2020-06-30 15:13
 */
@Data
@Accessors(chain = true)
@TableName("ct_ope_config")
public class OpeConfig {

    private static final long serialVersionUID = 1L;


    /**
     * Id
     * 
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    /**
     * 渠道ID
     * 
     */
    @TableField(value="channel_id")
    private String channelId;
    /**
     * 配置类型：1/banner 2/Icon 3/Loin 4/Waterfall
     * 
     */
    @TableField(value="index_type")
    private Integer indexType;
    /**
     * 首页配置ID
     * 
     */
    @TableField(value="config_id")
    private Integer configId;
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

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
