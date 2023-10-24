package com.rent.common.dto.vo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.dto.backstage.resp.TabListResp;
import com.rent.common.dto.marketing.PageElementConfigDto;
import com.rent.common.dto.product.TabProductResp;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Schema(description = "首页数据")
@Data
public class IndexActionListByPageVo {

    @Schema(description = "热搜词")
    private List<String> hotSearchList;

    @Schema(description = "轮播图")
    private List<PageElementConfigDto> bannerList;

    @Schema(description = "专区主图")
    private List<PageElementConfigDto> specialMain;

    @Schema(description = "专区副图")
    private List<PageElementConfigDto> specialSub;

    @Schema(description = "专区-标题-主标题")
    private String specialTitleMain;

    @Schema(description = "专区-标题-副标题")
    private String specialTitleSub;

    @Schema(description = "金刚区")
    private List<PageElementConfigDto> iconList;

    @Schema(description = "商品")
    private Page<TabProductResp> products;

    @Schema(description = "标签")
    private List<TabListResp> tabList;
}
