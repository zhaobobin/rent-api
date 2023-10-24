
package com.rent.model.product;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rent.common.enums.product.EnumProductAuditState;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.util.Date;

/**
 * 商品审核日志表
 *
 * @author youruo
 * @Date 2020-06-29 18:32
 */
@Data
@Builder
@Accessors(chain = true)
@TableName("ct_product_audit_log")
public class ProductAuditLog {

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
     * 商品审核状态 0为正在审核中 1为审核不通过 2为审核通过
     * 
     */
    @TableField(value="audit_status")
    private EnumProductAuditState auditStatus;
    /**
     * 反馈
     * 
     */
    @TableField(value="feed_back")
    private String feedBack;
    /**
     * 操作人
     * 
     */
    @TableField(value="operator")
    private String operator;
    /**
     * 商品ID
     *
     */
    @TableField(value="item_id")
    private String itemId;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
