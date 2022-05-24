package com.nowcoder.community.controller.interceptor;

import com.nowcoder.community.entity.LoginTicket;
import com.nowcoder.community.entity.User;
import com.nowcoder.community.service.UserService;
import com.nowcoder.community.util.CookieUtil;
import com.nowcoder.community.util.HostHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Component
public class LoginTicketInterceptor implements HandlerInterceptor {

    private Logger logger= LoggerFactory.getLogger(LoginTicketInterceptor.class);
    @Autowired
    UserService userService;

    @Autowired
    HostHolder hostHolder;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.debug("LoginTicketInterceptor-->preHandle"+handler.toString());
//        从cookie中获取凭证
        String ticket = CookieUtil.getValue(request, "ticket");
        System.out.println("《《《又拿到一个cookie》》》"+ticket);

        if (ticket != null) {
            LoginTicket loginTicket = userService.findLoginTicket(ticket);
            if (loginTicket != null && loginTicket.getStatus() == 0 && loginTicket.getExpired().after(new Date())) {
//                根据凭证查询用户
                User user = userService.findUserById(loginTicket.getUserId());
//                在本次请求中持有用户。
                hostHolder.setUser(user);
                System.out.println("不删cookie");
            }else{
//                如果没有ticket的cookie，没必要每次都删一次，但先这样加着吧。
//                没有的话就不用管。毕竟只有在有的时候需要回显这一个功能。
                CookieUtil.removeExpiredCookie(request);
                System.out.println("删除cookie");
            }
        }
        return true;
    }

//    controller之后，模板之前
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        logger.debug("LoginTicketInterceptor-->postHandle"+handler.toString());
        User user = hostHolder.getUser();
        if(user!=null && modelAndView!=null){
            modelAndView.addObject("loginUser",user);
        }
    }

//    模板之后
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        logger.debug("LoginTicketInterceptor-->afterCompletion"+handler.toString());
        System.out.println("=======HostHolder中删除了========");
        hostHolder.remove();
    }
}
