
package com.rent.model.product;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 店铺资质表
 *
 * @author youruo
 * @Date 2020-06-17 10:46
 */
@Data
@Accessors(chain = true)
@TableName("ct_shop_enterprise_infos")
public class ShopEnterpriseInfos {

    private static final long serialVersionUID = 1L;


    /**
     * Id
     * 
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    /**
     * 所属店铺id
     * 
     */
    @TableField(value="shop_id")
    private String shopId;
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
     * 企业名称
     * 
     */
    @TableField(value="name")
    private String name;
    /**
     * 企业注册资金
     * 
     */
    @TableField(value="registration_capital")
    private BigDecimal registrationCapital;
    /**
     * 营业执照号
     * 
     */
    @TableField(value="business_license_no")
    private String businessLicenseNo;
    /**
     * 营业执照有效期起始时间
     * 
     */
    @TableField(value="license_start")
    private Date licenseStart;
    /**
     * 营业执照有效期结束时间 如果为长期，则为2099-01-01
     * 
     */
    @TableField(value="license_end")
    private Date licenseEnd;
    /**
     * 营业执照所在省
     * 
     */
    @TableField(value="license_province")
    private Integer licenseProvince;
    /**
     * 营业执照所在市
     * 
     */
    @TableField(value="license_city")
    private Integer licenseCity;
    /**
     * 营业执照经营范围
     * 
     */
    @TableField(value="business_scope")
    private String businessScope;
    /**
     * 法人代表姓名
     * 
     */
    @TableField(value="realname")
    private String realname;
    /**
     * 法人手机号码
     * 
     */
    @TableField(value="telephone")
    private String telephone;
    /**
     * 联系人姓名
     * 
     */
    @TableField(value="contact_name")
    private String contactName;
    /**
     * 联系人手机号
     * 
     */
    @TableField(value="contact_telephone")
    private String contactTelephone;
    /**
     * 联系人qq
     * 
     */
    @TableField(value="contact_qq")
    private String contactQq;
    /**
     * 联系人邮箱
     * 
     */
    @TableField(value="contact_email")
    private String contactEmail;
    /**
     * 是否多证合一
     * 
     */
    @TableField(value="is_merged")
    private Boolean isMerged;
    /**
     * organization code license 即组织机构代码证，太长了，缩写了一下，此证的有效期的起始时间
     * 
     */
    @TableField(value="ocl_start")
    private Date oclStart;
    /**
     * 组织机构代码证有效期的结束时间
     * 
     */
    @TableField(value="ocl_end")
    private Date oclEnd;
    /**
     * 0|正在审核;1|审核不通过;2|审核通过
     * 
     */
    @TableField(value="status")
    private Integer status;
    /**
     * 审核原因  （拒绝时填入）
     * 
     */
    @TableField(value="reason")
    private String reason;
    /**
     * 	身份证号码
     * 
     */
    @TableField(value="identity")
    private String identity;
    /**
     * 企业地址
     * 
     */
    @TableField(value="license_street")
    private String licenseStreet;
    /**
     * 印章编号
     * 
     */
    @TableField(value="seal_no")
    private String sealNo;
    /**
     * 合同章编号
     * 
     */
    @TableField(value="contract_seal_no")
    private String contractSealNo;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
