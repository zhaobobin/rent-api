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
 * 蚁盾分日志
 * @author zhaowenchao
 */
@Data
@Accessors(chain = true)
@TableName("ct_ant_chain_shield_log")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class AntChainShieldLog {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "order_id")
    private String orderId;

    @TableField(value = "uid")
    private String uid;

    @TableField(value = "id_card")
    private String idCard;

    @TableField(value = "request")
    private String request;

    @TableField(value = "response")
    private String response;

    @TableField(value = "status_code")
    private String statusCode;

    @TableField(value = "score")
    private String score;

    @TableField(value = "create_time")
    private Date createTime;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
