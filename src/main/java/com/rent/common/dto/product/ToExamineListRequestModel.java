package com.rent.common.dto.product;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Schema(description = "店铺审核查询")
public class ToExamineListRequestModel implements Serializable {
    @Schema(description = "店铺状态 0待提交企业资质 1待填写店铺信息 2待提交品牌信息 3正在审核 4审核不通过 5审核通过，准备开店 6开店成功")
    private String status;
    @Schema(description = "商店ID")
    private String shopId;
    @Schema(description = "提交时间集合--2020-08-05 03:45:46")
    private List<String> createDate;
    @Schema(description = "pageNumber")
    private Integer pageNumber;
    @Schema(description = "pageSize")
    private Integer pageSize;
    @Schema(description = "企业名称")
    private String name;
    @Schema(description = "店铺名称")
    private String shopName;
    @Schema(description = "是否需要电话审核 0否 1是")
    private String isPhoneExamination;

    @Override
    public String toString() {
        return "ToExamineListRequestModel{" +
                "status='" + status + '\'' +
                ", shopId='" + shopId + '\'' +
                ", createDate=" + createDate +
                ", pageNumber=" + pageNumber +
                ", pageSize=" + pageSize +
                ", name='" + name + '\'' +
                ", shopName='" + shopName + '\'' +
                '}';
    }
}
