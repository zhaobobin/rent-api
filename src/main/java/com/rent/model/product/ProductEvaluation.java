
package com.rent.model.product;

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
 * 商品评论
 *
 * @author zhao
 * @Date 2020-07-22 20:51
 */
@Data
@Accessors(chain = true)
@TableName("ct_product_evaluation")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ProductEvaluation {


    /**
     * Id
     * 
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    /**
     * ParentId
     *
     */
    @TableField(value="product_id")
    private String productId;

    @TableField(value="order_id")
    private String orderId;



    /**
     * ParentId
     * 
     */
    @TableField(value="parent_id")
    private Long parentId;
    /**
     * 用户ID
     * 
     */
    @TableField(value="uid")
    private String uid;
    /**
     * 用户姓名
     * 
     */
    @TableField(value="user_name")
    private String userName;
    /**
     * 用户头像
     */
    @TableField(value="user_icon")
    private String userIcon;
    /**
     * 服务星级评价
     */
    @TableField(value="star_count_service")
    private Integer starCountService;

    /**
     * 物流星级评价
     */
    @TableField(value="star_count_express")
    private Integer starCountExpress;

    /**
     * 描述相符评价
     */
    @TableField(value="star_count_description")
    private Integer starCountDescription;

    /**
     * 评论内容
     */
    @TableField(value="content")
    private String content;
    /**
     * 是否是精选评价
     * 
     */
    @TableField(value="is_chosen")
    private String isChosen;

    /**
     * 是否包含图片
     */
    @TableField(value="contains_pic")
    private String containsPic;
    /**
     * 是否有追加评价
     */
    @TableField(value="contains_append")
    private String containsAppend;


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

    @TableField(value="channel")
    private String channel;
    /**
     * DeleteTime
     *
     */
    @TableField(value="status")
    private Integer status;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
