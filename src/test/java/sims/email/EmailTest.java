package sims.email;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:spring.xml", "classpath:spring-mybatis-test.xml"})
public class EmailTest {

    @Autowired
    private Email email;

    @Test
    public void testSendMail() {
        email.sendMail("Spring SMTP Mail Subject", "Spring SMTP Mail Text", "jasonleaster@163.com");
        //sims.email.sendMail("标题", "内容", "收件人邮箱");
    }
}
