
package com.rent.model.product;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rent.common.enums.product.EnumShopFundOperate;
import com.rent.common.enums.product.EnumShopFundStatus;
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
@TableName("ct_shop_fund_flow")
public class ShopFundFlow {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;


    @TableField(value="shop_id")
    private String shopId;

    @TableField(value="operate")
    private EnumShopFundOperate operate;

    @TableField(value="operator")
    private String operator;

    @TableField(value="change_amount")
    private BigDecimal changeAmount;

    @TableField(value="before_amount")
    private BigDecimal beforeAmount;

    @TableField(value="after_amount")
    private BigDecimal afterAmount;

    @TableField(value="flow_no")
    private String flowNo;

    @TableField(value="remark")
    private String remark;

    @TableField(value="status")
    private EnumShopFundStatus status;

    @TableField(value="create_time")
    private Date createTime;

    @TableField(value="update_time")
    private Date updateTime;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
