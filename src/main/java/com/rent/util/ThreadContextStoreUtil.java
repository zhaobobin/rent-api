package com.rent.util;

import java.util.Map;


/**
 * 线程上下文工具类
 * @Author xiaoyao
 * @Date 21:32 2020-4-19
 * @Param
 * @return
 **/
public final class ThreadContextStoreUtil extends AbstractThreadContext {

    private static ThreadLocal<Map<String, Object>> threadContext = new ThreadLocal<>();

    private static volatile ThreadContextStoreUtil app = null;

    private ThreadContextStoreUtil() {}

    /**
     * 获取实例
     *
     * @return 实例
     */
    public static ThreadContextStoreUtil getInstance() {
        if (app == null) {
            synchronized (ThreadContextStoreUtil.class) {
                if (app == null) {
                    app = new ThreadContextStoreUtil();
                }
            }
        }
        return app;
    }

    @Override
    protected ThreadLocal<Map<String, Object>> getThreadContext() {
        return threadContext;
    }

}
