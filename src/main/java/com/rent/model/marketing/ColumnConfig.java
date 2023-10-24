
package com.rent.model.marketing;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.util.Date;


/**
 * @author xiaotong
 */
@Data
@Accessors(chain = true)
@TableName("ct_config_column")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ColumnConfig {

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    @TableField(value="channel_id")
    private String channelId;

    @TableField(value="type")
    private String type;

    @TableField(value="name")
    private String name;

    @TableField(value="url")
    private String url;

    @TableField(value="jump_url")
    private String jumpUrl;

    @TableField(value="product_ids")
    private String productIds;

    @TableField(value="paperwork")
    private String paperwork;

    @TableField(value="create_time")
    private Date createTime;

    @TableField(value="update_time")
    private Date updateTime;



    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
