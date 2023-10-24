package com.rent.common.dto.marketing;

import com.rent.common.enums.marketing.PageElementConfigTypeEnum;
import com.rent.common.enums.marketing.PageElementExtCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @program: hzsx-rent-parent
 * @description:
 * @author: yr
 * @create: 2020-08-12 15:51
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PageElementConfigRequest {

    private Long id;

    /**
     * 素材中心素材项ID
     */
    private Long materialCenterItemId;

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
}
