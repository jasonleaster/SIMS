package email;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import sims.email.Email;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:spring.xml", "classpath:spring-mybatis-test.xml"})
public class EmailTest {

    @Autowired
    private Email email;

    @Test
    public void testSendMail() {
        email.sendMail("Spring SMTP Mail Subject", "Spring SMTP Mail Text", "jasonleaster@163.com");
        //email.sendMail("标题", "内容", "收件人邮箱");
    }
}
