package com.rent.common.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;

/**
 * 区域表
 *
 * @author zhao
 * @Date 2020-07-06 15:29
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "区域表")
public class DistrictDto implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * Id
     * 
     */
    @Schema(description = "Id")
    private Integer id;

    /**
     * DistrictId
     * 
     */
    @Schema(description = "DistrictId")
    private String districtId;

    /**
     * 父id
     * 
     */
    @Schema(description = "父id")
    private String parentId;

    /**
     * 区域名称
     * 
     */
    @Schema(description = "区域名称")
    private String name;

    /**
     * 合并名称
     * 
     */
    @Schema(description = "合并名称")
    private String mergerName;

    /**
     * 缩写名
     * 
     */
    @Schema(description = "缩写名")
    private String shortName;

    /**
     * 合并短名
     * 
     */
    @Schema(description = "合并短名")
    private String mergerShortName;

    /**
     * 类型
     * 
     */
    @Schema(description = "类型")
    private String levelType;

    /**
     * CityCode
     * 
     */
    @Schema(description = "CityCode")
    private String cityCode;

    /**
     * ZipCode
     * 
     */
    @Schema(description = "ZipCode")
    private String zipCode;

    /**
     * Pinyin
     * 
     */
    @Schema(description = "Pinyin")
    private String pinyin;

    /**
     * Jianpin
     * 
     */
    @Schema(description = "Jianpin")
    private String jianpin;

    /**
     * FirstChar
     * 
     */
    @Schema(description = "FirstChar")
    private String firstChar;

    /**
     * Lng
     * 
     */
    @Schema(description = "Lng")
    private String lng;

    /**
     * Lat
     * 
     */
    @Schema(description = "Lat")
    private String lat;



    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
