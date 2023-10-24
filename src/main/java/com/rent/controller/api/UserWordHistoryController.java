        
package com.rent.controller.api;

import com.rent.common.dto.CommonResponse;
import com.rent.common.dto.user.UserWordHistoryDto;
import com.rent.common.dto.vo.SearchPageVo;
import com.rent.service.marketing.HotSearchConfigService;
import com.rent.service.user.UserWordHistoryService;
import com.rent.util.AppParamUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 用户搜索记录控制器
 *
 * @author zhao
 * @Date 2020-06-18 15:41
 */
@Tag(name = "小程序搜索词")
@RestController
@RequiredArgsConstructor
@RequestMapping("/zyj-api-web/hzsx/userWordHistory")
public class UserWordHistoryController {

    private final UserWordHistoryService userWordHistoryService;
    private final HotSearchConfigService hotSearchConfigService;

    @Operation(summary = "查询搜索页面数据-热搜和用户搜索历史")
    @GetMapping("/searchPage")
    public CommonResponse<SearchPageVo> searchPage(@Parameter(name = "uid",description = "用户ID") @RequestParam(value = "uid",required = false) String uid) {
        SearchPageVo vo = new SearchPageVo();
        vo.setHistory(Collections.EMPTY_LIST);
        if(StringUtils.isNotEmpty(uid)){
            List<UserWordHistoryDto> userWordHistory = userWordHistoryService.getUserWordHistory(uid);
            if(CollectionUtils.isNotEmpty(userWordHistory)){
                vo.setHistory(userWordHistory.stream().map(UserWordHistoryDto::getWord).collect(Collectors.toList()));
            }
        }
        vo.setHotSearchList(hotSearchConfigService.list(AppParamUtil.getChannelId()));
        return CommonResponse.<SearchPageVo>builder().data(vo).build();
    }

    @Operation(summary = "清空用户搜索记录")
    @GetMapping("/deleteUserWordHistory")
    public CommonResponse<Boolean> deleteUserWordHistory(
            @Parameter(name = "uid",description = "用户ID",required = true) @RequestParam("uid") String uid){
        return CommonResponse.<Boolean>builder().data(userWordHistoryService.deleteUserWordHistory(uid)).build();
    }

}
