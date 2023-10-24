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
 * 订单地址
 *
 * @author xiaoyao
 * @Date 2020-06-16 10:09
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "订单地址")
public class OrderAddressDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Id
     */
    @Schema(description = "Id")
    private Long id;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private Date createTime;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    private Date updateTime;

    /**
     * 删除时间
     */
    @Schema(description = "删除时间")
    private Date deleteTime;

    /**
     * 省
     */
    @Schema(description = "省")
    private Integer province;

    /**
     * 市
     */
    @Schema(description = "市")
    private Integer city;

    /**
     * 所属用户id
     */
    @Schema(description = "所属用户id")
    private String uid;

    /**
     * 区
     */
    @Schema(description = "区")
    private Integer area;

    /**
     * 详细地址
     */
    @Schema(description = "详细地址")
    private String street;

    /**
     * 邮编
     */
    @Schema(description = "邮编")
    private String zcode;

    /**
     * 手机号码
     */
    @Schema(description = "手机号码")
    private String telephone;

    /**
     * 收货人姓名
     */
    @Schema(description = "收货人姓名")
    private String realname;

    /**
     * OrderId
     */
    @Schema(description = "OrderId")
    private String orderId;

    /**
     * 省
     */
    @Schema(description = "省Str")
    private String provinceStr;

    /**
     * 市
     */
    @Schema(description = "市Str")
    private String cityStr;

    /**
     * 区
     */
    @Schema(description = "区Str")
    private String areaStr;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
