package com.rent.controller.backstage;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.dto.CommonResponse;
import com.rent.common.dto.backstage.request.AuditUserPageReq;
import com.rent.common.dto.backstage.resp.AuditUserPageResp;
import com.rent.service.user.AuditUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@Tag(name = "信审人员管理")
@RestController
@RequestMapping("/zyj-backstage-web/hzsx/auditUser")
@RequiredArgsConstructor
public class AuditUserController {

    private final AuditUserService auditUserService;

    @Operation(summary = "分页查看信审人员信息")
    @PostMapping("/page")
    public CommonResponse<Page<AuditUserPageResp>> page(@RequestBody AuditUserPageReq req) {
        return CommonResponse.<Page<AuditUserPageResp>>builder().data(auditUserService.page(req)).build();
    }

    @Operation(summary = "修改信审状态")
    @GetMapping("/changeStatus")
    public CommonResponse<Boolean> changeStatus(@RequestParam("id") @Parameter(name = "id", description = "信审编号") Long id) {
        return CommonResponse.<Boolean>builder().data(auditUserService.changeStatus(id)).build();
    }

    @Operation(summary = "添加信审人员")
    @GetMapping("/add")
    public CommonResponse<Boolean> add(@RequestParam("backstageUserId") @Parameter(name = "backstageUserId", description = "后台用户ID") Long backstageUserId,
                                       @RequestParam(value = "qrcodeUrl", required = false) @Parameter(name = "qrcodeUrl", description = "信审人员联系二维码") String qrcodeUrl) {
        return CommonResponse.<Boolean>builder().data(auditUserService.add(backstageUserId, qrcodeUrl)).build();
    }

}

