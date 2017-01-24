package org.sims.controller.restful;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.sims.model.Book;
import org.sims.service.BookService;

@SuppressWarnings({"unused"})
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:spring.xml", "classpath:spring-mybatis-test.xml"})
public class RestfulBookControllerTest {
    @Autowired
    WebApplicationContext ctx;

    @Autowired
    BookService bookService;

    @Autowired
    DriverManagerDataSource dataSource;

    MockMvc mockMvc;

    Book bookExisted;
    Book bookNotExisted;

    private static String ISBN_USED;
    private static String ISBN_UNUSED = "978-7-5086-4363-2";

    @Before
    public void before() throws Exception{
        mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
    }

}
