        
package com.rent.mapper.order;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.dto.export.FeeBillDto;
import com.rent.common.dto.order.FeeBillDetailReqDto;
import com.rent.common.dto.order.response.FeeBillPageResp;
import com.rent.common.dto.order.resquest.FeeBillReqDto;
import com.rent.common.enums.order.EnumOderFeeBillInvoiceStatus;
import com.rent.model.order.FeeBill;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * FeeBillMapper
 *
 * @author zhao
 * @Date 2020-08-11 09:59
 */
public interface FeeBillMapper extends BaseMapper<FeeBill>{

    /**
     * 更新开票状态
     * @param ticketId
     * @param ticketStatus
     */
    @Update("update ct_fee_bill set ticket_status=#{ticketStatus} where ticket_id=#{ticketId}")
    void updateTicketStatus(@Param("ticketId") Long ticketId,@Param("ticketStatus") EnumOderFeeBillInvoiceStatus ticketStatus);

    /**
     * 分页查询费用账单
     * @param request
     * @return
     */
    Page<FeeBillPageResp> pageByCondition(IPage<FeeBill> page, FeeBillReqDto request);

    /**
     * 导出查询
     * @param request
     * @return
     */
    List<FeeBillDto> feeBillDetail(FeeBillDetailReqDto request);
}