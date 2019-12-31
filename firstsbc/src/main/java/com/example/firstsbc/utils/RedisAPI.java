package com.example.firstsbc.utils;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Component
public class RedisAPI {
@Autowired
   private JedisPool jedisPool;
    /**
     * 释放连接给连接池
     * @param redis
     */
    public  void returnResource(Jedis redis) {
        try {
            if (redis != null) {
                redis.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获得
     * @param index
     * @return
     */
    public Jedis borrowResource(int index) {
        try {
            Jedis jedis = jedisPool.getResource();
            jedis.select(index);
            return jedis;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }




    public  String get(String key,int dataBase) {
        String value = null;

        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.select(dataBase);
            value = jedis.get(key);
        } catch (Exception e) {
            // 释放redis对象
            returnResource(jedis);
            e.printStackTrace();
        } finally {
            // 返还到连接池
            returnResource(jedis);
        }

        return value;
    }

    public  void set(String key, String value,int dataBase) {

        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.select(dataBase);
            jedis.set(key, value);
        } catch (Exception e) {
            // 释放redis对象
            returnResource(jedis);
            e.printStackTrace();
        } finally {
            // 返还到连接池
            returnResource(jedis);
        }
    }

    public  void setex(String key, int seconds, String value,int dataBase) {

        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.select(dataBase);
            jedis.setex(key, seconds, value);
        } catch (Exception e) {
            // 释放redis对象
            returnResource(jedis);
            e.printStackTrace();
        } finally {
            // 返还到连接池
            returnResource(jedis);
        }
    }





    public  long incr(String key,int dataBase) {

        long counter = 0;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.select(dataBase);
            counter = jedis.incr(key);

        } catch (Exception e) {
            // 释放redis对象
            returnResource(jedis);
            e.printStackTrace();
        } finally {
            // 返还到连接池
            returnResource(jedis);
        }
        return counter;
    }

    public  void expire(String key, int seconds,int dataBase) {

        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.select(dataBase);
            jedis.expire(key, seconds);

        } catch (Exception e) {
            // 释放redis对象
            returnResource(jedis);
            e.printStackTrace();
        } finally {
            // 返还到连接池
            returnResource(jedis);
        }

    }



    public  Long del(String key,int dataBase) {
        Long value = null;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.select(dataBase);
            value = jedis.del(key);
        } catch (Exception e) {
            // 释放redis对象
            returnResource(jedis);
            e.printStackTrace();
        } finally {
            // 返还到连接池
            returnResource(jedis);
        }
        return value;
    }


    public  String hget(String key, String field,int dataBase) {
        String value = null;

        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.select(dataBase);
            value = jedis.hget(key, field);
        } catch (Exception e) {
            // 释放redis对象
            returnResource(jedis);
            e.printStackTrace();
        } finally {
            // 返还到连接池
            returnResource(jedis);
        }

        return value;
    }

    public  Map<String,String> hgetAll(String key,int dataBase) {
        Map<String,String> value = null;

        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.select(dataBase);
            value = jedis.hgetAll(key);
        } catch (Exception e) {
            // 释放redis对象
            returnResource(jedis);
            e.printStackTrace();
        } finally {
            // 返还到连接池
            returnResource(jedis);
        }

        return value;
    }


    public  boolean exist(String key,int dataBase) {
        boolean value= false;

        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.select(dataBase);
            value = jedis.exists(key);
        } catch (Exception e) {
            // 释放redis对象
            returnResource(jedis);
            e.printStackTrace();
        } finally {
            // 返还到连接池
            returnResource(jedis);
        }

        return value;
    }


    public  boolean setNxEx(String key, String value, long exTime, int dataBase) {
        String result = null;

        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.select(dataBase);
            result = jedis.set(key, value, "NX", "EX", exTime);
        } catch (Exception e) {
            // 释放redis对象
            returnResource(jedis);
            e.printStackTrace();
        } finally {
            // 返还到连接池
            returnResource(jedis);
        }

        return result != null;
    }


}

