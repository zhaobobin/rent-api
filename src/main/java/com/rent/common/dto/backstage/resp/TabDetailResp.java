package com.rent.common.dto.backstage.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;


/**
 * @author zhaowenchao
 */
@Data
@Schema(description = "运营后台查看tab详情响应结果")
public class TabDetailResp implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "tabId")
    private Long id;

    @Schema(description = "排序值")
    private Integer indexSort;

    @Schema(description = "tab名称")
    private String name;

    @Schema(description = "跳转地址")
    private String jumpUrl;


    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
