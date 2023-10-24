package com.rent.common.dto.backstage.request;

import com.rent.common.dto.Page;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;


/**
 * @author zhaowenchao
 */
@Data
@Schema(description = "tab下添加商品请求参数")
public class AddTabProductReq extends Page implements Serializable {

    private static final long serialVersionUID = 2346915145375375346L;

    @Schema(description = "tabId")
    private Long tabId;

    @Schema(description = "商品编号列表")
    private List<String> productIds;

}
