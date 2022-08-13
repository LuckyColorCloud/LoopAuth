package com.sobercoding;


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
public class RedisConn {

    private static volatile JedisPool jedisPool;        // 连接池
    private static volatile Jedis jedisConn;            // 单链接
    private static String host = "hw.iscolt.com";       // Redis服务器地址
    private static String password = "5211";            // Redis 密码,没有设置可以为空
    private static int port = 6379;                     // Redis端口号， 默认是6379
    private static int databaseNo = 0;                  // Redis 默认链接的数据库
    private static int timeout = 2000;                  // 超时时间
    private static int maxTotal = 16;                   // 最大连接数
    private static int maxIdle = 8;                     // 最大空闲连接数
    private static int minIdle = 4;                     // 最小空闲连接数
    private static boolean needPool = true;             // 需要连接池

    /**
     * 获取 Redis 客户端
     * 如果 {@link RedisConn#needPool} 为 true 则通过连接池返回连接
     *
     * @return
     */
    public static Jedis getJedis() {
        try {
            return needPool ? genPoolConn() : genSingleConn();
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
            synchronized (RedisConn.class) { // 排它锁, 独占锁, 悲观锁
                pool = jedisPool;
                if (pool == null) {
                    GenericObjectPoolConfig config = new GenericObjectPoolConfig();
                    config.setMaxTotal(maxTotal);
                    config.setMaxIdle(maxIdle);
                    config.setMinIdle(minIdle);
                    // 密码不为空时候设置密码
                    if (password != null && !"".equals(password)) {
                        pool = jedisPool = new JedisPool(config, host, port, timeout, password, databaseNo);
                    } else {
                        // TODO 没有 JedisPool(config, host, port, timeout, database) 构造，pwd传入null未经过测试
                        pool = jedisPool = new JedisPool(config, host, port, timeout, null, databaseNo);
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
                    .database(databaseNo)
                    .timeoutMillis(timeout);
            // 密码不为空时候设置密码
            if (password != null && !"".equals(password)) {
                config.password(password);
            }
            JedisClientConfig jedisClientConfig = config.build();
            jedisConn = new Jedis(host, port, jedisClientConfig);
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

