
package com.rent.model.marketing;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.util.Date;

/**
 * 布云短信模板
 *
 * @author youruo
 * @Date 2020-11-06 14:30
 */
@Data
@Accessors(chain = true)
@TableName("ct_xunda_sms_template")
public class XunDaSmsTemplate {

    private static final long serialVersionUID = 1L;


    /**
     * Id
     * 
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    /**
     * 短信模板Id
     * 
     */
    @TableField(value="tpl_id")
    private String tplId;
    /**
     * 签名
     * 
     */
    @TableField(value="autograph")
    private String autograph;
    /**
     * 短信内容
     * 
     */
    @TableField(value="content")
    private String content;
    /**
     * 使用场景
     * 
     */
    @TableField(value="use_case")
    private String useCase;
    /**
     * 是否生效 0:有效
     * 是否生效 0:有效,1:无效
     */
    @TableField(value="status")
    private Long status;
    /**
     * CreateTime
     * 
     */
    @TableField(value="create_time")
    private Date createTime;
    /**
     * UpdateTime
     * 
     */
    @TableField(value="update_time")
    private Date updateTime;
    /**
     * DeleteTime
     * 
     */
    @TableField(value="delete_time")
    private Date deleteTime;

    /**
     * 短信类型
     *
     */
    @TableField(value="sms_type")
    private String smsType;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
