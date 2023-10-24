package com.rent.common.dto.backstage;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author YR
 */
@Data
public class NoticeDto {
    @Schema(description = "标题")
    private String title;
    @Schema(description = "详情")
    private String detail;

}
