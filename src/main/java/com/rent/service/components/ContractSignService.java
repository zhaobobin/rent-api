package com.rent.service.components;


import com.rent.common.dto.components.dto.OrderContractDto;
import com.rent.common.dto.components.dto.ShopContractDto;
import com.rent.common.dto.components.response.ContractResultResponse;

import java.io.File;

/**
 * @author zhaowenchao
 */
public interface ContractSignService {

    /**
     * 签署订单合同
     * @param localFile
     * @param orderContractDto
     * @return
     */
    ContractResultResponse signOrderContract(File localFile, OrderContractDto orderContractDto);


    /**
     * 签署店铺合同
     * @param localFile
     * @param dto
     * @return
     */
    ContractResultResponse signShopContract(File localFile, ShopContractDto dto);

}
