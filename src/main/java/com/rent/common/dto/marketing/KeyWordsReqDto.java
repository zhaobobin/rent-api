package com.rent.common.dto.marketing;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @program: zwzrent-components
 * @description: 模板消息关键词
 * @author: yr
 * @create: 2020-05-29 17:33
 **/
@Data
public class KeyWordsReqDto {
    @Schema(description = "keyword")
    private String keyword;
    @Schema(description = "value")
    private String value;
}
