package com.rent.common.dto.product;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author boan
 */
@Data
@Schema(description = "商品审核参数")
public class ExamineProductQueryReqDto implements Serializable {

    private static final long serialVersionUID = 2582628985874223296L;

    @Schema(description = "商品ID")
    private String productId;

    @Schema(description = "商品名称")
    private String productName;

    /**
     * 商品审核状态 0为正在审核中 1为审核不通过 2为审核通过
     */
    @Schema(description = "商品审核状态 0为正在审核中 1为审核不通过 2为审核通过")
    private String auditState;

    @Schema(description = "创建时间集合")
    private List<String> creatTime = new ArrayList<>();

    @Schema(description = "商店ID")
    private String shopId;

    @Schema(description = "商店名称")
    private String shopName;

    /**
     * 商品状态 0为失效 1为有效
     */
    @Schema(description = "商品状态 0为失效 1为有效")
    private Integer status;

    /**
     * 商家上下架状态  1 已上架 2 已下架 0 回收站
     */
    @Schema(description = "商家上下架状态  1 已上架 2 已下架 0 回收站")
    private Integer type;

    @Schema(description = "pageNumber")
    private Integer pageNumber;
    @Schema(description = "pageSize")
    private Integer pageSize;


    /**
     * 类目ID
     */
    @Schema(description = "类目ID")
    private Integer categoryId;


}
