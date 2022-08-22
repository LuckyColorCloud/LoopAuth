package com.sobercoding.loopauth.jedis;

import com.sobercoding.loopauth.LoopAuthStrategy;
import com.sobercoding.loopauth.config.RedisConfig;
import com.sobercoding.loopauth.util.LoopAuthUtil;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.DefaultJedisClientConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisClientConfig;
import redis.clients.jedis.DefaultJedisClientConfig.Builder;
import redis.clients.jedis.JedisPool;

/**
 * <p>
 * Description: Redis 连接
 * </p>
 *
 * @author: Weny
 * @date: 2022/8/13
 * @see: com.sobercoding
 * @version: v1.0.0
 */
public class JedisConn {

    private static volatile JedisPool jedisPool;
    private static volatile Jedis jedisConn;

    private static final String HOST;

    private static final String PASSWORD;
    private static final int PORT;
    private static final int DATABASE_NO;
    private static final int TIMEOUT;
    private static final int MAX_TOTAL;
    private static final int MAX_IDLE;
    private static final int MIN_IDLE;

    private static final boolean IS_NEED_POOL;

    static {
        RedisConfig redisConfig = LoopAuthStrategy.getLoopAuthConfig().getRedisConfig();
        HOST = redisConfig.getHost();
        PASSWORD = redisConfig.getPassword();
        PORT = redisConfig.getPort();
        DATABASE_NO = redisConfig.getDatabaseNo();
        TIMEOUT = redisConfig.getTimeOut();
        MAX_TOTAL = redisConfig.getMaxTotal();
        MAX_IDLE = redisConfig.getMaxIdle();
        MIN_IDLE = redisConfig.getMinIdle();
        IS_NEED_POOL = redisConfig.isNeedPool();
    }

    /**
     * 获取 Redis 客户端
     * 如果 {@link JedisConn#IS_NEED_POOL} 为 true 则通过连接池返回连接
     *
     * @return Jedis
     */
    public static Jedis getJedis() {
        try {
            return IS_NEED_POOL ? genPoolConn() : genSingleConn();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 生成连接池连接
     * 通过方法内局部变量的定义,优化系统性能(减少了主内存与线程本地内存的数据交换次数)
     *
     * @return
     */
    private static Jedis genPoolConn() {
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
                        // TODO 没有 JedisPool(config, host, port, timeout, database) 构造，pwd传入null未经过测试
                        pool = jedisPool = new JedisPool(genericObjectPoolConfig, HOST, PORT, TIMEOUT, null, DATABASE_NO);
                    }
                }
            }
        }
        return pool.getResource();
    }

    /**
     * 生成单连接
     *
     * @return
     */
    private static Jedis genSingleConn() {
        if (jedisConn == null) {
            Builder config = DefaultJedisClientConfig.builder()
                    .database(DATABASE_NO)
                    .timeoutMillis(TIMEOUT);
            // 密码不为空时候设置密码
            if (PASSWORD != null && !"".equals(PASSWORD)) {
                config.password(PASSWORD);
            }
            JedisClientConfig jedisClientConfig = config.build();
            jedisConn = new Jedis(HOST, PORT, jedisClientConfig);
        }
        return jedisConn;
    }

    /**
     * 关闭连接
     */
    public static void close() {
        if (jedisConn != null) {
            jedisConn.close();
        }
        if (jedisPool != null) {
            jedisPool.close();
        }
    }
}
