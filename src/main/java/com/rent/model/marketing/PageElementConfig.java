package com.rent.model.marketing;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rent.common.enums.marketing.PageElementConfigTypeEnum;
import com.rent.common.enums.marketing.PageElementExtCodeEnum;
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
@TableName("ct_page_element_config")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class PageElementConfig {

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    /**
     * 素材中心素材项ID
     */
    @TableField(value="material_center_item_id")
    private Long materialCenterItemId;

    /**
     * 类型
     */
    @TableField(value="type")
    private PageElementConfigTypeEnum type;

    /**
     * 跳转链接
     */
    @TableField(value="link")
    private String link;

    /**
     * 排序
     */
    @TableField(value="sort_num")
    private Short sortNum;

    /**
     * 描述
     */
    @TableField(value="describe_info")
    private String describeInfo;


    /**
     * 描述
     */
    @TableField(value="ext_code")
    private PageElementExtCodeEnum extCode;
    /**
     * 渠道编号
     */
    @TableField(value="channel_id")
    private String channelId;

    @TableField(value="create_time")
    private Date createTime;

    @TableField(value="delete_time")
    private Date deleteTime;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
