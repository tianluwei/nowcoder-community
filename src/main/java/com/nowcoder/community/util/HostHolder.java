package com.nowcoder.community.util;

import com.nowcoder.community.entity.User;
import org.springframework.stereotype.Component;

/**
 * 把数据存到当前线程对应的map里，请求没有处理完，这个线程一直还在。
 * 请求处理完，服务器向浏览器作出响应之后，线程被销毁。
 * 一次请求，一个线程。这个请求没有处理完，ThreadLocal里的数据都是还在的。
 */
@Component
public class HostHolder{

    private ThreadLocal<User> users=new ThreadLocal();

    public void setUser(User user){
        users.set(user);
    }

    public User getUser(){
        return users.get();
    }

    public void remove(){
        users.remove();
    }


}
