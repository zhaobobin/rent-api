package com.rent.common.dto.product;

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
 * 店铺品牌表
 *
 * @author youruo
 * @Date 2020-06-17 10:37
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "店铺品牌表")
public class ShopBrandsDto implements Serializable {

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
     * 所属平台品牌的id
     * 所属平台品牌的id, 新品牌无此值
     */
    @Schema(description = "所属平台品牌的id")
    private Integer brandId;

    /**
     * 品牌主营类目
     * 品牌主营类目, 为店铺主营类目中的id
     */
    @Schema(description = "品牌主营类目")
    private Integer mainCategoryId;

    /**
     * 品牌授权类型id，根据不同的店铺类型，区分不同的授权类型
     * 
     */
    @Schema(description = "品牌授权类型id，根据不同的店铺类型，区分不同的授权类型")
    private Integer brandAuthType;

    /**
     * 0正在审核 1审核不通过 2审核通过
     * 
     */
    @Schema(description = "0正在审核 1审核不通过 2审核通过")
    private Boolean status;

    /**
     * 所属店铺id
     * 
     */
    @Schema(description = "所属店铺id")
    private String shopId;

    /**
     * 提交资料的店铺管理人员id
     * 
     */
    @Schema(description = "提交资料的店铺管理人员id")
    private String shopAdminId;

    /**
     * 店铺类型id
     * 
     */
    @Schema(description = "店铺类型id")
    private Integer shopTypeId;

    /**
     * 是否属于已注册品牌
     * 
     */
    @Schema(description = "是否属于已注册品牌")
    private Boolean isR;

    /**
     * 是否属于正在注册品牌
     * 
     */
    @Schema(description = "是否属于正在注册品牌")
    private Boolean isTm;

    /**
     * 品牌名称
     * 
     */
    @Schema(description = "品牌名称")
    private String name;

    /**
     * 品牌描述
     * 
     */
    @Schema(description = "品牌描述")
    private String description;

    /**
     * 品牌正方形Logo
     * 
     */
    @Schema(description = "品牌正方形Logo")
    private String squareLogo;

    /**
     * 品牌长方形Logo
     * 
     */
    @Schema(description = "品牌长方形Logo")
    private String rectangleLogo;



    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
