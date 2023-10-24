
package com.rent.model.user;

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
 * 用户搜索记录
 *
 * @author zhao
 * @Date 2020-06-18 15:41
 */
@Data
@Accessors(chain = true)
@TableName("ct_user_word_history")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UserWordHistory {


    /**
     * Id
     * 
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    /**
     * CreateTime
     * 
     */
    @TableField(value="create_time")
    private Date createTime;

    /**
     * delete_time
     *
     */
    @TableField(value="delete_time")
    private Date deleteTime;
    /**
     * 用户搜索关键字
     * 
     */
    @TableField(value="word")
    private String word;
    /**
     * 用户ID
     * 
     */
    @TableField(value="uid")
    private String uid;

    @TableField(value="count")
    private Integer count;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
