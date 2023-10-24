package com.rent.common.dto.product;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
// @Schema(description = "商家查询商品表")
public class ShopProductSerachReqDto implements Serializable {

    //@Schema(description = "productId", value = "productId")
    private String productId;
    //@Schema(description = "商品名称", value = "商品名称")
    private String productName;
    //@Schema(description = "商品类目", value = "商品类目")
    private String categoryIds;
    //@Schema(description = "商品上架状态", value = "商品上架状态 0回收站中的商品 1已上架售卖的商品 2放在仓库的商品")
    private Integer type;
    //@Schema(description = "商品审核状态", value = "商品审核状态 0为正在审核中 1为审核拒绝 2为审核通过")
    private Integer auditState;
    //@Schema(description = "商家ID", value = "商家ID shopId")
    private String shopId;
    //@Schema(description = "商家ID", value = "shopAdminId shopAdminId")
    private String shopAdminId;
    //@Schema(description = "创建开始日期&创建结束日期", value = "创建开始日期&创建结束日期")
    private List<String> creatTime = new ArrayList<>();
    //@Schema(description = "pageNumber", value = "pageNumber")
    private Integer pageNumber;
    //@Schema(description = "pageSize", value = "pageSize")
    private Integer pageSize;
    //@Schema(description = "是否支持买断", value = "0 全部   1 已经配置 2未配置")
    private Integer isBuyOut;
    //@Schema(description = "是否删除", value = "true已删除，false未删除")
    private Boolean isDelete;
}
