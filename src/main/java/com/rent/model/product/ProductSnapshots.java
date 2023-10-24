
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
 * 商品快照表
 *
 * @author youruo
 * @Date 2020-06-16 15:30
 */
@Data
@Accessors(chain = true)
@TableName("ct_product_snapshots")
public class ProductSnapshots {

    private static final long serialVersionUID = 1L;


    /**
     * Id
     * 
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
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
    /**
     * 商品id
     * 
     */
    @TableField(value="item_id")
    private String itemId;
    /**
     * 版本号 毫秒时间戳
     * 
     */
    @TableField(value="version")
    private Long version;
    /**
     * 快照数据
     * 
     */
    @TableField(value="data")
    private Object data;
    /**
     * 所属店铺id
     * 
     */
    @TableField(value="shop_id")
    private String shopId;
    /**
     * 0 待审核 1审核通过 2审核拒绝
     * 
     */
    @TableField(value="status")
    private Integer status;
    /**
     * 商品名字
     * 
     */
    @TableField(value="product_name")
    private String productName;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
