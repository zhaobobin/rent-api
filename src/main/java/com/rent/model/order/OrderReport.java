
package com.rent.model.order;

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

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单报表统计
 *
 * @author xiaoyao
 * @Date 2020-08-11 16:17
 */
@Data
@Accessors(chain = true)
@TableName("ct_order_report")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class OrderReport {


    /**
     * 主键
     * 
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    /**
     * 统计时间
     * 
     */
    @TableField(value="statistics_date")
    private Long statisticsDate;
    /**
     * 总单量
     * 
     */
    @TableField(value="total_order_count")
    private Long totalOrderCount;
    /**
     * 成功下单单量
     * 
     */
    @TableField(value="success_order_count")
    private Long successOrderCount;
    /**
     * 成功下单总租金
     * 
     */
    @TableField(value="success_order_rent")
    private BigDecimal successOrderRent;
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
