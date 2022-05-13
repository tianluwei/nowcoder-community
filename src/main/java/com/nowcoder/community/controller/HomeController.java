package com.nowcoder.community.controller;

import com.nowcoder.community.entity.*;
import com.nowcoder.community.service.DiscussPostService;
import com.nowcoder.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController {

    @Autowired
    UserService userService;

    @Autowired
    DiscussPostService discussPostService;

    @RequestMapping(path = "/index",method = RequestMethod.GET)
    public String index(Model model, Page page){
//        这里的page在方法调用前 SpringMVC会自动实例化。
        page.setRows(discussPostService.findDiscussPostRows(0));
        page.setPath("/index");

//        System.out.println("我将要调getoffset了哦！");
        List<DiscussPost> discussPosts = discussPostService.findDiscussPosts(0, page.getOffset(), page.getLimit());
        List<Map<String,Object>> list=new ArrayList<>();
        if (discussPosts!=null) {
            for(DiscussPost post:discussPosts){
                Map<String,Object> map=new HashMap<>();
                map.put("post",post);
                User user = userService.findUserById(post.getUserId());
                map.put("user",user);
                list.add(map);
            }
        }
        model.addAttribute("list",list);
        return "index";

    }
}
