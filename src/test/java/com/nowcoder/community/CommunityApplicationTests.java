package com.nowcoder.community;

import com.nowcoder.community.dao.DiscussPostMapper;
import com.nowcoder.community.dao.UserMapper;
import com.nowcoder.community.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
class CommunityApplicationTests {

    @Autowired
    UserMapper userMapper;

    @Autowired
    DiscussPostMapper discussPostMapper;

    @Test
    void contextLoads() {
        User user = userMapper.selectById(150);
        System.out.println(user);
    }

    @Test
    public void test(){
        User user=new User();
        user.setUsername("haha");
        user.setPassword("123");
        user.setSalt("123");
        user.setEmail("28392829@qq.com");
        user.setType(1);
        user.setStatus(1);
        user.setActiveCode("123");
        user.setHeaderUrl("123.com");
        user.setCreateTime(new Date());
        int i = userMapper.insertUser(user);
        System.out.println(i);
        userMapper.updateHeader(150,"2424.org");
    }

    @Test
    public void test1(){
//        List<DiscussPost> discussPosts = discussPostMapper.selectDiscussPosts(0, 0, 10);
//        for(DiscussPost i:discussPosts){
//            System.out.println(i);
//        }
        int i = discussPostMapper.selectDiscussPostRows(0);
        System.out.println(i);
    }
}
