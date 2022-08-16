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

    /**
     * page有两个作用，一个是给接下来的controller指导查询的页数和行数，一个是给页面中要请求服务器的要素指导链接和第几页数。
     * @return
     */
//    从小事，简单的事做起。就好。。。
    @RequestMapping(path = "/index", method = RequestMethod.GET)
    public String index(Model model, Page page) {
        // fixme  这样写也可以，把page加在model里也可以。写在参数里就可以直接用。
//        还有个问题就是，如果写在里面，那么通过分页再次请求/index的时候，要传index/&参数，其实也可以再改一次。但直接在参数里写page就会把current（当前页）参数直接封装到page里面了。下面会直接走，并且是对的。
//        todo 我大概知道了是这样做的，但是为什么呢？？像下面说的springmvc会自动实例化参数，但是怎么做到的？？？
//        Page page=new Page();
        System.out.println("这是indexController");
//        这里的page在方法调用前 SpringMVC会自动实例化。只是实例化，赋了默认值，其他值没有赋。
        page.setRows(discussPostService.findDiscussPostRows(0));
        page.setPath("/index");

//        model.addAttribute("page",page);

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
