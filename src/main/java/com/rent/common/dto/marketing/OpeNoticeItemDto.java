package com.rent.common.dto.marketing;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 公告常见问题tab内容
 *
 * @author youruo
 * @Date 2021-08-16 15:55
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "公告常见问题tab内容")
public class OpeNoticeItemDto implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 主键id
     * 
     */
    @Schema(description = "主键id")
    private Long id;

    /**
     * 创建时间
     * 
     */
    @Schema(description = "创建时间")
    private Date createTime;

    /**
     * 公告tabId
     * 
     */
    @Schema(description = "公告tabId")
    @NotNull(message = "公告tabId不能为空")
    private Long tabId;

    /**
     * DeleteTime
     * 
     */
    @Schema(description = "DeleteTime")
    private Date deleteTime;

    /**
     * UpdateTime
     * 
     */
    @Schema(description = "UpdateTime")
    private Date updateTime;

    /**
     * 序列
     * 
     */
    @Schema(description = "序列",required = true)
    @NotNull(message = "序列不能为空")
    private Integer indexSort;

    /**
     * 标题
     * 
     */
    @Schema(description = "标题",required = true)
    @NotNull(message = "标题不能为空")
    @Length(max = 40, message = "标题最长40位")
    private String name;

    /**
     * 备注
     * 
     */
    @Schema(description = "备注")
    private String remark;

    /**
     * 详情
     * 
     */
    @Schema(description = "详情")
    @NotNull(message = "详情不能为空")
    private String detail;



    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
