package com.rent.common.dto.marketing;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 公告常见问题tab
 *
 * @author youruo
 * @Date 2021-08-16 15:55
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "公告常见问题tab")
public class OpeNoticeTabDto implements Serializable {

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
    private Integer indexSort;

    /**
     * 标题
     * 
     */
    @Schema(description = "标题",required = true)
    @Length(max = 40, message = "标题最长40位")
    private String name;

    /**
     * 备注
     * 
     */
    @Schema(description = "备注")
    private String remark;


    @Schema(description = "公告常见问题tab内容")
    private List<OpeNoticeItemDto> itemDtos;



    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
