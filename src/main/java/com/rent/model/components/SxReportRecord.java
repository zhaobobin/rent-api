package com.rent.model.components;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.util.Date;

/**
 * 用户报告
 *
 * @author xiaoyao
 * @Date 2020-06-24 16:22
 */
@Data
@Accessors(chain = true)
@TableName("ct_sx_report_record")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class SxReportRecord {

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 用户或商家生成的唯一主键
     */
    @TableField(value = "uid")
    private String uid;
    /**
     * 用户的姓名
     */
    @TableField(value = "user_name")
    private String userName;
    /**
     * 身份证号
     */
    @TableField(value = "id_card")
    private String idCard;
    /**
     * 分数
     */
    @TableField(value = "multiple_score")
    private Integer multipleScore;
    /**
     * 查询结果
     */
    @TableField(value = "report_result")
    private String reportResult;
    /**
     * 手机号
     */
    @TableField(value = "phone")
    private String phone;
    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;
    /**
     * 1 首新查询正常 8 连接超时  9 首新查询异常
     */
    @TableField(value = "status")
    private Integer status;
    /** 报告类型 01：天狼星 02：新版风控报告 */
    @TableField(value = "report_type")
    private String reportType;
    /** 订单编号 */
    @TableField(value = "order_id")
    private String orderId;
    /**
     * 1 首新查询正常 8 连接超时  9 首新查询异常
     */
    @TableField(value = "forbidden_status")
    private Integer forbiddenStatus;
    /** 禁言查询结果 */
    @TableField(value = "forbidden_result")
    private String forbiddenResult;
    /** 报告查询时间 */
    @TableField(value = "query_time")
    private Date queryTime;
    /** nsf等级 */
    @TableField(value = "nsf_level")
    private String nsfLevel;
    /** 营销反作弊等级 */
    @TableField(value = "anti_cheating_level")
    private String antiCheatingLevel;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
