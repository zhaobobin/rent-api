
package com.rent.model.marketing;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.util.Date;

/**
 * 订单投诉图片凭证
 *
 * @author youruo
 * @Date 2020-09-27 15:38
 */
@Data
@Accessors(chain = true)
@TableName("ct_order_complaints_image")
public class OrderComplaintsImage {

    private static final long serialVersionUID = 1L;


    /**
     * Id
     * 
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    /**
     * 投诉ID
     * 
     */
    @TableField(value="complaint_id")
    private Long complaintId;
    /**
     * 图片链接
     * 
     */
    @TableField(value="image_url")
    private String imageUrl;
    /**
     * CreateTime
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

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
