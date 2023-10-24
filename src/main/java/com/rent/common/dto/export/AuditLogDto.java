package com.rent.common.dto.export;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: hzsx-rent-parent
 * @description: 审核记录
 * @author: yr
 * @create: 2021-07-16 14:46
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuditLogDto {
    private String auditOpetor;
    private String auditTime;
    private String auditContent;
}
