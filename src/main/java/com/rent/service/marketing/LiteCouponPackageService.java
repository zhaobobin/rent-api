        
package com.rent.service.marketing;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.dto.marketing.CouponPackageDto;
import com.rent.common.dto.marketing.CouponPackageReqDto;


/**
 * 优惠券大礼包Service
 *
 * @author zhao
 * @Date 2020-07-07 15:04
 */
public interface LiteCouponPackageService {

        /**
         * 新增优惠券大礼包
         * @param request 条件
         * @return boolean
         */
        Long addCouponPackage(CouponPackageDto request);

        /**
         * 根据ID 获取编辑优惠券页面 的数据
         * @param id
         * @return
         */
        CouponPackageDto updateCouponPackagePageData(Long id);

        /**
         * 根据 ID 修改优惠券大礼包
         * @param request 条件
         * @return String
         */
        Boolean modifyCouponPackage(CouponPackageDto request);


        /**
         * 根据条件列表
         * @param request 实体对象
         * @return CouponPackage
         */
        Page<CouponPackageDto> queryCouponPackagePage(CouponPackageReqDto request);


        /**
         * 删除一个大礼包
         * @param id
         * @return
         */
        Boolean deleteCouponPackage(Long id);


}