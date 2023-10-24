package com.rent.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Component("BaseRedisUtil")
@Slf4j
public class RedisUtil {

    private final static String LOCK_KEY_PREFIX = "reids:lock:";

    private final static String LOCK_VALUE = "lock";

    static final ThreadLocal<String> sThreadLocal = new ThreadLocal<String>();

    private static RedisTemplate<String, Object> redisTemplate;



    @Autowired
    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        RedisUtil.redisTemplate = redisTemplate;
    }

    /**
     * redis分布式锁
     *
     * @param key      键名
     * @param lockTime 锁的时间 秒
     * @return true 原值不存 false key存在
     */
    public static boolean tryLock(String key, long lockTime) {
        String lockValue = RandomUtil.randomString(8);
        sThreadLocal.set(lockValue);
        log.info("加redis锁：{}", key);
        String lockKey = LOCK_KEY_PREFIX + key;
        boolean isLock = (Boolean) redisTemplate.execute(
                (RedisCallback<Object>) connection -> connection.setNX(lockKey.getBytes(), lockValue.getBytes()));
        if (isLock) {
            expire(lockKey, lockTime);
            log.info("加redis锁：[{}]成功,超时时间{}s", key, lockTime);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 释放锁
     *
     * @param key
     */
    public static void unLock(String key) {
        if (StringUtils.isNotBlank(key)) {
            String lockKey = LOCK_KEY_PREFIX + key;
            Object value = redisTemplate.opsForValue()
                .get(lockKey);
            if ((value != null) && value.toString()
                .equals(sThreadLocal.get())) {
                redisTemplate.execute((RedisCallback<Object>) connection -> connection.del(lockKey.getBytes()));
                log.info("释放redis锁：{}成功", key);
            } else {
                log.info("非当前线程锁，释放redis锁：{}失败", key);
            }
        }
    }

    /**
     * redis分布式锁
     *
     * @param key      键名
     * @param lockTime 锁的时间 秒
     * @return true 原值不存 false key存在
     */
    public static boolean setNx(String key, long lockTime) {
        String lockKey = LOCK_KEY_PREFIX + key;
        boolean isLock = (Boolean) redisTemplate.execute(
                (RedisCallback<Object>) connection -> connection.setNX(lockKey.getBytes(), LOCK_VALUE.getBytes()));
        if (isLock) {
            expire(lockKey, lockTime);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 制定緩存失效時間
     *
     * @param key
     * @param time
     * @return
     */
    public static boolean expire(String key, long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 制定緩存失效時間-批量
     *
     * @param key
     * @param time
     * @return
     */
    public static boolean expirebatch(String key, long time) {
        try {
            if (time > 0) {
                Set<String> keys = keys(key+"*");
                log.info("【批量失效】key:{}",key);
                if (null != keys ){
                    for(String value: keys){
                        redisTemplate.expire(value, time, TimeUnit.SECONDS);
                    }
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据key获取过期时间
     *
     * @param key
     * @return
     */
    public static long getExpire(String key) {
        return redisTemplate.getExpire(key);
    }

    /**
     * 判断key是否存在
     *
     * @param key
     * @return
     */
    public static boolean hasKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除缓存
     *
     * @param key 可以传一个值 或多个
     */
    @SuppressWarnings("unchecked")
    public static void del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
    }

    public static void del(Collection<String> key) {
        redisTemplate.delete(key);
    }

    //============================String=============================

    /**
     * 模糊查询key
     *
     * @param @param key
     * @return Set<String> key的集合
     * @Title: keys
     */
    public static Set<String> keys(String key) {
        return redisTemplate.execute((RedisCallback<Set<String>>) connection -> {
            Set<String> keysTmp = new HashSet<>();
            try (Cursor<byte[]> cursor = connection.scan(new ScanOptions.ScanOptionsBuilder().match(key).count(10000).build())) {
                while (cursor.hasNext()) {
                    keysTmp.add(new String(cursor.next(), "Utf-8"));
                }
                //关闭scan
                cursor.close();
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                throw new RuntimeException(e);
            }
            return keysTmp;
        });
    }



    /**
     * 普通缓存获取
     *
     * @param key 键
     * @return 值
     */
    public static Object get(String key) {
        return key == null ? null : redisTemplate.opsForValue()
                .get(key);
    }

    public static Integer getInt(String key) {
        Object obj = get(key);
        if (Objects.isNull(obj)) {
            return 0;
        }

        String strVale = String.valueOf(obj);
        int intVale = 0;
        try {
            intVale = Integer.parseInt(strVale);
        } catch (Exception e) {

        }
        return intVale;

    }

    /**
     * 普通缓存多个获取
     *
     * @param key String类型，以","号分割的多个键
     * @return Object   值
     * @Title: mGet
     */
    public static Object mGet(String key) {
        if (null == key || key.isEmpty()) {
            return null;
        }
        String[] keys = key.split(",");
        return mGet(keys);
    }

    /**
     * 普通缓存多个获取
     *
     * @return Object   值
     * @Title: mGet
     */
    public static Object mGet(String[] keys) {
        if (null == keys || 0 == keys.length) {
            return null;
        }
        return mGet(Arrays.asList(keys));
    }

    /**
     * 普通缓存多个获取
     *
     * @return Object   值
     * @Title: mGet
     */
    public static Object mGet(List<String> keys) {
        if (null == keys || 0 == keys.size()) {
            return null;
        }
        return redisTemplate.opsForValue()
                .multiGet(keys);
    }

    /**
     * 普通缓存放入
     *
     * @param key   键
     * @param value 值
     * @return true成功 false失败
     */
    public static boolean set(String key, Object value) {
        try {
            redisTemplate.opsForValue()
                    .set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * 普通缓存放入并设置时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    public static boolean set(String key, Object value, long time) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue()
                        .set(key, value, time, TimeUnit.SECONDS);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 普通缓存多个放入
     *
     * @param @param map 多个键值对
     * @return true成功 false 失败
     */
    public static boolean mSet(Map<String, Object> map) {
        try {
            redisTemplate.opsForValue()
                    .multiSet(map);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 递增
     *
     * @param key 键
     * @return
     */
    public static long incr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递增因子必须大于0");
        }
        return redisTemplate.opsForValue()
                .increment(key, delta);
    }

    /**
     * 递减
     *
     * @param key 键
     * @return
     */
    public static long decr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递减因子必须大于0");
        }
        return redisTemplate.opsForValue()
                .increment(key, -delta);
    }

    //================================Map=================================

    /**
     * HashGet
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return 值
     */
    public static Object hget(String key, String item) {
        return redisTemplate.opsForHash()
                .get(key, item);
    }

    /**
     * 获取hashKey对应的所有键值
     *
     * @param key 键
     * @return 对应的多个键值
     */
    public static Map<Object, Object> hmget(String key) {
        return redisTemplate.opsForHash()
                .entries(key);
    }

    /**
     * HashSet
     *
     * @param key 键
     * @param map 对应多个键值
     * @return true 成功 false 失败
     */
    public static boolean hmset(String key, Map<String, Object> map) {
        try {
            redisTemplate.opsForHash()
                    .putAll(key, map);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * HashSet 并设置时间
     *
     * @param key  键
     * @param map  对应多个键值
     * @param time 时间(秒)
     * @return true成功 false失败
     */
    public static boolean hmset(String key, Map<String, Object> map, long time) {
        try {
            redisTemplate.opsForHash()
                    .putAll(key, map);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @return true 成功 false失败
     */
    public static boolean hset(String key, String item, Object value) {
        try {
            redisTemplate.opsForHash()
                    .put(key, item, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建,如果item存在就不放入数据
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @return true 成功 false失败
     */
    public static boolean hsetnx(String key, String item, Object value) {
        try {
            return redisTemplate.opsForHash().putIfAbsent(key, item, value);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @param time  时间(秒)  注意:如果已存在的hash表有时间,这里将会替换原有的时间
     * @return true 成功 false失败
     */
    public static boolean hset(String key, String item, Object value, long time) {
        try {
            redisTemplate.opsForHash()
                    .put(key, item, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除hash表中的值
     *
     * @param key  键 不能为null
     * @param item 项 可以使多个 不能为null
     */
    public static void hdel(String key, Object... item) {
        redisTemplate.opsForHash()
                .delete(key, item);
    }

    /**
     * 判断hash表中是否有该项的值
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return true 存在 false不存在
     */
    public static boolean hHasKey(String key, String item) {
        return redisTemplate.opsForHash()
                .hasKey(key, item);
    }

    /**
     * hash递增 如果不存在,就会创建一个 并把新增后的值返回
     *
     * @param key  键
     * @param item 项
     * @param by   要增加几(大于0)
     * @return
     */
    public static double hincr(String key, String item, double by) {
        return redisTemplate.opsForHash()
                .increment(key, item, by);
    }

    /**
     * hash递减
     *
     * @param key  键
     * @param item 项
     * @param by   要减少记(小于0)
     * @return
     */
    public static double hdecr(String key, String item, double by) {
        return redisTemplate.opsForHash()
                .increment(key, item, -by);
    }

    //============================set=============================

    /**
     * 根据key获取Set中的所有值
     *
     * @param key 键
     * @return
     */
    public static Set<Object> sGet(String key) {
        try {
            return redisTemplate.opsForSet()
                    .members(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据value从一个set中查询,是否存在
     *
     * @param key   键
     * @param value 值
     * @return true 存在 false不存在
     */
    public static boolean sHasKey(String key, Object value) {
        try {
            return redisTemplate.opsForSet()
                    .isMember(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将数据放入set缓存
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public static long sSet(String key, Object... values) {
        try {
            return redisTemplate.opsForSet()
                    .add(key, values);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 将set数据放入缓存
     *
     * @param key    键
     * @param time   时间(秒)
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public static long sSetAndTime(String key, long time, Object... values) {
        try {
            Long count = redisTemplate.opsForSet()
                    .add(key, values);
            if (time > 0)
                expire(key, time);
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取set缓存的长度
     *
     * @param key 键
     * @return
     */
    public static long sGetSetSize(String key) {
        try {
            return redisTemplate.opsForSet()
                    .size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 移除值为value的
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 移除的个数
     */
    public static long setRemove(String key, Object... values) {
        try {
            Long count = redisTemplate.opsForSet()
                    .remove(key, values);
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    //===============================list=================================

    /**
     * 获取list缓存的内容
     *
     * @param key   键
     * @param start 开始
     * @param end   结束  0 到 -1代表所有值
     * @return
     */
    public static List<Object> lGet(String key, long start, long end) {
        try {
            return redisTemplate.opsForList()
                    .range(key, start, end);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取list缓存的长度
     *
     * @param key 键
     * @return
     */
    public static long lGetListSize(String key) {
        try {
            return redisTemplate.opsForList()
                    .size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 通过索引 获取list中的值
     *
     * @param key   键
     * @param index 索引  index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     * @return
     */
    public static Object lGetIndex(String key, long index) {
        try {
            return redisTemplate.opsForList()
                    .index(key, index);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @return
     */
    public static boolean lSet(String key, Object value) {
        try {
            redisTemplate.opsForList()
                    .rightPush(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return
     */
    public static boolean lSet(String key, Object value, long time) {
        try {
            redisTemplate.opsForList()
                    .rightPush(key, value);
            if (time > 0)
                expire(key, time);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将list放入缓存  放到最左边
     *
     * @param key   键
     * @param value 值
     * @return
     */
    public static boolean llSet(String key, Object value) {
        try {
            redisTemplate.opsForList().leftPush(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @return
     */
    public static boolean lSet(String key, List<Object> value) {
        try {
            redisTemplate.opsForList()
                    .rightPushAll(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取数组最右边元素,并放入最左边
     * @param key
     * @return
     */
    public static Object loop(String key) {
        try {
            Object object = redisTemplate.opsForList().rightPopAndLeftPush(key, key);
            return object;
        } catch (Exception e){
            return null;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return
     */
    public static boolean lSet(String key, List<Object> value, long time) {
        try {
            redisTemplate.opsForList()
                    .rightPushAll(key, value);
            if (time > 0)
                expire(key, time);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据索引修改list中的某条数据
     *
     * @param key   键
     * @param index 索引
     * @param value 值
     * @return
     */
    public static boolean lUpdateIndex(String key, long index, Object value) {
        try {
            redisTemplate.opsForList()
                    .set(key, index, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 移除N个值为value
     *
     * @param key   键
     * @param count 移除多少个
     * @param value 值
     * @return 移除的个数
     */
    public static long lRemove(String key, long count, Object value) {
        try {
            Long remove = redisTemplate.opsForList()
                    .remove(key, count, value);
            return remove;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 移除范围外的内容
     *
     * @param key   键
     * @param start 移除多少个
     * @param end   值
     * @return 移除的个数
     */
    public static void ltrim(String key, long start, long end) {
        try {
            redisTemplate.opsForList().trim(key, start, end);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void zAdd(String key, String value, double score) {
        try {
            redisTemplate.opsForZSet().add(key,value,score);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Long zReverseRank(String key, String value) {
        try {
            return redisTemplate.opsForZSet().reverseRank(key,value);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
