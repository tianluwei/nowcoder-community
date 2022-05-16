package com.nowcoder.community;

import com.nowcoder.community.util.MailClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@SpringBootTest
public class MailTest {

    @Autowired
    private MailClient mailClient;

    @Autowired
    private TemplateEngine templateEngine;

    @Test
    public void mailSend(){
        mailClient.sendMail("nowcodertlw@sina.com","hello man","be kind to yourself,yes!");
    }

    @Test
    public void HTMLSend(){
        Context context=new Context();
        context.setVariable("username","KOBE");
        String content = templateEngine.process("/mail/demo", context);
        System.out.println(content);

        mailClient.sendMail("nowcodertlw@sina.com","HTML",content);
    }
}
