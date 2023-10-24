package com.rent.common.dto.product;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;
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
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "店铺资质表")
public class ShopEnterpriseInfosDto implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * Id
     * 
     */
    @Schema(description = "Id")
    private Integer id;

    /**
     * 所属店铺id
     * 
     */
    @Schema(description = "所属店铺id")
    private String shopId;

    /**
     * CreateTime
     * 
     */
    @Schema(description = "CreateTime")
    private Date createTime;

    /**
     * UpdateTime
     * 
     */
    @Schema(description = "UpdateTime")
    private Date updateTime;

    /**
     * DeleteTime
     * 
     */
    @Schema(description = "DeleteTime")
    private Date deleteTime;

    /**
     * 企业名称
     * 
     */
    @Schema(description = "企业名称")
    private String name;

    /**
     * 企业注册资金
     * 
     */
    @Schema(description = "企业注册资金")
    private BigDecimal registrationCapital;

    /**
     * 营业执照号
     * 
     */
    @Schema(description = "营业执照号")
    private String businessLicenseNo;
    /**
     * 营业执照照片
     *
     */
    @Schema(description = "营业执照照片")
    private String licenseSrc;

    /**
     * 营业执照有效期起始时间
     * 
     */
    @Schema(description = "营业执照有效期起始时间")
    private Date licenseStart;

    /**
     * 营业执照有效期结束时间 如果为长期，则为2099-01-01
     * 
     */
    @Schema(description = "营业执照有效期结束时间 如果为长期，则为2099-01-01")
    private Date licenseEnd;

    /**
     * 营业执照所在省
     * 
     */
    @Schema(description = "营业执照所在省")
    private Integer licenseProvince;
    /**
     * 营业执照所在省
     *
     */
    @Schema(description = "营业执照所在省-名字")
    private String licenseProvinceStr;

    /**
     * 营业执照所在市
     * 
     */
    @Schema(description = "营业执照所在市")
    private Integer licenseCity;
    /**
     * 营业执照所在市
     *
     */
    @Schema(description = "营业执照所在市-名字")
    private String licenseCityStr;

    /**
     * 营业执照经营范围
     * 
     */
    @Schema(description = "营业执照经营范围")
    private String businessScope;

    /**
     * 法人代表姓名
     * 
     */
    @Schema(description = "法人代表姓名")
    private String realname;

    /**
     * 法人手机号码
     * 
     */
    @Schema(description = "法人手机号码")
    private String telephone;

    /**
     * 联系人姓名
     * 
     */
    @Schema(description = "联系人姓名")
    private String contactName;

    /**
     * 联系人手机号
     * 
     */
    @Schema(description = "联系人手机号")
    private String contactTelephone;

    /**
     * 联系人qq
     * 
     */
    @Schema(description = "联系人qq")
    private String contactQq;

    /**
     * 联系人邮箱
     * 
     */
    @Schema(description = "联系人邮箱")
    private String contactEmail;

    /**
     * 是否多证合一
     * 
     */
    @Schema(description = "是否多证合一")
    private Boolean isMerged;

    /**
     * organization code license 即组织机构代码证，太长了，缩写了一下，此证的有效期的起始时间
     * 
     */
    @Schema(description = "organization code license 即组织机构代码证，太长了，缩写了一下，此证的有效期的起始时间")
    private Date oclStart;

    /**
     * 组织机构代码证有效期的结束时间
     * 
     */
    @Schema(description = "组织机构代码证有效期的结束时间")
    private Date oclEnd;

    /**
     * 0|正在审核;1|审核不通过;2|审核通过
     * 
     */
    @Schema(description = "0|正在审核;1|审核不通过;2|审核通过")
    private Integer status;

    /**
     * 审核原因  （拒绝时填入）
     * 
     */
    @Schema(description = "审核原因（拒绝时填入）")
    private String reason;

    /**
     * 	身份证号码
     * 
     */
    @Schema(description = "身份证号码")
    private String identity;

    /**
     * 企业地址
     * 
     */
    @Schema(description = "企业地址")
    private String licenseStreet;

    /**
     * 印章编号
     * 
     */
    @Schema(description = "印章编号")
    private String sealNo;

    /**
     * 合同章编号
     * 
     */
    @Schema(description = "合同章编号")
    private String contractSealNo;
    /**
     * 商店名称
     *
     */
    @Schema(description = "商店名称")
    private String shopName;


    /**
     * 身份证照片正面
     *
     */
    @Schema(description = "法人身份证照片正面")
    private String idcardDirect;
    /**
     * 身份证照片背面
     *
     */
    @Schema(description = "法人身份证照片背面")
    private String idcardBack;







    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
