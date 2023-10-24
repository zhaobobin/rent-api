package com.rent.service.order;

/**
 * @author zhaowenchao
 */
public interface OrderStageService {

    /**
     * 押金抵扣
     * @param id
     * @return
     */
    Boolean depositMortgage(Long id);


}
