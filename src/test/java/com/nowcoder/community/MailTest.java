package com.nowcoder.community;

import com.nowcoder.community.dao.LoginTicketMapper;
import com.nowcoder.community.entity.LoginTicket;
import com.nowcoder.community.util.MailClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Date;

@SpringBootTest
public class MailTest {

    @Autowired
    private MailClient mailClient;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private LoginTicketMapper loginTicketMapper;

    @Test
    public void mailSend() {
        mailClient.sendMail("nowcodertlw@sina.com", "hello man", "be kind to yourself,yes!");
    }

    @Test
    public void HTMLSend() {
        Context context = new Context();
        context.setVariable("username", "KOBE");
        String content = templateEngine.process("/mail/demo", context);
        System.out.println(content);

        mailClient.sendMail("nowcodertlw@sina.com", "HTML", content);
    }

    @Test
    public void loginTicketTest() {
        LoginTicket loginTicket = new LoginTicket(1, 11, "haha", 0, new Date());
//        int i = loginTicketMapper.insertLoginTicket(loginTicket);
//        System.out.println(i);
        LoginTicket loginTicket1 = loginTicketMapper.selectByTicket("haha");
        System.out.println(loginTicket1);

        int update = loginTicketMapper.updateTicket("haha", 1);
        System.out.println(update);
    }
}
