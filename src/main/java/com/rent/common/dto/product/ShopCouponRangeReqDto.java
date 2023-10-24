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
 * 店铺优惠券范围
 *
 * @author youruo
 * @Date 2020-06-17 10:43
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "店铺优惠券范围")
public class ShopCouponRangeReqDto extends Page implements Serializable {

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
     * 优惠券id
     * 
     */
    @Schema(description = "优惠券id")
    private String couponId;

    /**
     * 可用的商品id
     * 
     */
    @Schema(description = "可用的商品id")
    private String itemId;



    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
