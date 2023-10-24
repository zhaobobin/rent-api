package com.rent.common.dto.components.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Boan
 * @version 1.0
 * @date 2020/2/24
 * @desc 身份证正面ocr识别信息对象
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "身份证ocr认证响应参数")
public class IdCardOcrResponse {

    @Schema(description = "姓名")
    private String userName;

    @Schema(description = "身份证号")
    private String idCardNo;

    @Schema(description = "身份证到期日期")
    private String limitDate;

    @Schema(description ="有效期开始时间")
    private String startDate;

    @Schema(description = "地址")
    private String address;

    @Schema(description = "性别")
    private String sex;

    @Schema(description = "民族")
    private String nation;

    @Schema(description = "发证机关")
    private String issueOrg;
}
