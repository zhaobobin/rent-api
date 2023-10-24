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
 * 商品快照表
 *
 * @author youruo
 * @Date 2020-06-16 15:30
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "商品快照表")
public class ProductSnapshotsDto implements Serializable {

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
     * 商品id
     * 
     */
    @Schema(description = "商品id")
    private String itemId;

    /**
     * 版本号 毫秒时间戳
     * 
     */
    @Schema(description = "版本号 毫秒时间戳")
    private Long version;

    /**
     * 快照数据
     * 
     */
    @Schema(description = "快照数据")
    private Object data;

    /**
     * 所属店铺id
     * 
     */
    @Schema(description = "所属店铺id")
    private String shopId;

    /**
     * 0 待审核 1审核通过 2审核拒绝
     * 
     */
    @Schema(description = "0 待审核 1审核通过 2审核拒绝")
    private Integer status;

    /**
     * 商品名字
     * 
     */
    @Schema(description = "商品名字")
    private String productName;



    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
