package com.rent.common.dto.product;


import com.rent.common.dto.Page;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.util.Date;

/**
 * 店铺表
 *
 * @author youruo
 * @Date 2020-06-17 10:33
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "店铺表")
public class ShopReqDto extends Page implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * Id
     * 
     */
    @Schema(description = "Id")
    private Integer id;

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
     * 店铺名称
     * 
     */
    @Schema(description = "店铺名称")
    private String name;

    /**
     * 店铺logo链接 80x80
     * 
     */
    @Schema(description = "店铺logo链接 80x80")
    private String logo;

    /**
     * 店铺背景图链接
     * 
     */
    @Schema(description = "店铺背景图链接")
    private String background;

    /**
     * 店铺介绍
     * 
     */
    @Schema(description = "店铺介绍")
    private String description;

    /**
     * 店铺的经营类型
     * 
     */
    @Schema(description = "店铺的经营类型")
    private Integer shopTypeId;

    /**
     * 店铺主营类目id
     * 
     */
    @Schema(description = "店铺主营类目id")
    private Integer mainCategoryId;

    /**
     * 店铺状态 0待提交企业资质 1待填写店铺信息 2待提交品牌信息 3正在审核 4审核不通过 5审核通过，准备开店 6开店成功
     * 
     */
    @Schema(description = "店铺状态 0待提交企业资质 1待填写店铺信息 2待提交品牌信息 3正在审核 4审核不通过 5审核通过，准备开店 6开店成功")
    private Integer status;

    /**
     * 店铺审核通过时间
     * 
     */
    @Schema(description = "店铺审核通过时间")
    private Date approvalTime;

    /**
     * 审核原因  拒绝时录入
     * 
     */
    @Schema(description = "审核原因  拒绝时录入")
    private String reason;

    /**
     * 店铺注册时间，用于shop_id加密
     * 
     */
    @Schema(description = "店铺注册时间，用于shop_id加密")
    private String registTime;

    /**
     * 由平台自动生成的唯一标识
     * 
     */
    @Schema(description = "由平台自动生成的唯一标识")
    private String shopId;

    /**
     * 店铺是否被冻结
     * 
     */
    @Schema(description = "店铺是否被冻结")
    private Integer isLocked;

    /**
     * 店铺冻结时间
     * 
     */
    @Schema(description = "店铺冻结时间")
    private Date lockedTime;

    /**
     * 店铺是否被封
     * 
     */
    @Schema(description = "店铺是否被封")
    private Integer isDisabled;

    /**
     * 商家客服电话
     * 
     */
    @Schema(description = "商家客服电话")
    private String serviceTel;

    /**
     * 商家接收短信手机号码
     * 
     */
    @Schema(description = "商家接收短信手机号码")
    private String recvMsgTel;

    /**
     * 是否优质商家
     * 
     */
    @Schema(description = "是否优质商家")
    private Boolean isHighQuality;

    /**
     * 是否签约 0否 1是
     * 
     */
    @Schema(description = "是否签约 0否 1是")
    private Integer isSigning;

    /**
     * 是否需要电审
     *
     */
    @Schema(description = "是否需要电审")
    private Integer isPhoneExamination;

    /**
     * 是否开启自动分配审核
     *
     */
    @Schema(description = "是否需要电审")
    private Integer isAutoAuditUser;


    /**
     * 店铺联系邮箱
     *
     */
    @Schema(description = "店铺联系邮箱")
    private String userEmail;

    /**
     * 店铺联系人姓名
     *
     */
    @Schema(description = "店铺联系人姓名")
    private String userName;
    /**
     * 店铺联系人电话
     *
     */
    @Schema(description = "店铺联系人电话")
    private String userTel;

    /**
     * 支付宝账号
     *
     */
    @Schema(description = "支付宝账号")
    private String zfbCode;
    /**
     * 支付宝姓名
     *
     */
    @Schema(description = "支付宝姓名")
    private String zfbName;
    /**
     * 店铺合同链接
     *
     */
    @Schema(description = "店铺合同链接")
    private String shopContractLink;

    @Schema(description = "店铺地址")
    private String shopAddress;

    @Schema(description = "风控报告是否收费 ")
    private Boolean reportCharge;


    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
