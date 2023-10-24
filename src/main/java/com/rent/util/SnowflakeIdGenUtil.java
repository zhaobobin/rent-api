package com.rent.util;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.NetUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author zhaowenchao
 */
@Slf4j
@Component
public final class SnowflakeIdGenUtil {

    private static long workerId = 0;
    private static long datacenterId = 1;
    private static Snowflake snowflake = IdUtil.createSnowflake(workerId, datacenterId);

    @PostConstruct
    public void init() {
        try {
            workerId = NetUtil.ipv4ToLong(NetUtil.getLocalhostStr());
        } catch (Exception e) {
            log.info("当前机器的workerId获取失败", e);
            workerId = NetUtil.getLocalhostStr().hashCode();
        }
    }

    public static synchronized long snowflakeId() {
        return snowflake.nextId();
    }
}
