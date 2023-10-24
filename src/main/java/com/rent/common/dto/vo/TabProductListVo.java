package com.rent.common.dto.vo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.dto.product.TabProductResp;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "我的页面数据")
@Data
public class TabProductListVo {

    @Schema(description = "商品")
    private Page<TabProductResp> products;

}
