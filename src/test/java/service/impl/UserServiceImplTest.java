package service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import sims.model.User;
import sims.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.xml", "classpath:spring-mybatis.xml"})
public class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Test
    public void getUserByIdTest(){
        User user = userService.getById("jasonleaster@163.com");
        System.out.println(user.getPassword());
    }
}
