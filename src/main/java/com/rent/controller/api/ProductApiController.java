package com.rent.controller.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.constant.RedisKey;
import com.rent.common.dto.CommonResponse;
import com.rent.common.dto.api.ProductSearchDto;
import com.rent.common.dto.product.*;
import com.rent.common.dto.user.UserWordHistoryDto;
import com.rent.common.enums.marketing.CouponRangeConstant;
import com.rent.exception.HzsxBizException;
import com.rent.service.marketing.UserCollectionService;
import com.rent.service.product.OpeIndexShopBannerService;
import com.rent.service.product.ProductService;
import com.rent.service.user.UserWordHistoryService;
import com.rent.util.RedisUtil;
import com.rent.util.StringUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: lang
 * @description:
 * @author: yr
 * @create: 2020-07-16 10:04
 **/
@Slf4j
@RestController
@Tag(name = "小程序商品")
@RequestMapping("/zyj-api-web/hzsx/aliPay/product")
@RequiredArgsConstructor
public class ProductApiController {

    private final UserWordHistoryService userWordHistoryService;
    private final ProductService productService;
    private final UserCollectionService userCollectionService;
    private final OpeIndexShopBannerService opeIndexShopBannerService;

    @Operation(summary = "小程序商品搜索接口")
    @PostMapping("/searchProduct")
    public JSONObject searchProduct(@RequestBody ProductSearchDto productSearchDto) {
        if(StringUtil.isNotEmpty(productSearchDto.getContent()) && StringUtil.isNotEmpty(productSearchDto.getUid())){
            UserWordHistoryDto userWordHistoryDto = new UserWordHistoryDto();
            userWordHistoryDto.setUid(productSearchDto.getUid());
            userWordHistoryDto.setWord(productSearchDto.getContent());
            userWordHistoryService.addUserWordHistory(userWordHistoryDto);
        }
        //随机数
        Page<ShopProductAddReqDto> result =  productService.searchProduct(productSearchDto);
        return JSONObject.parseObject(JSON.toJSONString(result));
    }

    @Operation(summary = "小程序商品点击详情")
    @GetMapping("/getReturnFreightType")
    public CommonResponse<String> getReturnFreightType(@RequestParam("productId") String itemId) {
        return CommonResponse.<String>builder().data(productService.getReturnFreightType(itemId)).build();
    }


    @Operation(summary = "小程序商品点击详情")
    @GetMapping("/getProductDetailLite")
    public ShopProductAddReqDto getProductDetailLite(@RequestParam(value = "itemId") String itemId, @RequestParam(value = "uid", required = false, defaultValue = "") String uid) {
        ShopProductAddReqDto demo = productService.getProductDetailLite(itemId);
        if (null == demo) {
            throw new HzsxBizException("-1","商品不存在");
        }
        //封装是否收藏信息
        if(StringUtils.isNotEmpty(uid)){
            demo.setCollected(userCollectionService.checkCollection(uid, itemId, "PRODUCT"));
        }else {
            demo.setCollected(Boolean.FALSE);
        }
        return demo;
    }

    @Operation(summary = "店铺推荐商品")
    @GetMapping("/recommendLite")
    public JSONObject recommendLite(@RequestParam("productId")String productId) {
        Object result = RedisUtil.hget(RedisKey.LITE_PRODUCT_RECOMMEND,productId);
        if(result!=null){
            return JSONObject.parseObject(result.toString());
        }
        CommonResponse<List<ProductDetailRecommendResp>> response =
                CommonResponse.<List<ProductDetailRecommendResp>>builder().data(productService.recommendLite(productId)).build();
        JSONObject resp =  JSON.parseObject(JSON.toJSONString(response));
        if(response.isBusinessSuccess()){
            RedisUtil.hset(RedisKey.LITE_PRODUCT_RECOMMEND,productId,resp.toJSONString(),7200);
        }
        return resp;
    }

    @Operation(summary = "店铺分期&直购商品数据以及店铺数据")
    @GetMapping("/selectShopProductListAndShopV1")
    public JSONObject selectShopProductListAndShopV1(@RequestParam("shopId") String shopId, @RequestParam("pageNumber") Integer pageNumber, @RequestParam("pageSize") Integer pageSize) {
        String jedisIndexKey = RedisKey.ALIPAY_SHOP_PRODUCTS_V1 + shopId + pageNumber + pageSize;
        Object jedisOrderIdRslt = RedisUtil.get(jedisIndexKey);
        if (jedisOrderIdRslt != null) {
            return JSONObject.parseObject(jedisOrderIdRslt.toString());
        }
        JSONObject result = productService.selectShopProductListAndShopV1(shopId,pageNumber,pageSize);
        return result;
    }

    @Operation(summary = "小程序商家轮播图")
    @GetMapping("/queryOpeIndexShopBannerList")
    public List<OpeIndexShopBannerDto> queryOpeIndexShopBannerList(@RequestParam("shopId") String shopId) {
        return opeIndexShopBannerService.queryOpeIndexShopBannerList(shopId);
    }

    @Operation(summary = "小程序商家轮播图")
    @GetMapping("/liteIndexMore")
    CommonResponse<Page<ListProductDto>> liteIndexMore(@RequestParam("pageNumber") Integer pageNumber, @RequestParam("pageSize") Integer pageSize) {
        CouponRangeProductReqDto reqDto = new CouponRangeProductReqDto();
        reqDto.setType(CouponRangeConstant.RANGE_TYPE_ALL);
        reqDto.setPageNumber(pageNumber);
        reqDto.setPageSize(pageSize);
        return CommonResponse.<Page<ListProductDto>>builder().data(productService.getLiteProductInRange(reqDto)).build();
    }
}
