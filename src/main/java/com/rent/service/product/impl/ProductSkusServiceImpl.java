
package com.rent.service.product.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.rent.common.converter.product.ProductSkusConverter;
import com.rent.common.dto.product.PricingInformationsDto;
import com.rent.common.dto.product.ProductCyclePricesDto;
import com.rent.common.dto.product.ProductSkusDto;
import com.rent.common.dto.product.ShopProductSkuAllReqDto;
import com.rent.common.enums.product.ProductSkuPayEnum;
import com.rent.common.enums.product.ProductStatus;
import com.rent.dao.product.ProductSkuValuesDao;
import com.rent.dao.product.ProductSkusDao;
import com.rent.model.product.ProductSkuValues;
import com.rent.model.product.ProductSkus;
import com.rent.service.product.ProductCyclePricesService;
import com.rent.service.product.ProductSkusService;
import com.rent.service.product.ProductSpecValueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 商品sku表Service
 *
 * @author youruo
 * @Date 2020-06-16 15:27
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ProductSkusServiceImpl implements ProductSkusService {

    private final ProductSkusDao productSkusDao;
    private final ProductSkuValuesDao productSkuValuesDao;
    private final ProductCyclePricesService productCyclePricesService;
    private final ProductSpecValueService productSpecValueService;

    @Override
    public List<PricingInformationsDto> selectPricingByItemId(String productId) {
        List<PricingInformationsDto> result = new ArrayList<>();
        productSkusDao.list(new QueryWrapper<ProductSkus>().
                        eq("item_id", productId).isNull("delete_time"))
                .stream().map(sku -> {
                            List<ProductCyclePricesDto> cycles = productCyclePricesService.selectCyclePricesBySkuId(Long.parseLong(sku.getId().toString()));
                            List<ProductSkuValues> productSkuValues = productSkuValuesDao.list(new QueryWrapper<ProductSkuValues>().eq("sku_id", sku.getId()).isNull("delete_time"));
                            List<Long> specValueIds = productSkuValues.stream().map(ProductSkuValues::getSpecValueId).collect(Collectors.toList());
                            String spe = productSpecValueService.getProductSpecById(specValueIds);
                            cycles.forEach(cyc -> {
                                PricingInformationsDto skuDto = new PricingInformationsDto();
                                StringBuffer rent = new StringBuffer();
                                rent.append(cyc.getPrice()).append("元/").append(cyc.getDays()).append("天");
                                skuDto.setRentPrice(rent.toString());
                                skuDto.setInventory(sku.getInventory());
                                skuDto.setMarketPrice(sku.getMarketPrice());
                                skuDto.setBuyOutSupport(sku.getBuyOutSupport());
                                StringBuffer salePrice = new StringBuffer();
                                salePrice.append(cyc.getSalePrice()).append("元/").append(cyc.getDays()).append("天");
                                skuDto.setSalePrice(salePrice.toString());
                                skuDto.setSpeAndColer(spe);
                                result.add(skuDto);
                            });
                            return null;
                        }
                ).collect(Collectors.toList());
        return result;
    }

    @Override
    public ProductSkusDto getSkuBySkuId(Long skuId) {
        ProductSkus productSkus = new ProductSkus();
        if (null != skuId) {
            productSkus = productSkusDao.getOne(new QueryWrapper<ProductSkus>().eq("id", skuId).isNull("delete_time"));
        }
        return ProductSkusConverter.model2Dto(productSkus);
    }

    @Override
    public List<ShopProductSkuAllReqDto> selectPruductSkusByItemId(String productId) {
        List<ShopProductSkuAllReqDto> result = new ArrayList<>();
        result = productSkusDao.list(new QueryWrapper<ProductSkus>().
                eq("item_id", productId).isNull("delete_time"))
                .stream().map(sku -> {
                            ShopProductSkuAllReqDto skuDto = new ShopProductSkuAllReqDto();
                            List<ProductSkuValues> productSkuValues = productSkuValuesDao.list(new QueryWrapper<ProductSkuValues>().eq("sku_id", sku.getId()).isNull("delete_time"));
                            skuDto.setSkuId(sku.getId());
                            skuDto.setInventory(sku.getInventory());
                            skuDto.setDepositPrice(sku.getDepositPrice());
                            skuDto.setMarketPrice(sku.getMarketPrice());
                            skuDto.setOldNewDegree(sku.getOldNewDegree());
                            skuDto.setCycs(productCyclePricesService.selectCyclePricesBySkuId(Long.parseLong(sku.getId().toString())));
                            List<Long> specValueIds = productSkuValues.stream().map(ProductSkuValues::getSpecValueId).collect(Collectors.toList());
                            skuDto.setSpecAll(productSpecValueService.selectProductSpecById(specValueIds));
                            return skuDto;
                        }
                ).collect(Collectors.toList());
        return result;
    }


    /**
     * 给出总金额和花呗分期数，计算每期支付金额
     * @param totalAmount
     * @param periodNum
     * @return
     */
    public  BigDecimal calculate(BigDecimal totalAmount, Integer periodNum, Integer isHandlingFee){
        BigDecimal result = new BigDecimal(BigInteger.ZERO);
        if(null != periodNum) {
            long payAmount = totalAmount.multiply(new BigDecimal(100)).longValue();
            BigDecimal hbPeriodNum = new BigDecimal(periodNum);
            BigDecimal eachPrincipal = BigDecimal.valueOf(payAmount).divide(hbPeriodNum, BigDecimal.ROUND_DOWN);
            if (isHandlingFee.equals(ProductStatus.IS_HANDLING_FEE.getCode())) {
                //用户承担手续费
                BigDecimal totalFeeInDecimal = BigDecimal.valueOf(payAmount).multiply(ProductSkuPayEnum.find(periodNum).getMsg());
                long totalFeeInLong = totalFeeInDecimal.setScale(0, BigDecimal.ROUND_HALF_EVEN).longValue();
                BigDecimal eachFee = BigDecimal.valueOf(totalFeeInLong).divide(hbPeriodNum, BigDecimal.ROUND_DOWN);
                result = eachFee.add(eachPrincipal);
                result = result.divide(new BigDecimal(100));
                return result;
            }else{
                result = eachPrincipal.divide(new BigDecimal(100));
            }
        }
        return result;
    }


    @Override
    public Integer addProductSkus(ProductSkusDto request) {
        ProductSkus model = ProductSkusConverter.dto2Model(request);
        if (productSkusDao.save(model)) {
            return model.getId();
        } else {
            throw new MybatisPlusException("保存数据失败");
        }
    }

    @Override
    public void deleteProductSkus(String itemId) {
        QueryWrapper<ProductSkus> wh = new QueryWrapper<>();
        wh.eq("item_id", itemId);
        wh.isNull("delete_time");
        ProductSkus ss = new ProductSkus();
        ss.setDeleteTime(new Date());
        productSkusDao.update(ss, wh);
    }
}