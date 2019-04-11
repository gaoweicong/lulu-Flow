package com.glwlc.lulu.test;

import com.glwlc.lulu.framework.FlowStarter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

/**
 * @Author: Gavin
 * @Date: 2019-03-11 15:59
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {FlowStarter.class})
public class RedisTest {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    public void redisTest() {
        // redis存储数据
        String key = "name";
        redisTemplate.opsForValue().set(key, "helloworld");
        redisTemplate.expire(key, 10L , TimeUnit.MINUTES);
        // 获取数据
        String value = (String) redisTemplate.opsForValue().get(key);
        System.out.println(value);
    }
}
