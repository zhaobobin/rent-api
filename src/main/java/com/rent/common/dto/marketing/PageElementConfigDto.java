package com.rent.common.dto.marketing;

import com.rent.common.enums.marketing.PageElementExtCodeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;


/**
 * 页面配置信息
 * @author zhaowenchao
 */
@Data
@Schema(description = "页面配置元素信息")
public class PageElementConfigDto implements Serializable {

    private static final long serialVersionUID = 4421966393847552386L;

    @Schema(description = "素材文件地址")
    private String fileUrl;

    @Schema(description = "跳转链接")
    private String link;

    @Schema(description = "描述")
    private String describeInfo;

    @Schema(description = "配置编码")
    private PageElementExtCodeEnum extCode;

    @Schema(description = "展示数量")
    private Integer showNum=0;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
