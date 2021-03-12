package com.salesmanager.shop.cache.util;

import com.salesmanager.shop.cache.dao.CacheDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component("cacheUtil")
public class CacheUtil {

  @Autowired private CacheDao cacheDao;

  @Autowired public Environment environment;

  public <T> T getObjectFromCache(String key, Class<T> t) {
    return cacheDao.getDataValueFromRedis(key, t);
  }

  public void setObjectInCache(String memCacheKey, Object obj, Long expTime) {
    cacheDao.setDataValueToRedis(memCacheKey, obj, expTime);
  }

  public boolean setObjectInCache(String memCacheKey, Object obj) {
    return cacheDao.setDataValueToRedis(memCacheKey, obj);
  }

  public void setObjectInCache(String memCacheKey, Object obj, int expTime) {
    cacheDao.setDataValueToRedis(memCacheKey, obj, expTime);
  }

  public boolean deleteObjectFromCache(String key) {
    Object object = cacheDao.getDataValueFromRedis(key, Object.class);
    if (object == null) {
      return false;
    }
    cacheDao.deleteKeyFromRedis(key);
    return true;
  }
}
