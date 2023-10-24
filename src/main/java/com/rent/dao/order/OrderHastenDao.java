package com.rent.dao.order;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.enums.order.EnumOrderRemarkSource;
import com.rent.config.mybatis.IBaseDao;
import com.rent.model.order.OrderHasten;

import java.util.List;
import java.util.Map;

/**
 * OrderHastenDao
 *
 * @author
 * @Date
 */
public interface OrderHastenDao extends IBaseDao<OrderHasten> {

    /**
     * 据订单id和催收记录源查询备注
     * @param page
     * @param orderId
     * @param source
     * @return
     */
    Page<OrderHasten> pageByOrderId(Page<OrderHasten>page, String orderId, EnumOrderRemarkSource source);

    Map<String, List<OrderHasten>> queryListByOrderIds(List<String> orderIdList);
}
