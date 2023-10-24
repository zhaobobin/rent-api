
package com.rent.model.product;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rent.common.enums.product.EnumShopWithdrawApplyStatus;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.math.BigDecimal;
import java.util.Date;


/**
 * 店铺资金变动流水
 * @author zhaowenchao
 */
@Data
@Accessors(chain = true)
@TableName("ct_shop_withdraw_apply")
public class ShopWithdrawApply {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    @TableField(value="shop_id")
    private String shopId;

    @TableField(value="apply_uid")
    private Long applyUid;

    @TableField(value="amount")
    private BigDecimal amount;

    @TableField(value="status")
    private EnumShopWithdrawApplyStatus status;

    @TableField(value="audit_uid")
    private Long auditUid;

    @TableField(value="out_order_no")
    private String outOrderNo;

    @TableField(value="create_time")
    private Date createTime;

    @TableField(value="update_time")
    private Date updateTime;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
