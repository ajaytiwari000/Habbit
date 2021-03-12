package com.salesmanager.shop.cache.dao;

import com.google.gson.Gson;
import com.salesmanager.shop.cache.jedis.JedisClient;
import java.time.LocalTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;

@Repository("cacheDao")
public class CacheDao {
  private static final Logger LOGGER = LoggerFactory.getLogger(CacheDao.class);

  // @Inject private Jedis jedis;
  private Gson gson = new Gson();

  public boolean setDataValueToRedis(String key, Object object, int expTime) {
    Jedis jedis = getJedisClient();
    try {
      jedis.setex(key, expTime, gson.toJson(object)); // second
    } catch (Exception e) {
      LOGGER.error(e.getMessage(), e);
    } finally {
      if (jedis != null) jedis.close();
    }
    return true;
  }

  public boolean setDataValueToRedis(String key, Object object, Long expTime) {
    System.out.println(" start jedis " + LocalTime.now());
    Jedis jedis = getJedisClient();
    jedis.ping();
    System.out.println(" end jedis " + LocalTime.now());
    try {
      System.out.println(" start jedis setting obj" + LocalTime.now());
      jedis.psetex(key, expTime, gson.toJson(object)); // milliSecond
      System.out.println(" end jedis setting obj" + LocalTime.now());
    } catch (Exception e) {
      LOGGER.error(e.getMessage(), e);
    } finally {
      if (jedis != null) jedis.close();
    }

    return true;
  }

  public boolean setDataValueToRedis(String key, Object object) {
    Jedis jedis = getJedisClient();
    try {
      jedis.set(key, gson.toJson(object));
    } catch (Exception e) {
      LOGGER.error(e.getMessage(), e);
    } finally {
      if (jedis != null) jedis.close();
    }
    return true;
  }

  public <T> T getDataValueFromRedis(String key, Class<T> t) {
    Jedis jedis = getJedisClient();
    Object object = null;
    try {
      object = gson.fromJson(jedis.get(key), t);
    } catch (Exception e) {
      LOGGER.error(e.getMessage(), e);
    } finally {
      if (jedis != null) jedis.close();
    }
    if (object == null) {
      return null;
    }
    T tObject = (T) object;
    return tObject;
  }

  public boolean deleteKeyFromRedis(String key) {
    Jedis jedis = getJedisClient();
    try {
      jedis.del(key);
    } catch (Exception e) {
      LOGGER.error(e.getMessage(), e);
    } finally {
      if (jedis != null) jedis.close();
    }
    return true;
  }

  public Jedis getJedisClient() {
    JedisClient jedisClient = new JedisClient();
    Jedis jedis = jedisClient.getJedisPool().getResource();
    return jedis;
  }
}
