package com.rent.common.dto.backstage.request;

import com.rent.common.dto.Page;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "信审管理-查看信审人员列表请求参数")
public class AuditUserPageReq extends Page {

    @Schema(description = "信审人员")
    private String name;
}
