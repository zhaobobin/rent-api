package com.rent.common.dto.product;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 店铺分账规则
 *
 * @author youruo
 * @Date 2020-06-17 10:49
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "店铺分账规则")
public class ShopSplitBillRuleDto implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * Id
     * 
     */
    @Schema(description = "Id")
    private Long id;

    /**
     * 分账账户ID
     * 
     */
    @Schema(description = "分账账户ID")
    private Long accountId;

    /**
     * 分账类型 买断|租金
     * 
     */
    @Schema(description = "分账类型 买断|租金")
    private String type;

    /**
     * 延迟天数
     * 
     */
    @Schema(description = "延迟天数")
    private Integer delayNum;

    /**
     * 延迟天数类型
     * 
     */
    @Schema(description = "延迟天数类型")
    private String delayType;

    /**
     * 分账比例，小于等于1
     * 
     */
    @Schema(description = "分账比例，小于等于1")
    private BigDecimal scale;

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



    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
