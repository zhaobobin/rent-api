package com.rent.common.dto.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @author zhaowenchao
 */
@Data
@Schema(description = "搜索页面返回数据")
public class SearchPageVo {

    @Schema(description = "历史")
    private List<String> history;

    @Schema(description = "热门")
    private List<String> hotSearchList;


}
