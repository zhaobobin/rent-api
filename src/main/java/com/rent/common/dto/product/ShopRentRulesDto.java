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
 * 店铺规则表
 *
 * @author youruo
 * @Date 2020-06-17 10:48
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "店铺规则表")
public class ShopRentRulesDto implements Serializable {

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
     * 租用规则模板名称
     * 
     */
    @Schema(description = "租用规则模板名称")
    private String name;

    /**
     * 租用规则内容
     * 
     */
    @Schema(description = "租用规则内容")
    private String content;

    /**
     * 租用规则所属店铺id
     * 
     */
    @Schema(description = "租用规则所属店铺id")
    private String shopId;



    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
