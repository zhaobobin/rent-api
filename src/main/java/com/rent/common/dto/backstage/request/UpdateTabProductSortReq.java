package com.rent.common.dto.backstage.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;


/**
 * @author zhaowenchao
 */
@Data
@Schema(description = "修改tab下商品排序请求参数")
public class UpdateTabProductSortReq implements Serializable {

    private static final long serialVersionUID = 2346915145375375346L;

    @Schema(description = "tabProduct id值,/zyj-backstage-web/hzsx/tab/listProduct接口返回的记录的ID")
    private Long id;

    @Schema(description = "排序值")
    private Integer indexSort;

}
