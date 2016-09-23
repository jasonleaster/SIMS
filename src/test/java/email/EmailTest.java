package email;

import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import sims.email.Email;

@RunWith(SpringJUnit4ClassRunner.class)
public class EmailTest {

    private Email email;

    public void testSendMail() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-smtp-mail.xml");
        Email mail = (Email)context.getBean("simpleMail");
        mail.sendMail("Spring SMTP Mail Subject", "Spring SMTP Mail Text", "fancydeepin@yeah.net");
        //mail.sendMail("标题", "内容", "收件人邮箱");
    }
}
