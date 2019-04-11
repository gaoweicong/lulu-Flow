package com.glwlc.lulu.framework.util;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @Author: Gavin
 * @Date: 2019-03-11 17:22
 */
@Component
public final class DistributeLockUtil {

    private static final String DEFAULT_VALUE = "LOCK";

    private static final long DEFAULT_TIME = 10L;// MIN

    private static final long DEFAULT_EXPIRE_TIME = 5L*60*1000;

    private static RedisTemplate<String, String> redisTemplate;

    @Autowired
    public DistributeLockUtil(RedisTemplate<String, String> redisTemplate) {
        DistributeLockUtil.redisTemplate = redisTemplate;
    }

    public static boolean lock(String key){
        return lock(key, DEFAULT_EXPIRE_TIME);
    }


    /**
     * 获得锁
     *
     * @param [expireTime]
     * @author Gavin
     * @date 17:26
     */
    public static boolean lock(String key, Long expireTime) {
        try{
            long time = System.currentTimeMillis()+expireTime;
            Boolean flag = false;
            while ((flag==null || (!flag)) && System.currentTimeMillis()<time){
                flag = redisTemplate.opsForValue().setIfAbsent(key, DEFAULT_VALUE);
                if (flag!=null && flag)
                    redisTemplate.expire(key, DEFAULT_TIME, TimeUnit.MINUTES);
            }
            return flag==null ? false : flag;
        }catch (Throwable e){
            redisTemplate.expire(key, 0L, TimeUnit.SECONDS);
            return true;
        }
    }

    /**
     * 释放
     *
     * @author Gavin
     * @date 2019/3/12 9:39
     * @param [key]
     */
    public static void release(String key) {
        Boolean flag = false;
        int index = 0;
        while ((flag==null || !flag) && index++<3){
            // 重试三次
            flag = redisTemplate.expire(key, 0L, TimeUnit.SECONDS);
        }
    }
}
