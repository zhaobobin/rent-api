package com.rent.common.dto.api.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.validation.constraints.Size;
import java.util.List;


@Data
@Schema(description = "用户提交订单投诉请求参数")
public class SubmitOrderComplaintReqVo {

    @Schema(description = "用户ID")
    private String uid;

    @Schema(description = "用户手机")
    private String telphone;

    @Schema(description = "用户名称")
    private String name;

    @Schema(description = "投诉内容")
    @Size(max=150,message="字数超出，最多{max}个字")
    private String content;

    @Schema(description = "投诉类型")
    private Long typeId;

    @Schema(description = "投诉订单ID")
    private String orderId;

    @Schema(description = "投诉商户ID")
    private String shopId;

    @Schema(description = "评价图片")
    private List<String> images;


    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
