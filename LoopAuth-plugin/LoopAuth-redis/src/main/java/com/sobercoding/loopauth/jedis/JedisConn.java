package com.sobercoding.loopauth.jedis;

import com.sobercoding.loopauth.exception.LoopAuthDaoException;
import com.sobercoding.loopauth.exception.LoopAuthExceptionEnum;
import com.sobercoding.loopauth.session.SessionStrategy;
import com.sobercoding.loopauth.session.config.RedisConfig;
import com.sobercoding.loopauth.util.LoopAuthUtil;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.*;
import redis.clients.jedis.DefaultJedisClientConfig.Builder;

/**
 * Redis 连接
 * @author: Weny
 */
public class JedisConn {

    private volatile JedisPool jedisPool;
    private volatile Jedis jedisConn;

    private final RedisConfig redisConfig = SessionStrategy.getRedisConfig();

    private final String HOST = redisConfig.getHost();
    private final String PASSWORD = redisConfig.getPassword();
    private final int PORT = redisConfig.getPort();
    private final int DATABASE_NO = redisConfig.getDatabaseNo();
    private final int TIMEOUT = redisConfig.getTimeOut();
    private final int MAX_TOTAL = redisConfig.getMaxTotal();
    private final int MAX_IDLE = redisConfig.getMaxIdle();
    private final int MIN_IDLE = redisConfig.getMinIdle();
    private final boolean IS_NEED_POOL = redisConfig.isNeedPool();

    /**
     * 获取 Redis 客户端
     * 如果 {@link JedisConn#IS_NEED_POOL} 为 true 则通过连接池返回连接
     *
     * @return Jedis
     */
    public Jedis getJedis() {
        try {
            return IS_NEED_POOL ? genPoolConn() : genSingleConn();
        } catch (Exception e) {
            throw new LoopAuthDaoException(LoopAuthExceptionEnum.DATA_REDIS_LINK);
        }
    }

    /**
     * 生成连接池连接
     * 通过方法内局部变量的定义,优化系统性能(减少了主内存与线程本地内存的数据交换次数)
     */
    private Jedis genPoolConn() {
        JedisPool pool = jedisPool;
        if (pool == null) {
            synchronized (JedisConn.class) {
                pool = jedisPool;
                if (pool == null) {
                    GenericObjectPoolConfig<Jedis> genericObjectPoolConfig = new GenericObjectPoolConfig<>();
                    genericObjectPoolConfig.setMaxTotal(MAX_TOTAL);
                    genericObjectPoolConfig.setMaxIdle(MAX_IDLE);
                    genericObjectPoolConfig.setMinIdle(MIN_IDLE);
                    // 密码不为空时候设置密码
                    if (LoopAuthUtil.isNotEmpty(PASSWORD)) {
                        pool = jedisPool = new JedisPool(genericObjectPoolConfig, HOST, PORT, TIMEOUT, PASSWORD, DATABASE_NO);
                    } else {
                        pool = jedisPool = new JedisPool(genericObjectPoolConfig, HOST, PORT, TIMEOUT, null, DATABASE_NO);
                    }
                }
            }
        }
        return pool.getResource();
    }

    /**
     * 生成单连接
     */
    private Jedis genSingleConn() {
        if (jedisConn == null) {
            Builder config = DefaultJedisClientConfig.builder()
                    .database(DATABASE_NO)
                    .socketTimeoutMillis(TIMEOUT)
                    .connectionTimeoutMillis(TIMEOUT);
            // 密码不为空时候设置密码
            if (PASSWORD != null && !"".equals(PASSWORD)) {
                config.password(PASSWORD);
            }
            JedisClientConfig jedisClientConfig = config.build();
            jedisConn = new Jedis(new HostAndPort(HOST, PORT), jedisClientConfig);
        }
        return jedisConn;
    }

    /**
     * 关闭连接
     */
    public void close() {
        if (jedisConn != null) {
            jedisConn.close();
        }
        if (jedisPool != null) {
            jedisPool.close();
        }
    }
}

