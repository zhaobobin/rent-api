package com.rent.common.dto.product;

import lombok.Data;

import java.util.List;

/**
 * @author zhaowenchao
 */
@Data
public class SpiltBillConfigDto {

    private List<ShopSplitBillRuleDto> rules;

    private ShopSplitBillAccountDto account;


}
