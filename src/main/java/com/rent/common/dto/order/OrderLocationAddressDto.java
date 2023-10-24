package com.rent.common.dto.order;

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
 * 订单当前位置定位表
 *
 * @author youruo
 * @Date 2021-01-14 15:15
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "订单当前位置定位表")
public class OrderLocationAddressDto implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * Id
     * 
     */
    @Schema(description = "Id")
    private Long id;

    /**
     * 创建时间
     * 
     */
    @Schema(description = "创建时间")
    private Date createTime;

    /**
     * 更新时间
     * 
     */
    @Schema(description = "更新时间")
    private Date updateTime;

    /**
     * 删除时间
     * 
     */
    @Schema(description = "删除时间")
    private Date deleteTime;

    /**
     * 订单号
     * 
     */
    @Schema(description = "订单号")
    private String orderId;

    /**
     * 经度
     * 
     */
    @Schema(description = "经度")
    private String longitude;

    /**
     * 纬度
     * 
     */
    @Schema(description = "纬度")
    private String latitude;

    /**
     * 精确度，单位米 (m)
     * 
     */
    @Schema(description = "精确度，单位米 (m)")
    private String accuracy;

    /**
     * 水平精确度，单位为米 (m)
     * 
     */
    @Schema(description = "水平精确度，单位为米 (m)")
    private String horizontalAccuracy;

    /**
     * 国家（type>0生效）
     * 
     */
    @Schema(description = "国家（type>0生效）")
    private String country;

    /**
     * 国家编号（type>0生效）
     * 
     */
    @Schema(description = "国家编号（type>0生效）")
    private String countryCode;

    /**
     * 省份（type>0生效）
     * 
     */
    @Schema(description = "省份（type>0生效）")
    private String province;

    /**
     * 城市（type>0生效）
     * 
     */
    @Schema(description = "城市（type>0生效）")
    private String city;

    /**
     * 城市级别的地区代码（type>0生效）
     * 
     */
    @Schema(description = "城市级别的地区代码（type>0生效）")
    private String cityAdcode;

    /**
     * 区县（type>0生效）
     * 
     */
    @Schema(description = "区县（type>0生效）")
    private String district;

    /**
     * 区县级别的地区代码（type>0生效）
     * 
     */
    @Schema(description = "区县级别的地区代码（type>0生效）")
    private String districtAdcode;

    /**
     * 需要街道级别逆地理的才会有的字段（type>0生效）
     * 
     */
    @Schema(description = "需要街道级别逆地理的才会有的字段（type>0生效）")
    private String streetNumber;

    /**
     * 兴趣点
     * 
     */
    @Schema(description = "兴趣点")
    private String pois;



    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
