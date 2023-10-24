        
package com.rent.service.order;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.dto.order.response.FeeBillPageResp;
import com.rent.common.dto.order.resquest.FeeBillReqDto;

import java.util.List;

/**
 * @author zhaowenchao
 */
public interface FeeBillService {

    /**
     * 导入预授权转支付费用
     * @param outOrderNoList
     */
    Boolean importAssessmentFee(List<String> outOrderNoList);

    /**
     * 查看风控报告计费
     * @param orderId
     * @return
     */
    Boolean reportBilling(String orderId);

    /**
     * 签署订单合同计费
     * @param orderId
     * @return
     */
    Boolean contractBilling(String orderId);


    /**
     * 分页查询费用账单
     * @param reqDto
     * @return
     */
    Page<FeeBillPageResp> page(FeeBillReqDto reqDto);

}