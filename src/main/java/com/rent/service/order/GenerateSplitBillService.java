        
package com.rent.service.order;

import com.rent.common.dto.product.SpiltBillRuleConfigDto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author zhaowenchao
 */
public interface GenerateSplitBillService {

        /**
         * 生成分账信息
         * @param configDto
         * @param recordId
         * @param shopId
         * @param userPayAmount
         * @param userPayTime
         * @param type
         * @param orderId
         * @param period
         * @param uid
         */
        void generate(SpiltBillRuleConfigDto configDto,
                      Long recordId, String shopId, BigDecimal userPayAmount, Date userPayTime, String type,
                      String orderId, Integer period, String uid);


}