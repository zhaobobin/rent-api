package com.rent.service.components.impl;

import com.alipay.api.AlipayClient;
import com.alipay.api.FileItem;
import com.alipay.api.request.AlipayMerchantItemFileUploadRequest;
import com.alipay.api.response.AlipayMerchantItemFileUploadResponse;
import com.rent.common.util.AliPayClientFactory;
import com.rent.common.util.OSSFileUtils;
import com.rent.dao.components.OrderCenterProductDao;
import com.rent.dao.product.ProductImageDao;
import com.rent.model.components.OrderCenterProduct;
import com.rent.service.components.OrderCenterProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Date;

/**
 * @author udo
 * 支付宝 小程序 订单中心服务
 * https://opendocs.alipay.com/mini/introduce/ordercenter
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class OrderCenterProductServiceImpl implements OrderCenterProductService {

    private final OrderCenterProductDao orderCenterProductDao;
    private final ProductImageDao productImageDao;
    private final OSSFileUtils ossFileUtils;

    @Override
    public OrderCenterProduct getByProductId(String productId) {
        OrderCenterProduct orderCenterProduct = orderCenterProductDao.getByProductId(productId);
        if(orderCenterProduct!=null){
            return orderCenterProduct;
        }
        return fileUpload(productId);
    }

    /**
     * 支付宝小程序-订单中心-商品文件上传
     * https://opendocs.alipay.com/apis/api_4/alipay.merchant.item.file.upload
     * @param productId
     */
    private OrderCenterProduct fileUpload(String productId) {
        String image = productImageDao.getMainImage(productId);
        log.info("【订单中心-上传商品文件】objectKey={},productId={}", new Object[] {image, productId});
        try {
            File file = ossFileUtils.downImgUrl(image);
            AlipayClient alipayClient = AliPayClientFactory.getAlipayClientByType();
            AlipayMerchantItemFileUploadRequest request = new AlipayMerchantItemFileUploadRequest();
            request.setScene("SYNC_ORDER");
            FileItem FileContent = new FileItem(file.getPath());
            request.setFileContent(FileContent);
            AlipayMerchantItemFileUploadResponse response = alipayClient.certificateExecute(request);
            log.info("response=" + response);
            if (response.isSuccess()) {
                OrderCenterProduct orderCenterProduct = new OrderCenterProduct();
                orderCenterProduct.setObjectKey(image);
                orderCenterProduct.setProductId(productId);
                orderCenterProduct.setMaterialId(response.getMaterialId());
                orderCenterProduct.setMaterialKey(response.getMaterialKey());
                orderCenterProduct.setCreateTime(new Date());
                orderCenterProductDao.save(orderCenterProduct);
                return orderCenterProduct;
            } else {
                log.info("【订单中心-上传商品文件-请求失败】responseBody={}", new Object[] {response.getBody()});
            }
        } catch (Exception e) {
            log.error("【订单中心-上传商品文件-异常】", e);
        }
        return null;

    }
}
