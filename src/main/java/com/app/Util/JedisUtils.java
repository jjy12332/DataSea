package com.app.Util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Jedis工具类：以String类型为例，封装部分方法
 */
@Component
public class JedisUtils {
    @Autowired
    private JedisPool jedisPool;

    private Jedis jedis = null;

    /**
     * 获取一个Jedis实例
     */
    public Jedis getInstance() {
        jedis = jedisPool.getResource();
        jedis.select(1); // 选择存储库，单机版默认为db(0)
        return jedis;
    }

    /**
     * 回收Jedis实例
     */
    public void takebackJedis(Jedis jedis) {
        if (jedis != null && jedisPool != null) {
            jedis.close();
        }
    }

    /**
     * 根据key获取Value
     */
    public String get(String key) {
        return jedis.get(key);
    }

    /**
     * 添加键值对
     */
    public String set(String key, String value) {
        // jedie.set(key, value, "NX", "EX", 1800); // 添加key设置TTL
        return jedis.set(key, value);
    }

    /**
     * 删除一个或多个key
     */
    public Long del(String... keys) {
        return jedis.del(keys);
    }

    /**
     * java对象序列化后存入redis
     */
//    public byte[] objectSerialize(Object object) {
//        ObjectOutputStream oos = null;
//        ByteArrayOutputStream baos = null;
//        try {
//            baos = new ByteArrayOutputStream();
//            oos = new ObjectOutputStream(baos);
//            oos.writeObject(object);
//            return baos.toByteArray();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (oos != null) {
//                    oos.close();
//                }
//                if (baos != null) {
//                    baos.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return null;
//    }

    /**
     * byte反序列化为java对象
     */
//    public Object deObjectSerialize(byte[] bytes) {
//        ObjectIutputStream ois = null;
//        ByteArrayIutputStream bais = null;
//        try {
//            bais = new ByteArrayIutputStream(bytes);
//            ois = new ObjectIutputStream(bais);
//            return ois.readObject();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (ois != null) {
//                    ois.close();
//                }
//                if (bais != null) {
//                    bais.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return null;
//    }

}
