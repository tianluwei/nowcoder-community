package com.nowcoder.community.controller;

import com.nowcoder.community.entity.Message;
import com.nowcoder.community.entity.Page;
import com.nowcoder.community.entity.User;
import com.nowcoder.community.service.MessageService;
import com.nowcoder.community.service.UserService;
import com.nowcoder.community.util.CommunityUtil;
import com.nowcoder.community.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class MessageController {

    @Autowired
    MessageService messageService;

    @Autowired
    HostHolder hostHolder;

    @Autowired
    UserService userService;

//    私信列表
    @RequestMapping(path = "/letter/list",method = RequestMethod.GET)
    public String getLetterList(Model model, Page page){

        User user = hostHolder.getUser();

        page.setLimit(5);
        page.setPath("/letter/list");
        page.setRows(messageService.findConversationCount(user.getId()));

        List<Message> conversationList = messageService.findConversations(user.getId(), page.getOffset(), page.getLimit());
        List<Map<String,Object>> conversations=new ArrayList<>();
        if(conversationList!=null){
            for(Message message:conversationList){
                Map<String,Object> map=new HashMap<>();
                map.put("conversation",message);
                map.put("letterCount",messageService.findLettersCount(message.getConversationId()));
                map.put("unreadCount",messageService.findLetterUnread(user.getId(),message.getConversationId()));
                int targetId=user.getId()==message.getFromId()?message.getToId():message.getFromId();
                map.put("target",userService.findUserById(targetId));

                conversations.add(map);
            }
        }

        model.addAttribute("conversations",conversations);

//        查询全部未读消息数量
        int letterUnreadCount = messageService.findLetterUnread(user.getId(), null);
        model.addAttribute("letterUnreadCount",letterUnreadCount);

        return "/site/letter";
    }


    @RequestMapping(path = "/letter/detail/{conversationId}",method = RequestMethod.GET)
    public String getLetterDetail(@PathVariable("conversationId")String conversationId,Page page,Model model){

//        查询分页信息
        page.setLimit(5);
        page.setPath("/letter/detail/"+conversationId);
        page.setRows(messageService.findLettersCount(conversationId));

        List<Message> letterList = messageService.findLetters(conversationId, page.getOffset(), page.getLimit());
        List<Map<String,Object>> letters=new ArrayList<>();
        if(letterList!=null){
            for(Message message:letterList){
                Map<String,Object> map=new HashMap<>();
                map.put("letter",message);
                map.put("fromUser",userService.findUserById(message.getFromId()));

                letters.add(map);
            }
        }
        model.addAttribute("letters",letters);

//        查询私信目标
        model.addAttribute("target",getLetterTarget(conversationId));

//        设置已读
        List<Integer> letterIds = getLetterIds(letterList);
        if(!letterIds.isEmpty()){
            messageService.readMessage(letterIds);
        }

        return "/site/letter-detail";
    }

    private User getLetterTarget(String conversationId){
        String[] s = conversationId.split("_");
        int id0=Integer.parseInt(s[0]);
        int id1=Integer.parseInt(s[1]);
        if(hostHolder.getUser().getId()==id0){
            return userService.findUserById(id1);
        }else{
            return userService.findUserById(id0);
        }
    }

    private List<Integer> getLetterIds(List<Message> letterList){
        List<Integer> ids=new ArrayList<>();

        if(letterList!=null){
            for(Message message:letterList){
                if(hostHolder.getUser().getId()==message.getToId() && message.getStatus()==0){
                    ids.add(message.getId());
                }
            }
        }
        return ids;
    }

    @RequestMapping(path = "/letter/send",method = RequestMethod.POST)
    @ResponseBody
    public String sendLetter(String toName,String content){
//        System.out.println("走了吗，走了吗，走了吗？？？");
        User target = userService.findUserByName(toName);
        if(target==null){
            return CommunityUtil.getJsonString(1,"目标用户不存在！");
        }

        Message message=new Message();
        message.setFromId(hostHolder.getUser().getId());
        message.setToId(target.getId());

        if(message.getFromId()<message.getToId()){
            message.setConversationId(message.getFromId()+"_"+message.getToId());
        }else{
            message.setConversationId(message.getToId()+"_"+message.getFromId());
        }
        message.setContent(content);
        message.setCreateTime(new Date());

        messageService.addMessage(message);

        return CommunityUtil.getJsonString(0);
    }
}
