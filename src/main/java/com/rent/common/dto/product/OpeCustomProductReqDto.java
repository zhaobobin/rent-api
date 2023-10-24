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
 * 自定义tab产品挂载表
 *
 * @author youruo
 * @Date 2020-06-16 10:00
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "自定义tab产品挂载表")
public class OpeCustomProductReqDto extends Page implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 主键
     * 
     */
    @Schema(description = "主键")
    private Long id;

    /**
     * 创建时间
     * 
     */
    @Schema(description = "创建时间")
    private Date createTime;

    /**
     * 删除时间
     * 
     */
    @Schema(description = "删除时间")
    private Date deleteTime;

    /**
     * 跟新时间
     * 
     */
    @Schema(description = "跟新时间")
    private Date updateTime;

    /**
     * 序列
     * 
     */
    @Schema(description = "序列")
    private Integer indexSort;

    /**
     * 产品名称
     * 
     */
    @Schema(description = "产品名称")
    private String name;

    /**
     * 父tab的id
     * 
     */
    @Schema(description = "父tab的id")
    private Integer tabId;

    /**
     * Image
     * 
     */
    @Schema(description = "Image")
    private String image;

    /**
     * 价格优惠
     * 
     */
    @Schema(description = "价格优惠")
    private String price;

    /**
     * 价格原价（划横线）
     * 
     */
    @Schema(description = "价格原价（划横线）")
    private String sale;

    /**
     * 新旧程度
     * 
     */
    @Schema(description = "新旧程度")
    private String oldNewDegree;

    /**
     * 服务标签
     * 
     */
    @Schema(description = "服务标签")
    private String serviceMarks;

    @Schema(description = "商品标签")
    private String productMarks;



    /**
     * 产品唯一id
     * 
     */
    @Schema(description = "产品唯一id")
    private String itemId;

    /**
     * LinkUrl
     * 
     */
    @Schema(description = "LinkUrl")
    private String linkUrl;

    /**
     * 店铺名称 
     * 
     */
    @Schema(description = "店铺名称 ")
    private String shopName;

    /**
     * 起租时间
     * 
     */
    @Schema(description = "起租时间")
    private String minDays;

    /**
     * 销量
     *
     */
    @Schema(description = "销量")
    private Integer salesVolume;

    @Schema(description = " 0失效  1有效")
    private Integer status;


    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
