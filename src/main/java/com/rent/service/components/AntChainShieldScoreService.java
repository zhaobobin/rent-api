package com.rent.service.components;


import com.rent.common.dto.components.dto.QueryAntChainShieldRequest;

/**
 * 蚁盾分
 * @author zhaowenchao
 */
public interface AntChainShieldScoreService {

    /**
     * 查询蚁盾分
     * @param request
     * @return
     */
    String queryAntChainShieldScore(QueryAntChainShieldRequest request);


}
