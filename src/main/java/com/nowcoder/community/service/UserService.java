package com.nowcoder.community.service;

import com.nowcoder.community.dao.LoginTicketMapper;
import com.nowcoder.community.dao.UserMapper;
import com.nowcoder.community.entity.LoginTicket;
import com.nowcoder.community.entity.User;
import com.nowcoder.community.util.CommunityConstant;
import com.nowcoder.community.util.CommunityUtil;
import com.nowcoder.community.util.MailClient;
import com.nowcoder.community.util.RedisKeyUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class UserService implements CommunityConstant {

    @Autowired
    UserMapper userMapper;

//    @Autowired
//    LoginTicketMapper loginTicketMapper;

    @Autowired
    RedisTemplate redisTemplate;


    @Autowired
    TemplateEngine templateEngine;

    @Autowired
    MailClient mailClient;

    @Value("${community.path.domain}")
    String domain;

    @Value("${server.servlet.context-path}")
    String contextPath;


    //    根据id查找用户，查询消息列表时和消息联合查询。
    public User findUserById(int id) {
//        return userMapper.selectById(id);
        User user=getCache(id);
        if(user==null){
            user= initCache(id);
        }
        return user;
    }

    public Map<String, Object> register(User user) {
        Map<String, Object> map = new HashMap<>();
        if (null == user) {
            throw new IllegalArgumentException("用户参数不能为空！");
        }
        if (StringUtils.isBlank(user.getUsername())) {
            map.put("usernameMsg", "账号不能为空");
            return map;
        }

        if (StringUtils.isBlank(user.getPassword())) {
            map.put("passwordMsg", "密码不能为空");
            return map;
        }

        if (StringUtils.isBlank(user.getEmail())) {
            map.put("emailMsg", "邮箱不能为空");
            return map;
        }

        User u = userMapper.selectByName(user.getUsername());
        if (u != null) {
            map.put("usernameMsg", "该改用户名已存在，请重新输入！");
            return map;
        }

        u = userMapper.selectByEmail(user.getEmail());
        if (u != null) {
            map.put("emailMsg", "该邮箱已存在，请重新输入！");
            return map;
        }

//        注册用户
        user.setSalt(CommunityUtil.generateUUID().substring(0, 5));
        user.setPassword(CommunityUtil.md5(user.getPassword() + user.getSalt()));
        user.setType(0);
        user.setStatus(0);
        user.setActivationCode(CommunityUtil.generateUUID());
        user.setHeaderUrl(String.format("https://images.nowcoder.com/head/%dt.png", new Random().nextInt(1000)));
        user.setCreateTime(new Date());
        userMapper.insertUser(user);
//        keyProperty可以把id注入到实体对象user中。
//        System.out.println(user.getId()+"<><><><><>");

//       激活邮箱
        Context context = new Context();
        context.setVariable("email", user.getEmail());
        String url = domain + contextPath + "/activation/" + user.getId() + "/" + user.getActivationCode();
        context.setVariable("url", url);

        String content = templateEngine.process("/mail/activation", context);
        mailClient.sendMail(user.getEmail(), "激活账号", content);

        return map;
    }

    //    用户通过邮件的链接激活
    public int activation(int userId, String code) {
        User user = userMapper.selectById(userId);
//        System.out.println("status"+user.getStatus());
//        System.out.println("activeCode"+user.getActivationCode());

        if (user.getStatus() == 1) {
            return ACTIVATION_REPEAT;
        } else if (user.getActivationCode().equals(code)) {
            userMapper.updateStatus(userId, 1);
            clearCache(userId);
            return ACTIVATION_SUCCESS;
        } else {
            return ACTIVATION_FAILURE;
        }
    }

    //    登录
    public Map<String, Object> login(String username, String password, int expiredSeconds) {
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isBlank(username)) {
            map.put("usernameMsg", "用户名不能为空！");
            return map;
        }
        if (StringUtils.isBlank(password)) {
            map.put("passwordMsg", "密码不能为空！");
            return map;
        }
        User user = userMapper.selectByName(username);
        if (user == null) {
            map.put("usernameMsg", "该用户不存在，请重新输入！");
            return map;
        }
        if (user.getStatus() == 0) {
            map.put("usernameMsg", "该账号未激活，请您激活后再使用!");
            return map;
        }
        String md5Password = CommunityUtil.md5(password + user.getSalt());
        if (!user.getPassword().equals(md5Password)) {
            map.put("passwordMsg", "密码错误，请重新输入！");
            return map;
        }

//        生成登录凭证
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(user.getId());
        loginTicket.setTicket(CommunityUtil.generateUUID());
        loginTicket.setStatus(0);
        loginTicket.setExpired(new Date(System.currentTimeMillis() + expiredSeconds * 1000));

//        loginTicketMapper.insertLoginTicket(loginTicket);
        String redisKey= RedisKeyUtil.getTicketKey(loginTicket.getTicket());
        redisTemplate.opsForValue().set(redisKey,loginTicket);


        map.put("ticket", loginTicket.getTicket());
        return map;
    }

    public void logout(String ticket) {
//        loginTicketMapper.updateTicket(ticket, 1);
        String redisKey=RedisKeyUtil.getTicketKey(ticket);
        LoginTicket loginTicket = (LoginTicket) redisTemplate.opsForValue().get(redisKey);
        loginTicket.setStatus(1);
        redisTemplate.opsForValue().set(redisKey,loginTicket);

    }

    public LoginTicket findLoginTicket(String ticket) {
//        return loginTicketMapper.selectByTicket(ticket);
        String redisKey=RedisKeyUtil.getTicketKey(ticket);
        return (LoginTicket) redisTemplate.opsForValue().get(redisKey);
    }

    public int updateHeaderUrl(int userId,String headerUrl){
//        return userMapper.updateHeader(userId,headerUrl);
        int rows=userMapper.updateHeader(userId,headerUrl);
        clearCache(userId);
        return rows;
    }

//    忘记密码发送邮件验证
    public Map<String,Object> verify(String email){
        Map<String,Object> map=new HashMap<>();
        User user = userMapper.selectByEmail(email);
        if(user==null){
            map.put("emailMsg","该邮箱未注册过，请重新输入有效邮箱！");
            return map;
        }
        String verifyCode=CommunityUtil.generateUUID().substring(0,6);
        map.put("verifyCode",verifyCode);
        mailClient.sendMail(user.getEmail(),"重置密码验证","这是您的重置密码验证码:"+verifyCode+"，请妥善保管，不要泄漏。如果不是本人操作忽略本邮件！");
        return map;
    }

//    验证码和新密码验证没问题，开始改密码。
    public int forget(String email,String newPassword){
        User user = userMapper.selectByEmail(email);
        newPassword = CommunityUtil.md5(newPassword + user.getSalt());

        return userMapper.updatePassword(user.getId(),newPassword);
    }

    public User findUserByName(String username){
        return userMapper.selectByName(username);
    }

//    查询时优先从缓存中取值
    private User getCache(int userId){
        String redisKey = RedisKeyUtil.getUserKey(userId);
        return (User) redisTemplate.opsForValue().get(redisKey);
    }

//    取不到时初始化缓存数据
    private User initCache(int userId){
        User user = userMapper.selectById(userId);
        String redisKey = RedisKeyUtil.getUserKey(userId);
//        存1个小时
        redisTemplate.opsForValue().set(redisKey,user,3600, TimeUnit.SECONDS);
        return user;
    }

//    数据变更时清楚缓存数据
    private void clearCache(int userId){
        String redisKey=RedisKeyUtil.getUserKey(userId);
        redisTemplate.delete(redisKey);
    }

}
