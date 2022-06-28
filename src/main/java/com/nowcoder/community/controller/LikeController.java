package com.nowcoder.community.controller;


import com.nowcoder.community.entity.User;
import com.nowcoder.community.service.LikeService;
import com.nowcoder.community.util.CommunityUtil;
import com.nowcoder.community.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class LikeController {

    @Autowired
    LikeService likeService;

//    当前点赞用户
    @Autowired
    HostHolder hostHolder;

//    todo 在like这里加个拦截，因为如果没用户登录就不能使用。
//    处理异步请求的方法
    @RequestMapping(path = "/like",method = RequestMethod.POST)
    @ResponseBody
    public String like(int entityType,int entityId,int entityUserId){
        User user = hostHolder.getUser();

        likeService.like(user.getId(),entityType,entityId,entityUserId);

        long likeCount = likeService.findEntityLikeCount(entityType, entityId);
        int likeStatus = likeService.findEntityLikeStatus(user.getId(), entityType, entityId);

//        返回的结果
        Map<String,Object> map=new HashMap<>();
        map.put("likeCount",likeCount);
        map.put("likeStatus",likeStatus);

        // TODO: 2022/6/14   问题1、这里传的这个json的code是什么意思？它的作用是什么？
        return CommunityUtil.getJsonString(0,null,map);
    }
}
