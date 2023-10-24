package com.rent.controller.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.cache.api.IndexCache;
import com.rent.common.cache.product.ProductSalesCache;
import com.rent.common.dto.CommonResponse;
import com.rent.common.dto.backstage.resp.TabListResp;
import com.rent.common.dto.marketing.PageElementConfigDto;
import com.rent.common.dto.order.HotRentDto;
import com.rent.common.dto.order.OrderStatusCountDto;
import com.rent.common.dto.product.ListProductDto;
import com.rent.common.dto.product.RentWellProductDto;
import com.rent.common.dto.product.TabProductResp;
import com.rent.common.dto.vo.IndexActionListByPageVo;
import com.rent.common.dto.vo.MyPageVo;
import com.rent.common.dto.vo.TabProductListVo;
import com.rent.service.marketing.HotSearchConfigService;
import com.rent.service.marketing.PageElementConfigService;
import com.rent.service.order.UserOrdersQueryService;
import com.rent.service.product.ProductService;
import com.rent.service.product.TabService;
import com.rent.util.AppParamUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;


/**
 * @program: lang
 * @description:
 * @author: yr
 * @create: 2020-07-16 10:04
 **/
@Slf4j
@RestController
@RequestMapping("/zyj-api-web/hzsx/aliPay/index")
@Tag(name = "小程序首页和我的页面数据")
@RequiredArgsConstructor
public class IndexActivityController {

    private final PageElementConfigService pageElementConfigService;
    private final HotSearchConfigService hotSearchConfigService;
    private final TabService tabService;
    private final UserOrdersQueryService userOrdersQueryService;
    private final ProductService productService;

    @Operation(summary = "获取首页数据,以及第一个tab下的商品")
    @GetMapping("/getIndexActionListByPage")
    public IndexActionListByPageVo getIndexActionList(@RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize) {
        IndexActionListByPageVo vo = IndexCache.getIndexActionCache(AppParamUtil.getChannelId(),pageNum,pageSize);
        if(vo!=null){
            return vo;
        }
        vo = new IndexActionListByPageVo();
        List<PageElementConfigDto> bannerList = pageElementConfigService.listBanner();
        List<PageElementConfigDto> specialMainList = pageElementConfigService.listSpecialAreaMain();
        List<PageElementConfigDto> specialSubList = pageElementConfigService.listSpecialAreaSub();
        List<PageElementConfigDto> iconList = pageElementConfigService.listIconArea();
        List<String> hotSearchList = hotSearchConfigService.list(AppParamUtil.getChannelId());
        String specialTitleMain = pageElementConfigService.getSpecialTitleMain();
        String specialTitleSub = pageElementConfigService.getSpecialTitleSub();
        vo.setHotSearchList(hotSearchList);
        vo.setBannerList(bannerList);
        vo.setSpecialMain(specialMainList);
        vo.setSpecialSub(specialSubList);
        vo.setSpecialTitleMain(specialTitleMain);
        vo.setSpecialTitleSub(specialTitleSub);
        vo.setIconList(iconList);
        List<TabListResp> tabList = tabService.list(AppParamUtil.getChannelId());
        if (CollectionUtils.isNotEmpty(tabList)) {
            Page<TabProductResp> productPage = tabService.listProductForApi(tabList.get(0).getId(),pageNum,pageSize);
            vo.setProducts(productPage);
            vo.setTabList(tabList);
        }
        IndexCache.setIndexActionCache(AppParamUtil.getChannelId(),pageNum,pageSize,vo);
        return vo;
    }

    @Operation(summary = "获取首页tab下的商品信息")
    @GetMapping("/getIndexTabAndProductByPage")
    public TabProductListVo getIndexTabAndProductByPage(@RequestParam("tabId") Long tabId,
                                                        @RequestParam("pageNum") int pageNum,
                                                        @RequestParam("pageSize") int pageSize) {
        TabProductListVo vo = IndexCache.getIndexTabCache(AppParamUtil.getChannelId(),tabId,pageNum,pageSize);
        if(vo!=null){
            return vo;
        }
        vo = new TabProductListVo();
        Page<TabProductResp> productPage = tabService.listProductForApi(tabId,pageNum,pageSize);
        vo.setProducts(productPage);
        IndexCache.setIndexTabCache(AppParamUtil.getChannelId(),tabId,pageNum,pageSize, vo);
        return vo;
    }

