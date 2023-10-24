
package com.rent.service.order;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.dto.backstage.TransferOrderRecordReqDto;
import com.rent.common.dto.backstage.TransferOrderRequest;
import com.rent.common.dto.order.TransferOrderRecordDto;


/**
 * 转单记录表Service
 *
 * @author youruo
 * @Date 2021-12-22 17:55
 */
public interface TransferOrderRecordService {

    /**
     * <p>
     * 根据条件列表
     * </p>
     *
     * @param request 实体对象
     * @return TransferOrderRecord
     */
    Page<TransferOrderRecordDto> queryTransferOrderRecordPage(TransferOrderRecordReqDto request);


    /**
     * 转单
     *
     * @param request
     * @return
     */
    Boolean transferOrder(TransferOrderRequest request);


}