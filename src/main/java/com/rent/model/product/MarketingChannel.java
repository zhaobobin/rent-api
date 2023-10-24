
package com.rent.model.product;

import com.baomidou.mybatisplus.annotation.*;
import com.rent.common.enums.product.EnumSplitBillAccountStatus;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 渠道账号分佣表
 *
 * @author xiaotong
 * @Date 2020-06-17 10:49
 */
@Data
@Accessors(chain = true)
@TableName("ct_marketing_channel")
public class MarketingChannel {

    private static final long serialVersionUID = 1L;


    /**
     * Id
     * 
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    /**
     * 渠道名称
     */
    @TableField(value="name")
    private String name;

    /**
     * 渠道编号
     */
    @TableField(value="uid")
    private String uid;

    /**
     * 分账比例，小于等于1
     */
    @TableField(value="scale")
    private BigDecimal scale;

    /**
     * 状态
     */
    @TableField(value="status")
    private EnumSplitBillAccountStatus status;

    /**
     * 渠道账号
     */
    @TableField(value="account")
    private String account;

    /**
     * 商家支付宝账号
     */
    @TableField(value="identity")
    private String identity;

    /**
     * 商家支付宝实名认证姓名
     */
    @TableField(value="ali_name")
    private String aliName;


    /**
     * 添加信息的人员
     */
    @TableField(value="add_user")
    private String addUser;

    /**
     * 审核人员
     */
    @TableField(value="audit_user")
    private String auditUser;

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




    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
