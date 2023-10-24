
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
 * 店铺位置定位表
 *
 * @author youruo
 * @Date 2021-01-14 15:15
 */
@Data
@Accessors(chain = true)
@TableName("ct_marketing_channel_store_address")
public class ChannelStoreAddress {

    private static final long serialVersionUID = 1L;


    /**
     * Id
     *
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    /**
     * 创建时间
     *
     */
    @TableField(value="create_time")
    private Date createTime;
    /**
     * 更新时间
     *
     */
    @TableField(value="update_time")
    private Date updateTime;
    /**
     * 删除时间
     *
     */
    @TableField(value="delete_time")
    private Date deleteTime;
    /**
     * 店铺链接编号(店铺编号)
     *
     */
    @TableField(value="marketing_id")
    private String marketingId;
    /**
     * 经度
     *
     */
    @TableField(value="longitude")
    private String longitude;
    /**
     * 纬度
     *
     */
    @TableField(value="latitude")
    private String latitude;
    /**
     * 精确度，单位米 (m)
     *
     */
    @TableField(value="accuracy")
    private String accuracy;
    /**
     * 水平精确度，单位为米 (m)
     *
     */
    @TableField(value="horizontal_accuracy")
    private String horizontalAccuracy;
    /**
     * 国家（type>0生效）
     *
     */
    @TableField(value="country")
    private String country;
    /**
     * 国家编号（type>0生效）
     *
     */
    @TableField(value="country_code")
    private String countryCode;
    /**
     * 省份（type>0生效）
     *
     */
    @TableField(value="province")
    private String province;
    /**
     * 城市（type>0生效）
     *
     */
    @TableField(value="city")
    private String city;
    /**
     * 城市级别的地区代码（type>0生效）
     *
     */
    @TableField(value="city_adcode")
    private String cityAdcode;
    /**
     * 区县（type>0生效）
     *
     */
    @TableField(value="district")
    private String district;
    /**
     * 区县级别的地区代码（type>0生效）
     *
     */
    @TableField(value="district_adcode")
    private String districtAdcode;
    /**
     * 需要街道级别逆地理的才会有的字段（type>0生效）
     *
     */
    @TableField(value="street_number")
    private String streetNumber;
    /**
     * 兴趣点
     *
     */
    @TableField(value="pois")
    private String pois;

    @TableField(value="ip_addr")
    private String ipAddr;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
