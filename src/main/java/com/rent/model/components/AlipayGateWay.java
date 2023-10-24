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
 * 支付宝网关数据日志
 * @author zhaowenchao
 */
@Data
@Accessors(chain = true)
@TableName("ct_alipay_gate_way")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class AlipayGateWay {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "data_utc_timestamp")
    private String dataUtcTimestamp;

    @TableField(value = "app_id")
    private String appId;

    @TableField(value = "notify_id")
    private String notifyId;

    @TableField(value = "msg_method")
    private String msgMethod;

    @TableField(value = "data_version")
    private String dataVersion;

    @TableField(value = "biz_content")
    private String bizContent;

    @TableField(value = "notify_type")
    private String notifyType;

    @TableField(value = "create_time")
    private Date createTime;


    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
