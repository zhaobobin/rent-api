package com.rent.model.components;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rent.common.enums.components.EnumAntChainLogStatus;
import com.rent.common.enums.components.EnumInsureOperate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.util.Date;

/**
 * @author zhaowenchao
 */
@Data
@Accessors(chain = true)
@TableName("ct_ant_chain_insurance_log")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class AntChainInsuranceLog {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "order_id")
    private String orderId;

    @TableField(value = "operate")
    private EnumInsureOperate operate;

    @TableField(value = "req_params")
    private String reqParams;

    @TableField(value = "resp")
    private String resp;

    @TableField(value = "status")
    private EnumAntChainLogStatus status;

    @TableField(value = "create_time")
    private Date createTime;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
