
package com.rent.service.product.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rent.common.converter.product.PlatformExpressConverter;
import com.rent.common.dto.product.PlatformExpressDto;
import com.rent.common.dto.vo.ApiPlatformExpressVo;
import com.rent.dao.product.PlatformExpressDao;
import com.rent.model.product.PlatformExpress;
import com.rent.service.product.PlatformExpressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 平台物流表Service
 *
 * @author youruo
 * @Date 2020-06-16 11:46
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class PlatformExpressServiceImpl implements PlatformExpressService {

    private final PlatformExpressDao platformExpressDao;

    @Override
    public PlatformExpress queryPlatformExpressDetailById(Long id) {
        return platformExpressDao.getById(id);
    }


    @Override
    public List<PlatformExpressDto> selectExpressList() {
        List<PlatformExpress> platformExpress = platformExpressDao.list(new QueryWrapper<PlatformExpress>()
                .select("id", "create_time", "update_time", "delete_time", "name", "logo", "short_name", "telephone", "`index`", "ali_code"));
        return PlatformExpressConverter.modelList2DtoList(platformExpress);
    }

    @Override
    public List<ApiPlatformExpressVo> selectExpressListForApi() {
        List<PlatformExpress> platformExpress = platformExpressDao.list(new QueryWrapper<PlatformExpress>().select("id", "name").orderByAsc("id"));
        return PlatformExpressConverter.modelList2ApiVo(platformExpress);
    }
}