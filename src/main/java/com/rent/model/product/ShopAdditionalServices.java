
package com.rent.model.product;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 店铺增值服务表
 *
 * @author youruo
 * @Date 2020-06-17 10:35
 */
@Data
@Accessors(chain = true)
@TableName("ct_shop_additional_services")
public class ShopAdditionalServices {

    private static final long serialVersionUID = 1L;


    /**
     * Id
     * 
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    /**
     * 创建时间
     * 
     */
    @TableField(value="create_time")
    private Date createTime;
    /**
     * 更新时间
     * 
     */
    @TableField(value="update_time")
    private Date updateTime;
    /**
     * 删除时间
     * 
     */
    @TableField(value="delete_time")
    private Date deleteTime;
    /**
     * 店铺id
     * 
     */
    @TableField(value="shop_id")
    private String shopId;
    /**
     * 增值服务名称
     * 
     */
    @TableField(value="name")
    private String name;
    /**
     * 增值服务内容
     * 
     */
    @TableField(value="content")
    private String content;
    /**
     * 增值服务价格
     * 
     */
    @TableField(value="price")
    private BigDecimal price;
    /**
     * 增值服务审核状态；0:审核拒绝；1:审核中；2:审核成功；默认1
     * 
     */
    @TableField(value="approval_status")
    private Integer approvalStatus;
    /**
     * 增值服务状态；0:无效；1:有效；默认1（店铺删除此服务，修改此状态为0）
     * 
     */
    @TableField(value="status")
    private Integer  status;
    /**
     * 增值简短服务说明
     * 
     */
    @TableField(value="description")
    private String description;

    @TableField(value = "original_add_id")
    private Integer originalAddId;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
