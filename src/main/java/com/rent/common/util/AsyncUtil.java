package com.rent.common.util;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;

/**
 * 异步任务
 *
 * @Author xiaoyao
 * @Date 17:32 2020-4-30
 * @Since v1.0
 * @return
 **/
@Slf4j
public class AsyncUtil {

    /**
     * 异步线程调用模板代码
     *
     * @param runnable
     * @return void
     */
    public static void runAsync(Runnable runnable) {
        CompletableFuture.runAsync(() -> {
            try {
                runnable.run();

            } catch (Exception e) {
                log.error("【异步调用异常】", e);
            }
        });
    }

    /**
     * 异步线程调用模板代码
     *
     * @param runnable
     * @return void
     */
    public static void runAsync(Runnable runnable, String taskName) {
        log.info("创建:[{}]异步任务", taskName);
        CompletableFuture.runAsync(() -> {
            try {
                runnable.run();
            } catch (Exception e) {
                log.error("【异步调用异常】taskName=" + taskName, e);
            }
        });
    }

}
