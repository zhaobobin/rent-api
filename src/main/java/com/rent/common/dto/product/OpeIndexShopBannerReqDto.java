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
 * 商家详情轮播配置
 *
 * @author youruo
 * @Date 2020-07-23 17:37
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "商家详情轮播配置")
public class OpeIndexShopBannerReqDto extends Page implements Serializable {

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
     * OnlineTime
     * 
     */
    @Schema(description = "OnlineTime")
    private Date onlineTime;

    /**
     * ShopId
     * 
     */
    @Schema(description = "ShopId")
    private String shopId;

    /**
     * Name
     * 
     */
    @Schema(description = "Name")
    private String name;

    /**
     * ImgSrc
     * 
     */
    @Schema(description = "ImgSrc")
    private String imgSrc;

    /**
     * JumpUrl
     * 
     */
    @Schema(description = "JumpUrl")
    private String jumpUrl;

    /**
     * 0 失效 1 有效
     * 
     */
    @Schema(description = "0 失效 1 有效")
    private Integer status;

    /**
     * IndexSort
     * 
     */
    @Schema(description = "IndexSort")
    private Integer indexSort;



    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
