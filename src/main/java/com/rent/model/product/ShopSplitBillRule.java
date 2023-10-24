
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
 * 店铺分账规则
 *
 * @author youruo
 * @Date 2020-06-17 10:49
 */
@Data
@Accessors(chain = true)
@TableName("ct_shop_split_bill_rule")
public class ShopSplitBillRule {

    private static final long serialVersionUID = 1L;


    /**
     * Id
     * 
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    /**
     * 分账账户ID
     * 
     */
    @TableField(value="account_id")
    private Long accountId;
    /**
     * 分账类型 买断|租金
     * 
     */
    @TableField(value="type")
    private String type;
    /**
     * 延迟天数
     * 
     */
    @TableField(value="delay_num")
    private Integer delayNum;
    /**
     * 延迟天数类型
     * 
     */
    @TableField(value="delay_type")
    private String delayType;
    /**
     * 分账比例，小于等于1
     * 
     */
    @TableField(value="scale")
    private BigDecimal scale;
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

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
