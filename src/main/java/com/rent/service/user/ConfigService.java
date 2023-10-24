        
package com.rent.service.user;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.dto.user.ConfigDto;
import com.rent.common.dto.user.ConfigReqDto;


/**
 * 配置信息Service
 *
 * @author zhao
 * @Date 2020-11-11 16:52
 */
public interface ConfigService {


        /**
         * 根据 ID 修改配置信息
         *
         * @param request 条件
         * @return String
         */
        Boolean modifyConfig(ConfigDto request);

        /**
         * <p>
         * 根据条件列表
         * </p>
         *
         * @param request 实体对象
         * @return Config
         */
        Page<ConfigDto> queryConfigPage(ConfigReqDto request);


        /**
         * 根据code获取bvalue值
         * @param code
         * @return
         */
        String getConfigByCode(String code);
}