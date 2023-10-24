package com.rent.service.export.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.rent.common.dto.backstage.ExportBuyOutOrderReq;
import com.rent.common.dto.export.BuyOutOrderExportDto;
import com.rent.common.enums.export.ExportFileName;
import com.rent.common.enums.order.EnumOrderBuyOutStatus;
import com.rent.config.annotation.ExportFile;
import com.rent.dao.order.UserOrderBuyOutDao;
import com.rent.dao.product.ProductDao;
import com.rent.model.product.Product;
import com.rent.service.export.BuyOutExportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author zhaowenchao
 */
@Service
@Slf4j
public class BuyOutExportServiceImpl implements BuyOutExportService {

    private UserOrderBuyOutDao userOrderBuyOutDao;
    private ProductDao productDao;

    public BuyOutExportServiceImpl(UserOrderBuyOutDao userOrderBuyOutDao, ProductDao productDao) {
        this.userOrderBuyOutDao = userOrderBuyOutDao;
        this.productDao = productDao;
    }


    @Override
    @ExportFile(fileName= ExportFileName.BUY_OUT_ORDER,exportDtoClazz= BuyOutOrderExportDto.class)
    public List<BuyOutOrderExportDto> buyOut(ExportBuyOutOrderReq request) {
        List<BuyOutOrderExportDto> list = userOrderBuyOutDao.getBuyOutOrder(request);
        if(CollectionUtil.isEmpty(list)){
            return list;
        }
        List<String> productIdList = list.stream().map(BuyOutOrderExportDto::getProductId).collect(Collectors.toList());
        //获取商品ID和商品名称对应信息
        List<Product> productList = productDao.getProductNames(productIdList);
        Map<String,String> productNameMap = productList.stream().collect(Collectors.toMap(Product::getProductId,Product::getName,(k1 , k2)-> k2 ));
        for (BuyOutOrderExportDto buyOutOrderExportDto : list) {
            buyOutOrderExportDto.setProductName(productNameMap.get(buyOutOrderExportDto.getProductId()));
            buyOutOrderExportDto.setStatus(EnumOrderBuyOutStatus.find(buyOutOrderExportDto.getStatus()).getDescription());
        }
        return list;
    }
}
