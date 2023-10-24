        
package com.rent.dao.order;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.dto.export.FeeBillDto;
import com.rent.common.dto.order.FeeBillDetailReqDto;
import com.rent.common.dto.order.response.FeeBillPageResp;
import com.rent.common.dto.order.resquest.FeeBillReqDto;
import com.rent.common.enums.order.EnumFeeBillType;
import com.rent.common.enums.order.EnumOderFeeBillInvoiceStatus;
import com.rent.config.mybatis.IBaseDao;
import com.rent.model.order.FeeBill;

import java.util.List;

/**
 * FeeBillDao
 *
 * @author zhao
 * @Date 2020-08-11 09:59
 */
public interface FeeBillDao extends IBaseDao<FeeBill> {

    /**
     * 根据订单编号获取租押分离费用信息
     * @param orderIdList
     * @return
     */
    List<FeeBill> getAssessmentFeeByOrderId(List<String> orderIdList);


    /**
     * 根据订单编号以及费用类型获取费用信息
     * @param orderId
     * @param type
     * @return
     */
    FeeBill getByOrderIdAndType(String orderId, EnumFeeBillType type);

    /**
     * 更新开票状态
     * @param ticketId
     */
    void updateTicketStatus(Long ticketId,EnumOderFeeBillInvoiceStatus ticketStatus);

    /**
     * 分页查询费用账单
     * @param request
     * @return
     */
    Page<FeeBillPageResp> pageByCondition(FeeBillReqDto request);

    /**
     * 导出查询
     * @param request
     * @return
     */
    List<FeeBillDto> feeBillDetail(FeeBillDetailReqDto request);
}
