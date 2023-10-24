
package com.rent.model.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;


/**
 * 区域表
 *
 * @author zhao
 * @Date 2020-07-06 15:29
 */
@Data
@Accessors(chain = true)
@TableName("ct_district")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class District {


    /**
     * Id
     * 
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    /**
     * DistrictId
     * 
     */
    @TableField(value="district_id")
    private String districtId;
    /**
     * 父id
     * 
     */
    @TableField(value="parent_id")
    private String parentId;
    /**
     * 区域名称
     * 
     */
    @TableField(value="name")
    private String name;
    /**
     * 合并名称
     * 
     */
    @TableField(value="merger_name")
    private String mergerName;
    /**
     * 缩写名
     * 
     */
    @TableField(value="short_name")
    private String shortName;
    /**
     * 合并短名
     * 
     */
    @TableField(value="merger_short_name")
    private String mergerShortName;
    /**
     * 类型
     * 
     */
    @TableField(value="level_type")
    private String levelType;
    /**
     * CityCode
     * 
     */
    @TableField(value="city_code")
    private String cityCode;
    /**
     * ZipCode
     * 
     */
    @TableField(value="zip_code")
    private String zipCode;
    /**
     * Pinyin
     * 
     */
    @TableField(value="pinyin")
    private String pinyin;
    /**
     * Jianpin
     * 
     */
    @TableField(value="jianpin")
    private String jianpin;
    /**
     * FirstChar
     * 
     */
    @TableField(value="first_char")
    private String firstChar;
    /**
     * Lng
     * 
     */
    @TableField(value="lng")
    private String lng;
    /**
     * Lat
     * 
     */
    @TableField(value="lat")
    private String lat;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
