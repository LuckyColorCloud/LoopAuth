package com.sobercoding.loopauth.jedis;

import com.sobercoding.loopauth.LoopAuthStrategy;
import com.sobercoding.loopauth.config.LoopAuthConfig;
import com.sobercoding.loopauth.dao.LoopAuthDao;
import com.sobercoding.loopauth.exception.LoopAuthDaoException;
import com.sobercoding.loopauth.exception.LoopAuthExceptionEnum;
import com.sobercoding.loopauth.model.TokenModel;
import com.sobercoding.loopauth.util.JsonUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;

import java.util.Objects;
import java.util.Set;

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
public class JedisDaoImpl{

    public Jedis redisConn = JedisConn.getJedis();
    private static final String tokenPersistencePrefix;
    private static final String loginIdPersistencePrefix;

    static {
        LoopAuthConfig loopAuthConfig = LoopAuthStrategy.getLoopAuthConfig();
        tokenPersistencePrefix = loopAuthConfig.getTokenPersistencePrefix();
        loginIdPersistencePrefix = loopAuthConfig.getLoginIdPersistencePrefix();
    }

    /**
     * 存入缓存
     * value 需要根据 Config 设置的 tokenPersistencePrefix 及 loginIdPersistencePrefix 转换
     * key 前几个字符如果为 tokenPersistencePrefix 设置的值则 TokenModel 转 String
     * key 前几个字符如果为 loginIdPersistencePrefix 设置的值则 TokenModel 转 Set<String>
     * 有效时间参数在TokenModel里面
     *
     * @param value 值
     *              操作失败抛出异常  异常请参考exception目录其他异常 新建一个DaoException异常类，并在Enum枚举类新增  持久层操作失败的异常code msg
     */

    public void set(String key, Object value) {
        Long timeOut = null;
        String json = null;
        if (tokenPersistencePrefix.equals(key.substring(0, tokenPersistencePrefix.length()))) {
            TokenModel tokenModel = (TokenModel) value;
            timeOut = tokenModel.getTimeOut();
            json = JsonUtil.objToJson(tokenModel);
        }
        if (loginIdPersistencePrefix.equals(key.substring(0, loginIdPersistencePrefix.length()))) {
            Set<String> strings = (Set<String>) value;
            json = JsonUtil.objToJson(strings);
        }
        LoopAuthDaoException.isEmpty(json, LoopAuthExceptionEnum.DATA_EXCEPTION);
        String res = timeOut != null ?  set(key, json, timeOut / 1000) : set(key, json);
        LoopAuthDaoException.isOK(res, LoopAuthExceptionEnum.CACHE_FAILED);
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

    public Object get(String key) {
        return this.redisConn.get(key);
    }

    /**
     * 判断key是否存在
     *
     * @param key Redis中的键
     * @return
     */

    public boolean containsKey(String key) {
        return this.redisConn.exists(key);
    }

    /**
     * 获取Redis中的键
     *
     * @param key 操作失败抛出异常  异常请参考exception目录其他异常 新建一个DaoException异常类，并在Enum枚举类新增  持久层操作失败的异常code msg
     */
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
     * @param seconds   过期时间（分钟）
     * @return 操作成功则返回 OK
     */
    public String set(String key, String value, long seconds) {
        seconds = seconds == 0 ? 60 : seconds; // 如果是0秒，就改为60秒
        return this.redisConn.set(key, value, SetParams.setParams().ex(seconds));
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
