package com.huawei.cache;

import org.apache.ibatis.cache.Cache;
import org.springframework.data.redis.connection.jedis.JedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.util.concurrent.locks.ReadWriteLock;

/**
 * Created by dllo on 17/11/27.
 */
public class RedisCache implements Cache {

    // Redis的连接工厂
    private static JedisConnectionFactory connectionFactory;
    // 缓存的唯一标识
    private String id;
    // 读写锁
    private ReadWriteLock readWriteLock;

    public static void setConnectionFactory(JedisConnectionFactory connectionFactory) {
        RedisCache.connectionFactory = connectionFactory;
    }

    public RedisCache(String id) {
        if (id == null) {
            throw new IllegalArgumentException("缓存需要一个id");
        }
        this.id = id;
    }

    // 返回缓存对象的唯一标识
    @Override
    public String getId() {
        return this.id;
    }

    // 保存key/value到缓存对象中
    @Override
    public void putObject(Object key, Object value) {
        JedisConnection connection = null;
        try {
            // 建立连接
            connection = (JedisConnection) connectionFactory.getConnection();
            // 序列化工具类
            RedisSerializer<Object> serializer = new JdkSerializationRedisSerializer();
            byte[] keys = serializer.serialize(key);
            byte[] values = serializer.serialize(value);

            connection.set(keys, values);
        } catch (JedisConnectionException e) {
            e.printStackTrace();
        } finally {
            // 关闭
            if (connection != null) {
                connection.close();
            }
        }
    }

    // 根据key从缓存中取值
    @Override
    public Object getObject(Object key) {
        JedisConnection connection = null;
        try {
            connection = (JedisConnection) connectionFactory.getConnection();
            RedisSerializer<Object> serialize = new JdkSerializationRedisSerializer();
            byte[] keys = serialize.serialize(key);
            byte[] value = connection.get(keys);
            return serialize.deserialize(value); // 反序列化
        } catch (JedisConnectionException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.close();
            }
        }

        return null;
    }

    // 根据key移除对应的值
    @Override
    public Object removeObject(Object key) {
        JedisConnection connection = null;
        try {
            connection = (JedisConnection) connectionFactory.getConnection();
            RedisSerializer<Object> serialize = new JdkSerializationRedisSerializer();
            byte[] keys = serialize.serialize(key);
            return connection.expire(keys, 0);
        } catch (JedisConnectionException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.close();
            }
        }

        return null;
    }

    // 清空缓存
    @Override
    public void clear() {
        JedisConnection connection = null;
        try {
            connection = (JedisConnection) connectionFactory.getConnection();
            RedisSerializer<Object> serialize = new JdkSerializationRedisSerializer();
            connection.flushDb(); //清空数据库
            connection.flushAll(); //清空所有缓存
        } catch (JedisConnectionException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    // 获取缓存中键值对的数量
    @Override
    public int getSize() {
        JedisConnection connection = null;
        try {
            connection = (JedisConnection) connectionFactory.getConnection();
            return connection.dbSize().intValue();
        } catch (JedisConnectionException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return 0;
    }

    // 获取读写锁
    // 任何需要的锁都必须由缓存供应方来提供
    @Override
    public ReadWriteLock getReadWriteLock() {
        return this.readWriteLock;
    }
}
