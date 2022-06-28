package com.nowcoder.community;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;

@SpringBootTest
public class RedisTest {

    @Autowired
    RedisTemplate redisTemplate;

    @Test
    public void testString(){
        String key="redis:count";
        redisTemplate.opsForValue().set(key,1);
        System.out.println(redisTemplate.opsForValue().get(key));
        System.out.println(redisTemplate.opsForValue().decrement(key));
        System.out.println(redisTemplate.opsForValue().increment(key,2));
    }

    @Test
    public void test(){
        Object obj = redisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations redisOperations) throws DataAccessException {
                String key="test:tx";
//                启用事务
                redisOperations.multi();

                redisOperations.opsForSet().add(key,"zhangsan");
                redisOperations.opsForSet().add(key,"lisi");
                redisOperations.opsForSet().add(key,"haha");

                System.out.println(redisOperations.opsForSet().members(key));
                return redisOperations.exec();
            }
        });
        System.out.println(obj);
    }
}
