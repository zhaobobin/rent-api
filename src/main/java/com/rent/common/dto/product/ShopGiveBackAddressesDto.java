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
 * 店铺归还地址表
 *
 * @author youruo
 * @Date 2020-06-17 10:47
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "店铺归还地址表")
public class ShopGiveBackAddressesDto implements Serializable {

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
     * 店铺id
     * 
     */
    @Schema(description = "店铺id")
    private String shopId;

    /**
     * 省id
     * 
     */
    @Schema(description = "省id")
    private Integer provinceId;

    @Schema(description = "省str")
    private String provinceStr;

    /**
     * 市id
     * 
     */
    @Schema(description = "市id")
    private Integer cityId;
    @Schema(description = "市str")
    private String cityStr;

    /**
     * 区id
     * 
     */
    @Schema(description = "区id")
    private Integer areaId;
    @Schema(description = "区str")
    private String areaStr;

    /**
     * 街道
     * 
     */
    @Schema(description = "街道")
    private String street;

    /**
     * 收件人手机号码
     * 
     */
    @Schema(description = "收件人手机号码")
    private String telephone;

    /**
     * 邮编
     * 
     */
    @Schema(description = "邮编")
    private String zcode;

    /**
     * 收件人姓名
     * 
     */
    @Schema(description = "收件人姓名")
    private String name;

    @Schema(description = "快递方式-包邮:FREE-到付:PAY-自提:SELF")
    private String freightType;



    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
