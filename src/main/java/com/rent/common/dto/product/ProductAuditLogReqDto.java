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
 * 商品审核日志表
 *
 * @author youruo
 * @Date 2020-06-29 18:32
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "商品审核日志表")
public class ProductAuditLogReqDto extends Page implements Serializable {

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
     * 商品审核状态 0为正在审核中 1为审核不通过 2为审核通过
     * 
     */
    @Schema(description = "商品审核状态 0为正在审核中 1为审核不通过 2为审核通过")
    private Integer auditStatus;

    /**
     * 反馈
     * 
     */
    @Schema(description = "反馈")
    private String feedBack;

    /**
     * 操作人
     * 
     */
    @Schema(description = "操作人")
    private String operator;

    /**
     * 商品ID
     *
     */
    @Schema(description = "商品ID")
    private String itemId;



    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
