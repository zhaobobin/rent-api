package com.rent.common.dto.components.request;

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
 * 用户报告
 *
 * @author xiaoyao
 * @Date 2020-06-24 16:22
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "用户报告")
public class SxReportRecordReqDto extends Page implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @Schema(description = "主键")
    private Long id;

    /**
     * 用户或商家生成的唯一主键
     */
    @Schema(description = "用户或商家生成的唯一主键")
    private String uid;

    /**
     * 用户的姓名
     */
    @Schema(description = "用户的姓名")
    private String userName;

    /**
     * 身份证号
     */
    @Schema(description = "身份证号")
    private String idCard;

    /**
     * 分数
     */
    @Schema(description = "分数")
    private Integer multipleScore;

    /**
     * 查询结果
     */
    @Schema(description = "查询结果")
    private String reportResult;

    /**
     * 手机号
     */
    @Schema(description = "手机号")
    private String phone;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private Date createTime;

    /**
     * 1 首新查询正常 8 连接超时  9 首新查询异常
     */
    @Schema(description = "1 首新查询正常 8 连接超时  9 首新查询异常")
    private Integer status;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
