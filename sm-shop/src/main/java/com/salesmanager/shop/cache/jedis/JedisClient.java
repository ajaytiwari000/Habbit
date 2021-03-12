package com.salesmanager.shop.cache.jedis;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Component("jedisClient")
public class JedisClient {

  @Value("${redis.url}")
  private static String redisUrl;

  @Value("${redis.url}")
  public void setRedisUrl(String url) {
    redisUrl = url;
  }

  @Value("${redis.ssl}")
  private static String redisSsl;

  @Value("${redis.ssl}")
  public void setSsl(String ssl) {
    redisSsl = ssl;
  }

  private static final long serialVersionUID = 1230456009807L;
  private static JedisPool jedisPool = null;
  //  final JedisPoolConfig poolConfig = buildPoolConfig();
  //  JedisPool jedisPool =
  //      new JedisPool(
  //          poolConfig,
  //          redisUrl, // "master.habbit-redis-prod.xbxctz.aps1.cache.amazonaws.com"
  //          6379,
  //          2000,
  //          Boolean.parseBoolean(redisSsl));
  @PostConstruct
  public void init() {
    JedisPoolConfig poolConfig = new JedisPoolConfig();
    poolConfig.setMaxTotal(200);
    poolConfig.setMaxIdle(40);
    poolConfig.setMinIdle(20);
    poolConfig.setMaxWaitMillis(500);
    if (jedisPool == null) {
      jedisPool =
          new JedisPool(
              poolConfig,
              redisUrl, // "master.habbit-redis-prod.xbxctz.aps1.cache.amazonaws.com"
              6379,
              2000,
              Boolean.parseBoolean(redisSsl));
    }
  }

  //  private JedisPoolConfig buildPoolConfig() {
  //    final JedisPoolConfig poolConfig = new JedisPoolConfig();
  //    poolConfig.setMaxTotal(200);
  //    poolConfig.setMaxIdle(40);
  //    poolConfig.setMinIdle(20);
  //    poolConfig.setMaxWaitMillis(500);
  //    return poolConfig;
  //  }

  public JedisPool getJedisPool() {
    return jedisPool;
  }
}
