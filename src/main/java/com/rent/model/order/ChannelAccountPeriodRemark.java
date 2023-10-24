package com.rent.model.order;

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
 * 账期备注表
 * @author xiaotong
 */
@Data
@Accessors(chain = true)
@TableName("ct_channel_account_period_remark")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChannelAccountPeriodRemark {


    private static final long serialVersionUID = 1L;
    /**
     * Id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 账期ID
     */
    @TableField(value = "account_period_id")
    private Long accountPeriodId;
    /**
     * 备注人姓名
     */
    @TableField(value = "backstage_user_name")
    private String backstageUserName;
    /**
     * 备注内容
     */
    @TableField(value = "content")
    private String content;
    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;


    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
