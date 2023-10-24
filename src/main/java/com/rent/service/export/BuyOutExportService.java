package com.rent.service.export;



import com.rent.common.dto.backstage.ExportBuyOutOrderReq;
import com.rent.common.dto.export.BuyOutOrderExportDto;

import java.util.List;

/**
 * @author zhaowenchao
 */
public interface BuyOutExportService {

    /**
     * 买断订单导出
     * @param request
     */
    List<BuyOutOrderExportDto> buyOut(ExportBuyOutOrderReq request);
}
