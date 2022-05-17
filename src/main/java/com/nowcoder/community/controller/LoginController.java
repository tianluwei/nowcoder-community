package com.nowcoder.community.controller;

import com.google.code.kaptcha.Producer;
import com.nowcoder.community.entity.User;
import com.nowcoder.community.service.UserService;
import com.nowcoder.community.util.CommunityConstant;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

@Controller
public class LoginController implements CommunityConstant {

    private Logger logger = LoggerFactory.getLogger(LoginController.class);

    // TODO: 2022/5/15 和@Bean方法名不一样能不能注入进来？？？  事实证明，可以用类型注入。
    @Autowired
    Producer producer;

    @Autowired
    UserService userService;

    @Value("${server.servlet.context-path}")
    String contextPath;


    @RequestMapping(path = "/register", method = RequestMethod.GET)
    public String register() {
        return "/site/register";
    }
    // TODO: 2022/5/14 问题：1、注册成功之前邮件就已经发送了。已解决  2、注册失败后传过来的密码是加密后的。已解决

    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public String register(Model model, User user) {
        Map<String, Object> map = userService.register(user);
        if (map == null || map.isEmpty()) {
            model.addAttribute("msg", "注册成功，我们已向您的邮箱发送了一封激活邮件，请尽快激活！");
            model.addAttribute("target", "/index");
            return "/site/operate-result";
        } else {
            model.addAttribute("usernameMsg", map.get("usernameMsg"));
            model.addAttribute("passwordMsg", map.get("passwordMsg"));
            model.addAttribute("emailMsg", map.get("emailMsg"));

            return "/site/register";
        }
    }

    @RequestMapping(path = "/activation/{userId}/{code}", method = RequestMethod.GET)
    public String activation(Model model, @PathVariable("userId") int userId, @PathVariable("code") String code) {
        int result = userService.activation(userId, code);
        if (result == 0) {
            model.addAttribute("msg", "激活成功，您的账号已经可以正常使用了！");
            model.addAttribute("target", "/login");
            return "/site/login";
        } else if (result == 1) {
            model.addAttribute("msg", "无效操作，该账号已经激活过了！");
            model.addAttribute("target", "/index");
        } else {
            model.addAttribute("msg", "激活失败，您提供的激活码不正确！");
            model.addAttribute("target", "/index");
        }
        return "/site/operate-result";
    }

    @RequestMapping(path = "login", method = RequestMethod.GET)
    public String getLoginPage() {
        return "/site/login";
    }

    @RequestMapping(path = "kaptcha", method = RequestMethod.GET)
    public void getKaptcha(HttpServletResponse response, HttpSession session) {
        String text = producer.createText();
        BufferedImage image = producer.createImage(text);

        session.setAttribute("text", text);

        response.setContentType("image/png");

        try {
            ServletOutputStream os = response.getOutputStream();
            ImageIO.write(image, "png", os);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String login(String username, String password, String code, boolean rememberme,
                        Model model, HttpSession session, HttpServletResponse response) {
//        检查验证码
        String text = (String) session.getAttribute("text");
        if (StringUtils.isBlank(text) || StringUtils.isBlank(code) || !code.equalsIgnoreCase(text)) {
            model.addAttribute("codeMsg", "验证码不正确,请尝试重新输入！");
            return "/site/login";
        }
        int expiredTime = rememberme ? REMEMBER_EXPIRED_SECONDS : DEFAULT_EXPIRED_SECONDS;
        Map<String, Object> map = userService.login(username, password, expiredTime);
        if (map.containsKey("ticket")) {
            Cookie cookie = new Cookie("ticket", (String) map.get("ticket"));
            cookie.setMaxAge(expiredTime);
//            cookie设置的有效路径是contextPath所以之后不管访问那个，都会把cookie这条数据携带发给服务器。
            cookie.setPath(contextPath);
            response.addCookie(cookie);
            return "redirect:/index";
        } else {
            model.addAttribute("usernameMsg", map.get("usernameMsg"));
            model.addAttribute("passwordMsg", map.get("passwordMsg"));
//            todo 这样写可以吗？而不是forward：/site/login   这样写经过一个controller还能不能把model中的值传过去
//            看样子（return /login）这样不行的哦
            return "/site/login";
        }
    }

    @RequestMapping(path = "/logout", method = RequestMethod.GET)
    public String logout(@CookieValue("ticket") String ticket) {
        userService.logout(ticket);
        return "redirect:/login";
    }
    // TODO: 2022/5/17 忘记密码实现 ？？？


    //    测试cookie，cookie会先从服务端发给客户端，然后客户端再请求时会携带cookie
    public String setCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie("code", "hahahahaha");
        cookie.setPath("/community/alpha");
        cookie.setMaxAge(60 * 10);
        response.addCookie(cookie);
        return "heihiehei on page";
    }

    @RequestMapping(path = "/cookie/get", method = RequestMethod.GET)
    @ResponseBody
//    cookie会自动（返还）发送过来
//    code是cookie的key
    public String getCookie(@CookieValue("code") String code) {
        System.out.println(code);
        return "get cookie";
    }

    @RequestMapping(path = "/session/set", method = RequestMethod.GET)
    @ResponseBody
    public String setSession(HttpSession session) {
        session.setAttribute("id", 1);
        session.setAttribute("name", "test");
        return "set session";
    }

    @RequestMapping(path = "/session/get", method = RequestMethod.GET)
    @ResponseBody
    public String getSession(HttpSession session) {
        session.getAttribute("id");
        session.getAttribute("name");
        return "get session";
    }
}
