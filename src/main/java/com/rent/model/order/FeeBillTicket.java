
package com.rent.model.order;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rent.common.enums.order.EnumOderFeeBillInvoiceStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 租押分离收费信息
 * @author zhao
 * @Date 2020-08-11 09:59
 */
@Data
@Accessors(chain = true)
@TableName("ct_fee_bill_ticket")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class FeeBillTicket {

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    /**
     * 店铺ID
     */
    @TableField(value="shop_id")
    private String shopId;

    /**
     * 费用金额
     */
    @TableField(value="amount")
    private BigDecimal amount;


    /**
     * 开发票状态
     */
    @TableField(value="ticket_status")
    private EnumOderFeeBillInvoiceStatus ticketStatus ;

    /**
     * 申请人
     */
    @TableField(value="apply_uid")
    private Long applyUid ;

    /**
     * 审核人
     */
    @TableField(value="audit_uid")
    private Long auditUid ;

    /**
     * 创建时间
     */
    @TableField(value="create_time")
    private Date createTime;

    /**
     * 创建时间
     */
    @TableField(value="update_time")
    private Date updateTime;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
