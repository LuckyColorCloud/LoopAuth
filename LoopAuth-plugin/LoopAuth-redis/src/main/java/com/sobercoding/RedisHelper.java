package com.sobercoding;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;

/**
 * <p>
 * Description: Redis 操作助手
 * </p>
 *
 * @author: Weny
 * @date: 2022/8/13
 * @see: com.sobercoding
 * @version: v1.0.0
 */
public class RedisHelper {

    private static Jedis redisConn;

    /**
     * 构造函数获取redis连接
     */
    public RedisHelper() {
        if (redisConn == null) {
            redisConn = RedisConn.getJedis();
        }
    }

    /**
     * 设置字符串值
     *
     * @param key   Redis键
     * @param value 待设置的值
     * @return 操作成功则返回 OK
     */
    public String setJson(String key, String value) {
        return redisConn.set(key, value);
    }

    /**
     * 设置字符串值
     *
     * @param key   Redis键
     * @param value 待设置的值
     * @param min   过期时间（分钟）
     * @return 操作成功则返回 OK
     */
    public String setJson(String key, String value, long min) {
        min = min == 0 ? 1 : min;
        return redisConn.set(key, value, SetParams.setParams().ex(min * 60));
    }

    /**
     * 获取字符串值
     *
     * @param key Redis键
     * @return value
     */
    public String getJson(String key) {
        return redisConn.get(key);
    }

    /**
     * 获取Redis中的键
     *
     * @param key
     * @return 操作成功返回 true
     */
    public boolean delete(String key) {
        long rs = redisConn.del(key);
        return rs == 1;
    }

    /**
     * 设置过期时间
     *
     * @param key    Redis键
     * @param minute 过期时间（分钟）
     * @return 操作成功返回 1
     */
    public long expire(String key, long minute) {
        return redisConn.expire(key, minute * 60);
    }

    /**
     * 判断key是否存在
     *
     * @param key Redis中的键
     * @return
     */
    public boolean hasKey(String key) {
        return redisConn.exists(key);
    }
}
