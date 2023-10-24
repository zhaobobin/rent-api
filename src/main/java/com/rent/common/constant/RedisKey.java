package com.rent.common.constant;

/**
 * @author zhaowenchao
 */
public class RedisKey {

    /**
     * 简版小程序推荐商品
     */
    public static final String LITE_PRODUCT_RECOMMEND = "product:recommendLite:";


    /**
     * 小程序商家及商品数据
     */
    public static final String ALIPAY_SHOP_PRODUCTS_V1 = "shop:shopv1_products:";


    /**
     * nsf等级
     */
    public static final String ORDER_NSF = "order:nsf:";
    /**
     * 营销反作弊等级
     */
    public static final String ORDER_ANTI_CHEATING = "order:anti:cheating:";


    /**
     * 订单操作锁
     */
    public static final String ORDER_BACKSTAGE_OPERATOR_LOCK = "order:backstage:operator:lock:";


    /**
     * 导出中心导出历史缓存前缀
     */
    public static final String EXPORT_HISTORY_PREFIX = "export:file:";


    /**
     * 导出中心导出历史缓存前缀
     */
    public static final String EXPORT_HISTORY_KEY = "export:history:";


    public static final String CHANNEL_USER_IN_7_DAYS = "user:channel:marketing:";

    public static final String ORDER_CHANGE_PRICE_LOCK_KEY = "order:price:operator:lock:";

}
