package com.rent.mapper.order;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rent.common.dto.backstage.ExportBuyOutOrderReq;
import com.rent.common.dto.export.BuyOutOrderExportDto;
import com.rent.model.order.UserOrderBuyOut;

import java.util.List;

/**
 * UserOrderBuyOutDao
 *
 * @author xiaoyao
 * @Date 2020-06-23 14:50
 */
public interface UserOrderBuyOutMapper extends BaseMapper<UserOrderBuyOut> {



    /**
     * 租赁订单导出-商家和运营公用一个接口
     * @param req
     * @return
     */
    List<BuyOutOrderExportDto> getBuyOutOrder(ExportBuyOutOrderReq req);

}