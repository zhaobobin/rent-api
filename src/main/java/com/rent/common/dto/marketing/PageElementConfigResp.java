package com.rent.common.dto.marketing;

import com.rent.common.enums.marketing.PageElementConfigTypeEnum;
import com.rent.common.enums.marketing.PageElementExtCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;


/**
 * @author zhaowenchao
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageElementConfigResp implements Serializable {

    private Long id;

    /**
     * 素材中心素材项ID
     */
    private Long materialCenterItemId;

    /**
     * 素材中心素材项ID
     */
    private String fileUrl;

    /**
     * 素材详细信息
     */
    private MaterialCenterItemDto materialCenterItem;

    /**
     * 类型
     */
    private PageElementConfigTypeEnum type;

    /**
     * 跳转链接
     */
    private String link;

    /**
     * 排序
     */
    private Short sortNum;

    /**
     * 描述
     */
    private String describeInfo;


    /**
     * 配置编码
     */
    private PageElementExtCodeEnum extCode;

    /**
     * 渠道编号
     */
    private String channelId;


    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
