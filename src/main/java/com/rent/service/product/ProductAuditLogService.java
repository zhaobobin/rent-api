        
package com.rent.service.product;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.dto.product.ProductAuditLogDto;
import com.rent.common.dto.product.ProductAuditLogReqDto;

/**
 * 商品审核日志表Service
 *
 * @author youruo
 * @Date 2020-06-29 18:32
 */
public interface ProductAuditLogService {


        /**
         * <p>
         * 根据条件列表
         * </p>
         *
         * @param request 实体对象
         * @return ProductAuditLog
         */
        Page<ProductAuditLogDto> queryProductAuditLogPage(ProductAuditLogReqDto request);


}