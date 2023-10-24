
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
 * @author zhaowenchao
 */
@Data
@Accessors(chain = true)
@TableName("ct_hot_search_config")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class HotSearchConfig {

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    @TableField(value="channel_id")
    private String channelId;

    @TableField(value="word")
    private String word;

    @TableField(value="index_sort")
    private Integer indexSort;

    @TableField(value="create_time")
    private Date createTime;

    @TableField(value="delete_time")
    private Date deleteTime;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