    @Operation(summary = "获取我的页面的数据")
    @GetMapping("/myPage")
    public CommonResponse<MyPageVo> myPage(@RequestParam(value = "uid",required = false)String uid) {
        MyPageVo vo = new MyPageVo();
        //我的服务配置
        List<PageElementConfigDto> myService = pageElementConfigService.listMyService();
        vo.setServices(myService);
        //我的订单配置
        List<PageElementConfigDto> myOrders = pageElementConfigService.listMyOrder();
        if(StringUtils.isNotEmpty(uid)){
            OrderStatusCountDto dto = userOrdersQueryService.liteStatusCount(uid);
            for (PageElementConfigDto myOrder : myOrders) {
                if(myOrder.getExtCode()==null){
                    continue;
                }
                switch (myOrder.getExtCode()){
                    case ORDER_WAIT_PAY:
                        myOrder.setShowNum(dto.getWaitPay());break;
                    case ORDER_WAIT_DELIVERY:
                        myOrder.setShowNum(dto.getWaitDelivery());break;
                    case ORDER_WAIT_RECEIVE:
                        myOrder.setShowNum(dto.getWaitReceive());break;
                    case ORDER_RENTING:
                        myOrder.setShowNum(dto.getRenting());break;
                    case ORDER_WAIT_SETTLE:
                        myOrder.setShowNum(dto.getWaitSettle());break;
                    case ORDER_OVERDUE:
                        myOrder.setShowNum(dto.getOverdue());break;
                }
            }
        }
        vo.setOrders(myOrders);
        //热销商品配置
        List<HotRentDto> hotRentDtoList = userOrdersQueryService.hotRentOrder();
        Map<String,Integer> total = new HashMap<>();
        for (HotRentDto hotRentDto : hotRentDtoList) {
            String productId = hotRentDto.getProductId();
            Integer totalCount = total.containsKey(productId) ? total.get(productId) : 0;
            total.put(productId,totalCount+1);
        }
        List<Map.Entry<String, Integer>> entryList = new ArrayList<>(total.entrySet());
        if(CollectionUtils.isNotEmpty(entryList)){
            Collections.sort(entryList, (o1, o2) -> o2.getValue().compareTo(o1.getValue()));
            if(entryList.size()>20){
                entryList.subList(0,20);
            }
            List<String> productIds = entryList.stream().map(Map.Entry::getKey).collect(Collectors.toList());
            List<ListProductDto> productDtoList =  productService.getHotRentProduct(productIds);
            List<RentWellProductDto> rentWellProductDtoList = new ArrayList<>(productDtoList.size());
            for (ListProductDto dto : productDtoList) {
                RentWellProductDto rentWellProductDto = new RentWellProductDto();
                rentWellProductDto.setProductId(dto.getProductId());
                rentWellProductDto.setProductName(dto.getName());
                rentWellProductDto.setImage(dto.getImage());
                rentWellProductDto.setPrice(dto.getMinPrice());
                rentWellProductDto.setOldNewDegree(dto.getOldNewDegree());
                rentWellProductDto.setSalesVolume(ProductSalesCache.getProductSales(dto.getProductId()));
                rentWellProductDtoList.add(rentWellProductDto);
            }
            vo.setProducts(rentWellProductDtoList);
        }
        return CommonResponse.<MyPageVo>builder().data(vo).build();
    }

    @Operation(summary = "获取小程序公告")
    @GetMapping("/selectApiNotice")
    public CommonResponse<String> selectApiNotice() {
        return CommonResponse.<String>builder().data(IndexCache.getIndexApiNoticeCache(AppParamUtil.getChannelId())).build();
    }


}
