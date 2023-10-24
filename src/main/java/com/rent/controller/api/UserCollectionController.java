        
package com.rent.controller.api;


import com.rent.common.dto.CommonResponse;
import com.rent.common.dto.marketing.CollectionInfo;
import com.rent.common.dto.marketing.UserCollectionDto;
import com.rent.common.dto.marketing.UserCollectionReqDto;
import com.rent.common.dto.product.ListProductDto;
import com.rent.service.marketing.UserCollectionService;
import com.rent.service.product.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 用户收藏表控制器
 *
 * @author zhao
 * @Date 2020-07-22 15:28
 */
@Tag(name = "小程序用户收藏模块")
@RestController
@RequestMapping("/zyj-api-web/hzsx/userCollection")
@RequiredArgsConstructor
public class UserCollectionController {

    private static final String COLLECTION_RESOURCE_TYPE_PRODUCT = "PRODUCT";

    private final UserCollectionService userCollectionService;
    private final ProductService productService;

    @Operation(summary = "收藏商品")
    @PostMapping("/addProductCollection")
    public CommonResponse<Long> addProductCollection(@RequestBody UserCollectionDto request) {
        request.setResourceType(COLLECTION_RESOURCE_TYPE_PRODUCT);

        return CommonResponse.<Long>builder().data(userCollectionService.addUserCollection(request)).build();
    }

    @Operation(summary = "取消收藏商品")
    @PostMapping("/cancelProductCollection")
    public CommonResponse<Boolean> cancelProductCollection(@RequestBody UserCollectionDto request) {
        return CommonResponse.<Boolean>builder().data(userCollectionService.cancelUserCollection(request)).build();
    }

    @Operation(summary = "分页获取收藏商品")
    @GetMapping("/getProductCollection")
    public CommonResponse<List<ListProductDto>> getProductCollection(@RequestParam("uid") String uid) {
        UserCollectionReqDto reqDto = new UserCollectionReqDto();
        reqDto.setResourceType(COLLECTION_RESOURCE_TYPE_PRODUCT);
        reqDto.setUid(uid);

        List<CollectionInfo> collectionInfos = userCollectionService.queryUserCollection(reqDto);
        if(CollectionUtils.isEmpty(collectionInfos)){
            return CommonResponse.<List<ListProductDto>>builder().data(null).build();
        }
        List<String> productIds = collectionInfos.stream().map(CollectionInfo::getResourceId).collect(Collectors.toList());
        List<ListProductDto> productDtos =  productService.getProductInByIds(productIds);
        Map<String,Integer> numMap = new HashMap<>();
        for (CollectionInfo collectionInfo : collectionInfos) {
            numMap.put(collectionInfo.getResourceId(),collectionInfo.getNum());
        }
        for (ListProductDto productDto : productDtos) {
            productDto.setCollectNum(numMap.get(productDto.getProductId()));
        }
        return CommonResponse.<List<ListProductDto>>builder().data(productDtos).build();

    }

}
