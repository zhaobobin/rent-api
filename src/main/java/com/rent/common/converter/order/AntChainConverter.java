package com.rent.common.converter.order;


import com.rent.common.dto.order.AntChainDto;
import com.rent.model.order.AntChainStep;


/**
 * @author zhaowenchao
 */
public class AntChainConverter {

    /**
     *
     * @param model
     * @return
     */
    public static AntChainDto model2Dto(AntChainStep model) {
        AntChainDto dto = new AntChainDto();
        if (model == null) {
            dto.setShieldScore(null);
            dto.setSyncToChain(false);
            dto.setInsure(false);
        }else {
            dto.setShieldScore(model.getShieldScore());
            dto.setSyncToChain(model.getSyncToChain());
            dto.setInsure(model.getInsure());
        }
        return dto;
    }

}