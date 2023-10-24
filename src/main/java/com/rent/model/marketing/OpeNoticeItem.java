
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
 * 公告常见问题tab内容
 *
 * @author youruo
 * @Date 2021-08-16 15:55
 */
@Data
@Accessors(chain = true)
@TableName("ct_ope_notice_item")
public class OpeNoticeItem {

    private static final long serialVersionUID = 1L;


    /**
     * 主键id
     * 
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    /**
     * 创建时间
     * 
     */
    @TableField(value="create_time")
    private Date createTime;
    /**
     * 公告tabId
     * 
     */
    @TableField(value="tab_id")
    private Long tabId;
    /**
     * DeleteTime
     * 
     */
    @TableField(value="delete_time")
    private Date deleteTime;
    /**
     * UpdateTime
     * 
     */
    @TableField(value="update_time")
    private Date updateTime;
    /**
     * 序列
     * 
     */
    @TableField(value="index_sort")
    private Integer indexSort;
    /**
     * 标题
     * 
     */
    @TableField(value="name")
    private String name;
    /**
     * 备注
     * 
     */
    @TableField(value="remark")
    private String remark;
    /**
     * 详情
     * 
     */
    @TableField(value="detail")
    private String detail;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
