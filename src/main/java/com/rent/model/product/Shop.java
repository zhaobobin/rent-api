
package com.rent.model.product;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.util.Date;

/**
 * 店铺表
 *
 * @author youruo
 * @Date 2020-06-17 10:33
 */
@Data
@Accessors(chain = true)
@TableName("ct_shop")
public class Shop {

    private static final long serialVersionUID = 1L;


    /**
     * Id
     * 
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    /**
     * CreateTime
     * 
     */
    @TableField(value="create_time")
    private Date createTime;
    /**
     * UpdateTime
     * 
     */
    @TableField(value="update_time")
    private Date updateTime;
    /**
     * DeleteTime
     * 
     */
    @TableField(value="delete_time")
    private Date deleteTime;
    /**
     * 店铺名称
     * 
     */
    @TableField(value="name")
    private String name;
    /**
     * 店铺logo链接 80x80
     * 
     */
    @TableField(value="logo")
    private String logo;
    /**
     * 店铺背景图链接
     * 
     */
    @TableField(value="background")
    private String background;
    /**
     * 店铺介绍
     * 
     */
    @TableField(value="description")
    private String description;
    /**
     * 店铺的经营类型
     * 
     */
    @TableField(value="shop_type_id")
    private Integer shopTypeId;
    /**
     * 店铺主营类目id
     * 
     */
    @TableField(value="main_category_id")
    private Integer mainCategoryId;
    /**
     * 店铺状态 0待提交企业资质 1待填写店铺信息 2待提交品牌信息 3正在审核 4审核不通过 5审核通过，准备开店 6开店成功
     * 
     */
    @TableField(value="status")
    private Integer  status;
    /**
     * 店铺审核通过时间
     * 
     */
    @TableField(value="approval_time")
    private Date approvalTime;
    /**
     * 审核原因  拒绝时录入
     * 
     */
    @TableField(value="reason")
    private String reason;
    /**
     * 店铺注册时间，用于shop_id加密
     * 
     */
    @TableField(value="regist_time")
    private String registTime;
    /**
     * 由平台自动生成的唯一标识
     * 
     */
    @TableField(value="shop_id")
    private String shopId;
    /**
     * 店铺是否被冻结
     * 
     */
    @TableField(value="is_locked")
    private Integer isLocked;
    /**
     * 店铺冻结时间
     * 
     */
    @TableField(value="locked_time")
    private Date lockedTime;
    /**
     * 店铺是否被封
     * 
     */
    @TableField(value="is_disabled")
    private Integer isDisabled;
    /**
     * 商家客服电话
     * 
     */
    @TableField(value="service_tel")
    private String serviceTel;
    /**
     * 商家接收短信手机号码
     * 
     */
    @TableField(value="recv_msg_tel")
    private String recvMsgTel;
    /**
     * 是否优质商家
     * 
     */
    @TableField(value="is_high_quality")
    private Boolean isHighQuality;
    /**
     * 是否签约 0否 1是
     * 
     */
    @TableField(value="is_signing")
    private Integer isSigning;
    /**
     * 是否需要电审 0否 1是
     *
     */
    @TableField(value="is_phone_examination")
    private Integer isPhoneExamination;

    /**
     * 是否开启自动分配审核 0否 1是
     *
     */
    @TableField(value="is_auto_audit_user")
    private Integer isAutoAuditUser;


    /**
     * 店铺联系邮箱
     *
     */
    @TableField(value="user_email")
    private String userEmail;

    /**
     * 店铺联系人姓名
     *
     */
    @TableField(value="user_name")
    private String userName;
    /**
     * 店铺联系人电话
     *
     */
    @TableField(value="user_tel")
    private String userTel;

   /**
     * 支付宝账号
     *
     */
    @TableField(value="zfb_code")
    private String zfbCode;
   /**
     * 支付宝姓名
     *
     */
    @TableField(value="zfb_name")
    private String zfbName;

    /**
     * 店铺合同链接
     *
     */
    @TableField(value="shop_contract_link")
    private String shopContractLink;

    /**
     * 店铺合同链接
     *
     */
    @TableField(value="shop_address")
    private String shopAddress;

    /**
     * 合同code,电子签章存证时需要的字段
     *
     */
    @TableField(value="contract_code")
    private String contractCode;

    /**
     * 签署人流水号,电子签章存证时需要的字段
     *
     */
    @TableField(value="signer_code")
    private String signerCode;


    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
