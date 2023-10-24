package com.rent.model.order;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rent.common.enums.order.EnumOrderRemarkSource;
import com.rent.common.enums.order.EnumOrderType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.util.Date;

/**
 * 订单备注
 *
 * @author xiaoyao
 * @Date 2020-06-16 10:09
 */
@Data
@Accessors(chain = true)
@TableName("ct_order_remark")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRemark {

    private static final long serialVersionUID = 1L;

    /**
     * Id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 备注来源 OPE：运营    BUSINESS：商户
     */
    @TableField(value = "source")
    private EnumOrderRemarkSource source;

    /**
     * 订单类型
     */
    @TableField(value = "order_type")
    private EnumOrderType orderType;
    /**
     * 订单ID
     */
    @TableField(value = "order_id")
    private String orderId;
    /**
     * 备注人姓名
     */
    @TableField(value = "user_name")
    private String userName;
    /**
     * 备注内容
     */
    @TableField(value = "remark")
    private String remark;
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
