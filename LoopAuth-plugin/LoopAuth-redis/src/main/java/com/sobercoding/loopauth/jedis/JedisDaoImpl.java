package com.sobercoding.loopauth.jedis;

import com.sobercoding.loopauth.dao.LoopAuthDao;
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
public class JedisDaoImpl implements LoopAuthDao {

    public Jedis redisConn = JedisConn.getJedis();

    /**
     * 存入缓存
     * value 需要根据 Config 设置的 tokenPersistencePrefix 及 loginIdPersistencePrefix 转换
     * key 前几个字符如果为 tokenPersistencePrefix 设置的值则 TokenModel 转 String
     * key 前几个字符如果为 loginIdPersistencePrefix 设置的值则 TokenModel 转 Set<String>
     * 有效时间参数在TokenModel里面
     * @param value 值
     * 操作失败抛出异常  异常请参考exception目录其他异常 新建一个DaoException异常类，并在Enum枚举类新增  持久层操作失败的异常code msg
     */
    @Override
    public void set(String key, Object value) {

    }

    /**
     * 获取缓存值
     * value 需要根据 Config 设置的 tokenPersistencePrefix 及 loginIdPersistencePrefix 转换
     * key 前几个字符如果为 tokenPersistencePrefix设置的值则 转 TokenModel
     * key 前几个字符如果为 loginIdPersistencePrefix设置的值则 转 Set<String>
     *
     * @param key Redis键
     * @return value
     */
    @Override
    public Object get(String key) {
        return this.redisConn.get(key);
    }

    /**
     * 判断key是否存在
     *
     * @param key Redis中的键
     * @return
     */
    @Override
    public boolean containsKey(String key) {
        return this.redisConn.exists(key);
    }

    /**
     * 获取Redis中的键
     *
     * @param key
     * 操作失败抛出异常  异常请参考exception目录其他异常 新建一个DaoException异常类，并在Enum枚举类新增  持久层操作失败的异常code msg
     */
    @Override
    public void remove(String key) {
        long rs = this.redisConn.del(key);
//        return rs == 1;
    }

    /**
     * 设置字符串值
     *
     * @param key   Redis键
     * @param value 待设置的值
     * @return 操作成功则返回 OK
     */
    public String set(String key, String value) {
        return this.redisConn.set(key, value);
    }

    /**
     * 设置字符串值
     *
     * @param key   Redis键
     * @param value 待设置的值
     * @param min   过期时间（分钟）
     * @return 操作成功则返回 OK
     */
    public String set(String key, String value, long min) {
        min = min == 0 ? 1 : min;
        return this.redisConn.set(key, value, SetParams.setParams().ex(min * 60));
    }

    /**
     * 获取Redis中的键
     *
     * @param key
     * @return 操作成功返回 true
     */
    public boolean delete(String key) {
        long rs = this.redisConn.del(key);
        return rs == 1;
    }

    /**
     * 此方法不需要  缓存的有效期写入token的时候统一管理，token续期时也是重新生成token，token是唯一的。所以无需此方法
     * 设置过期时间
     *
     * @param key    Redis键
     * @param minute 过期时间（分钟）
     * @return 操作成功返回 1
     */
    public long expire(String key, long minute) {
        return this.redisConn.expire(key, minute * 60);
    }

    /**
     * 判断key是否存在
     *
     * @param key Redis中的键
     * @return
     */
    public boolean hasKey(String key) {
        return this.redisConn.exists(key);
    }
}
