package com.rent.model.order;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rent.common.enums.order.EnumOrderRemarkSource;
import com.rent.common.enums.order.EnumOrderResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.util.Date;

/**
 * 催收记录
 * @author Tooblack
 */
@Data
@Accessors(chain = true)
@TableName("ct_order_hasten")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderHasten {

    private static final long serialVersionUID = 1L;

    /**
     * Id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 催收记录来源 OPE：运营    BUSINESS：商户
     */
    @TableField(value = "source")
    private EnumOrderRemarkSource source;

    /**
     * 订单ID
     */
    @TableField(value = "order_id")
    private String orderId;
    /**
     * 催收人
     */
    @TableField(value = "user_name")
    private String userName;
    /**
     * 结果
     */
    @TableField(value = "result")
    private EnumOrderResult result;
    /**
     * 小记
     */
    @TableField(value = "notes")
    private String notes;
    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;
    /**
     * 删除时间
     */
    @TableField(value = "delete_time")
    private Date deleteTime;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
