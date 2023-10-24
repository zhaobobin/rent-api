package com.rent.service.product;


import com.rent.common.dto.product.TransferProductDto;
import com.rent.common.dto.product.TransferProductResponse;

/**
 * @author zhaowenchao
 */
public interface ProductExtraService {


    /**
     * 赋值转单商品
     *
     * @param request
     * @return
     */
    TransferProductResponse saveTransferProduct(TransferProductDto request);

    /**
     * 复制商品
     * @param productId
     * @return
     */
    Boolean busCopyProduct(String productId);
}
