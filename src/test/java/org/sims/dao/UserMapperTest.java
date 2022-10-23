package org.sims.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sims.domain.User;
import org.sims.infrastructure.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;


@SpringBootTest
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private DataSource dataSource;

    @BeforeEach
    public void before(){
        Assertions.assertNotNull(dataSource);
    }


    @Test
    public void testMyBatisMapper() {
        User user = new User();
        user.setUsername("张三");
        user.setUserType(1);
        user.setPassword("123");
        user.setEmail("jasonleaster@gmail.com");

        userMapper.insert(user);
    }
}
