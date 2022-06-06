package com.nowcoder.community.controller;

import com.nowcoder.community.entity.Comment;
import com.nowcoder.community.entity.DiscussPost;
import com.nowcoder.community.entity.Page;
import com.nowcoder.community.entity.User;
import com.nowcoder.community.service.CommentService;
import com.nowcoder.community.service.DiscussPostService;
import com.nowcoder.community.service.UserService;
import com.nowcoder.community.util.CommunityConstant;
import com.nowcoder.community.util.CommunityUtil;
import com.nowcoder.community.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
@RequestMapping("discuss")
public class DiscussPostController implements CommunityConstant {

    @Autowired
    HostHolder hostHolder;

    @Autowired
    DiscussPostService discussPostService;

    @Autowired
    UserService userService;

    @Autowired
    CommentService commentService;



    @RequestMapping(path="/add",method = RequestMethod.POST)
    @ResponseBody
    public String addDiscussPost(String title,String content){
        User user = hostHolder.getUser();
        if(user==null){
            return CommunityUtil.getJsonString(403,"您还没有登录，没有发布帖子的权限哦！");
        }

        DiscussPost discussPost=new DiscussPost();
        discussPost.setTitle(title);
        discussPost.setContent(content);
        discussPost.setUserId(user.getId());
        discussPost.setCreateTime(new Date());

        discussPostService.addDiscussPost(discussPost);
//        报错的情况，将来统一处理。
        return CommunityUtil.getJsonString(0,"发布帖子成功！");
    }

    @RequestMapping(path = "/detail/{discussPostId}",method = RequestMethod.GET)
    public String getDiscussPost(@PathVariable("discussPostId")int id, Model model, Page page){
//        写在这里的参数page会自动放到model（request）域中。
        DiscussPost post = discussPostService.findDiscussPostById(id);
        model.addAttribute("post",post);

//        它是一个请求调一次，model相当于是一个request。
        User user = userService.findUserById(post.getUserId());
        model.addAttribute(user);

//        评论分页信息
        page.setLimit(5);
        page.setPath("/discuss/detail/"+post.getId());
        page.setRows(post.getCommentCount());

//      帖子的评论列表
        List<Comment> commentList = commentService.findCommentsByEntity(
                ENTITY_TYPE_POST, post.getId(), page.getOffset(), page.getLimit());

//        评论：给帖子的评论
//        回复：给评论的评论

//        评论ViewObject列表
        List<Map<String,Object>> commentVoList=new ArrayList<>();
        if(commentList!=null){
            for(Comment comment:commentList){
                Map<String,Object> commentVo=new HashMap<>();

//                评论
                commentVo.put("comment",comment);
//                作者
                commentVo.put("user",userService.findUserById(comment.getUserId()));

//                回复列表
                List<Comment> replyList = commentService.findCommentsByEntity(ENTITY_TYPE_COMMENT, comment.getId(), 0, Integer.MAX_VALUE);

                List<Map<String,Object>> replyVoList=new ArrayList<>();
                if(replyList!=null){
                    for(Comment reply:replyList){
                        Map<String,Object> replyVo=new HashMap<>();

                        replyVo.put("reply",reply);
                        replyVo.put("user",userService.findUserById(reply.getUserId()));
//                        回复目标
                        User target=reply.getTargetId()==0?null: userService.findUserById(reply.getTargetId());
                        replyVo.put("target",target);
                        replyVoList.add(replyVo);
                    }
                }
                commentVo.put("replys",replyVoList);

//                回复数量
                int replyCount = commentService.findCountByEntity(ENTITY_TYPE_COMMENT,comment.getId());
                commentVo.put("replyCount",replyCount);

                commentVoList.add(commentVo);
            }
        }
        model.addAttribute("comments",commentVoList);

        return "/site/discuss-detail";
    }
}
