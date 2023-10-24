
package com.rent.model.order;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rent.common.enums.order.EnumOrderAuditRefuseType;
import com.rent.common.enums.order.EnumOrderAuditStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.util.Date;

/**
 * 订单审核记录表
 *
 * @author xiaoyao
 * @Date 2020-10-22 10:29
 */
@Data
@Accessors(chain = true)
@TableName("ct_order_audit_record")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class OrderAuditRecord {


    /**
     * 主键
     * 
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    /**
     * 订单编号
     * 
     */
    @TableField(value="order_id")
    private String orderId;
    /**
     * 审核人uid
     * 
     */
    @TableField(value="approve_uid")
    private String approveUid;
    /**
     * 审核人姓名
     * 
     */
    @TableField(value="approve_user_name")
    private String approveUserName;
    /**
     * 审核时间
     * 
     */
    @TableField(value="approve_time")
    private Date approveTime;
    /**
     * 审核结果 EnumOrderAuditStatus 00:待审核 01：通过 02：拒绝
     * 
     */
    @TableField(value="approve_status")
    private EnumOrderAuditStatus approveStatus;

    /**
     * 拒绝类型 REFUSE01("01","小贷过多"),
     *     REFUSE02("02", "在途订单过多"),
     *     REFUSE03("03", "偿还能力"),
     *     REFUSE04("04", "欺诈风险"),
     *     REFUSE05("05", "法院涉案"),
     *     REFUSE06("06", "非本人租用"),
     *     REFUSE07("07", "他处还款逾期"),
     *     REFUSE08("08", "客户失联"),
     *     REFUSE09("09", "敏感行业"),
     */
    @TableField(value="refuse_type")
    private EnumOrderAuditRefuseType refuseType;
    /**
     * 审核备注
     * 
     */
    @TableField(value="remark")
    private String remark;
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

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
