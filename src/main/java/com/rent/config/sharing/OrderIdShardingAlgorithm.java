package com.rent.config.sharing;

import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @author zhaowenchao
 */
@Slf4j
@Component
public class OrderIdShardingAlgorithm implements PreciseShardingAlgorithm<String> {

    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<String> preciseShardingValue) {

        String orderId = preciseShardingValue.getValue();
        String suffix = orderId.substring(orderId.length()-1);
        // 需要分库的逻辑表
        String table = preciseShardingValue.getLogicTableName();
        table = table+"_"+suffix;
        return table;
    }
}
