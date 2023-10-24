
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
 * @author zhaowenchao
 */
@Data
@Accessors(chain = true)
@TableName("ct_tab")
public class Tab {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    @TableField(value="create_time")
    private Date createTime;

    @TableField(value="delete_time")
    private Date deleteTime;

    @TableField(value="update_time")
    private Date updateTime;

    @TableField(value="index_sort")
    private Integer indexSort;

    @TableField(value="name")
    private String name;

    @TableField(value="jump_url")
    private String jumpUrl;

    @TableField(value="channel_id")
    private String channelId;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
