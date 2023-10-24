        
package com.rent.service.product.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.converter.product.ProductAuditLogConverter;
import com.rent.common.dto.product.ProductAuditLogDto;
import com.rent.common.dto.product.ProductAuditLogReqDto;
import com.rent.dao.product.ProductAuditLogDao;
import com.rent.model.product.ProductAuditLog;
import com.rent.service.product.ProductAuditLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 商品审核日志表Service
 *
 * @author youruo
 * @Date 2020-06-29 18:32
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ProductAuditLogServiceImpl implements ProductAuditLogService {

    private final ProductAuditLogDao productAuditLogDao;

    @Override
    public Page<ProductAuditLogDto> queryProductAuditLogPage(ProductAuditLogReqDto model) {
        Page<ProductAuditLog> page = this.productAuditLogDao.page(new Page<>(model.getPageNumber(), model.getPageSize()),
                new QueryWrapper<ProductAuditLog>()
                        .eq(StringUtils.isNotEmpty(model.getItemId()), "item_id", model.getItemId())
                        .isNull("delete_time")
                        .orderByDesc("create_time")
        );
        return new Page<ProductAuditLogDto>(page.getCurrent(), page.getSize(), page.getTotal()).setRecords(
            ProductAuditLogConverter.modelList2DtoList(page.getRecords()));
    }

}