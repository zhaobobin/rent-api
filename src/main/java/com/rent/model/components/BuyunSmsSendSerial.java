
package com.rent.model.components;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.util.Date;

/**
 * 布云短信发送流水记录
 *
 * @author youruo
 * @Date 2021-01-18 10:41
 */
@Data
@Accessors(chain = true)
@TableName("ct_buyun_sms_send_serial")
public class BuyunSmsSendSerial {

    private static final long serialVersionUID = 1L;


    /**
     * Id
     * 
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    /**
     * 短信编号
     * 
     */
    @TableField(value="sms_code")
    private String smsCode;
    /**
     * 短信参数
     * 
     */
    @TableField(value="params")
    private String params;
    /**
     * 接收人手机号
     * 
     */
    @TableField(value="mobile")
    private String mobile;
    /**
     * 发送结果
     * 
     */
    @TableField(value="result")
    private String result;
    /**
     * 发送结果返回码
     * 
     */
    @TableField(value="status")
    private String status;
    /**
     * 当前账户余额，单位厘
     * 
     */
    @TableField(value="balance")
    private String balance;
    /**
     * 渠道编号
     * 
     */
    @TableField(value="channel_id")
    private String channelId;
    /**
     * 创建时间
     * 
     */
    @TableField(value="create_time")
    private Date createTime;

    /**
     * 短信行业类型
     *
     */
    @TableField(value="sms_type")
    private String smsType;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
