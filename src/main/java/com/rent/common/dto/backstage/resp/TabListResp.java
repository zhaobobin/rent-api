package com.rent.common.dto.backstage.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;


/**
 * @author zhaowenchao
 */
@Data
@Schema(description = "运营后台查看tab列表响应结果")
public class TabListResp implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "tabId")
    private Long id;

    @Schema(description = "tab名称")
    private String name;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
