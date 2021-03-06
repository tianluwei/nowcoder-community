package com.nowcoder.community.controller;

import com.nowcoder.community.entity.*;
import com.nowcoder.community.service.DiscussPostService;
import com.nowcoder.community.service.LikeService;
import com.nowcoder.community.service.UserService;
import com.nowcoder.community.util.CommunityConstant;
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
public class HomeController implements CommunityConstant {

    @Autowired
    UserService userService;

    @Autowired
    DiscussPostService discussPostService;

    @Autowired
    LikeService likeService;

    @RequestMapping(path = "/index", method = RequestMethod.GET)
    public String index(Model model, Page page) {
        System.out.println("这是indexController");
//        这里的page在方法调用前 SpringMVC会自动实例化。
        page.setRows(discussPostService.findDiscussPostRows(0));
        page.setPath("/index");

//        System.out.println("我将要调getoffset了哦！");
        List<DiscussPost> discussPosts = discussPostService.findDiscussPosts(0, page.getOffset(), page.getLimit());
        List<Map<String, Object>> list = new ArrayList<>();
        if (discussPosts != null) {
            for (DiscussPost post : discussPosts) {
                Map<String, Object> map = new HashMap<>();
                map.put("post", post);
                User user = userService.findUserById(post.getUserId());
                map.put("user", user);

                long likeCount=likeService.findEntityLikeCount(ENTITY_TYPE_POST,post.getId());
                map.put("likeCount",likeCount);


                list.add(map);
            }
        }
        model.addAttribute("list", list);
        // FIXME: 2022/5/17 这里记住，要跳转到classpath下的/index。这里不加也是没关系的，but why？
//        我想知道的是，DispatcherServlet做了什么？ 它的里面做了什么事情，才能这样就跳转。
        return "index";
    }

    @RequestMapping(path = "error",method = RequestMethod.GET)
    public String getErrorPage(){
        return "/error/500";
    }
}
