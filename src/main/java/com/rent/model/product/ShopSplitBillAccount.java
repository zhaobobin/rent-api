
package com.rent.model.product;

import com.baomidou.mybatisplus.annotation.*;
import com.rent.common.enums.product.EnumSplitBillAccountStatus;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.util.Date;

/**
 * 店铺分账账户
 *
 * @author youruo
 * @Date 2020-06-17 10:49
 */
@Data
@Accessors(chain = true)
@TableName("ct_shop_split_bill_account")
public class ShopSplitBillAccount {

    private static final long serialVersionUID = 1L;


    /**
     * Id
     * 
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    /**
     * 店铺ID
     */
    @TableField(value="shop_id")
    private String shopId;

    /**
     * 店铺名称
     */
    @TableField(value="shop_name")
    private String shopName;

    /**
     * 店铺企业信息
     */
    @TableField(value="shop_firm_info")
    private String shopFirmInfo;
    /**
     * 商家支付宝账号
     */
    @TableField(value="identity")
    private String identity;
    /**
     * 商家支付宝实名认证姓名
     */
    @TableField(value="name")
    private String name;
    /**
     * 添加记录的管理员
     */
    @TableField(value="add_user")
    private String addUser;
    /**
     * 状态
     */
    @TableField(value="status")
    private EnumSplitBillAccountStatus status;
    /**
     * 审核的管理员
     */
    @TableField(value="audit_user",updateStrategy = FieldStrategy.IGNORED )
    private String auditUser;
    /**
     * 审核备注
     */
    @TableField(value="audit_remark",updateStrategy = FieldStrategy.IGNORED)
    private String auditRemark;
    /**
     * 审核时间
     */
    @TableField(value="audit_time",updateStrategy = FieldStrategy.IGNORED)
    private Date auditTime;
    /**
     * 创建时间
     */
    @TableField(value="create_time")
    private Date createTime;
    /**
     * 更新时间
     */
    @TableField(value="update_time")
    private Date updateTime;
    /**
     * 删除时间
     */
    @TableField(value="delete_time")
    private Date deleteTime;

    /**
     * 周期
     */
    @TableField(value="cycle")
    private String cycle;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
