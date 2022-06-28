package com.nowcoder.community.service;

import com.nowcoder.community.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
public class LikeService {

    @Autowired
    private RedisTemplate redisTemplate;

//    点赞
    public void like(int userId,int entityType,int entityId,int entityUserId){
//        String entityLikeKey= RedisKeyUtil.getEntityLike(entityType,entityId);
//        Boolean member = redisTemplate.opsForSet().isMember(entityLikeKey, userId);
//        if(member){
//            redisTemplate.opsForSet().remove(entityLikeKey,userId);
//        }else{
//            redisTemplate.opsForSet().add(entityLikeKey,userId);
//        }
//        一个业务连续两次执行更新，事务
        redisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                String entityLikeKey=RedisKeyUtil.getEntityLike(entityType,entityId);
//                这个user是实体的作者，也就是被赞的人，他的赞数increase+1.
                String userLikeKey=RedisKeyUtil.getUserLikeKey(entityUserId);
                boolean isMember=operations.opsForSet().isMember(entityLikeKey,userId);

//                开启事务
                operations.multi();

                if(isMember){
                    operations.opsForSet().remove(entityLikeKey,userId);
                    operations.opsForValue().decrement(userLikeKey);
                }else{
                    operations.opsForSet().add(entityLikeKey,userId);
                    operations.opsForValue().increment(userLikeKey);
                }

//                执行事务
                return operations.exec();
            }
        });
    }

//    查询某实体点赞数量
    public long findEntityLikeCount(int entityType,int entityId){
        String entityLikeKey=RedisKeyUtil.getEntityLike(entityType, entityId);
        return redisTemplate.opsForSet().size(entityLikeKey);
    }

//    查询某人对某实体点赞的状态
    public int findEntityLikeStatus(int userId,int entityType,int entityId){
        String entityLikeKey = RedisKeyUtil.getEntityLike(entityType, entityId);
        return redisTemplate.opsForSet().isMember(entityLikeKey,userId)?1:0;
    }

//    查询某个用户获得的赞
    public int findUserLikeCount(int userId){
        String userLikeKey=RedisKeyUtil.getUserLikeKey(userId);
        Integer count=(Integer) redisTemplate.opsForValue().get(userLikeKey);
        return count==null?0:count.intValue();
    }

}
