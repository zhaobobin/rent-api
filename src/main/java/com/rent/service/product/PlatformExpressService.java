        
package com.rent.service.product;

import com.rent.common.dto.product.PlatformExpressDto;
import com.rent.common.dto.vo.ApiPlatformExpressVo;
import com.rent.model.product.PlatformExpress;

import java.util.List;

/**
 * 平台物流表Service
 *
 * @author youruo
 * @Date 2020-06-16 11:46
 */
public interface PlatformExpressService {


        PlatformExpress queryPlatformExpressDetailById(Long id);

        /**
         * 获取物流集合信息
          * @return
         */
        List<PlatformExpressDto> selectExpressList();

        /**
         * 小程序查询物流公司
         * @return
         */
        List<ApiPlatformExpressVo> selectExpressListForApi();
}