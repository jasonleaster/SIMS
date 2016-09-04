package controller;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import sims.model.User;
import sims.service.UserService;
import sims.util.MsgAndContext;
import sims.util.URLs;
import sims.util.Views;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:spring.xml", "classpath:spring-mybatis-test.xml"})
public class UserControllerTest {

    private static Logger log = Logger.getLogger(UserControllerTest.class.getName());

    @Autowired
    WebApplicationContext ctx;

    @Autowired
    UserService userService;

    @Autowired
    DriverManagerDataSource dataSourceForTest;

    MockMvc mockMvc;

    User userExisted;
    User userNotExisted;

    private void initDB(){
        Resource resource = new ClassPathResource("SQL/schema.sql");
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
        databasePopulator.addScript(resource);
        DatabasePopulatorUtils.execute(databasePopulator, dataSourceForTest);
    }

    @Before
    public void before(){
        mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();

        initDB();

        userExisted    = new User("Eric", "eric@gmail.com", "ericpassword");
        userNotExisted = new User("Jack", "jack@gmail.com", "jackpassword");

        userService.add(userExisted);
    }


    @Test
    public void searchTest() throws Exception{
        String url = URLs.USERS + URLs.QUERY;

        mockMvc.perform(MockMvcRequestBuilders.get(url))
                .andExpect(MockMvcResultMatchers.view().name(Views.USER_SEARCH));

        mockMvc.perform(MockMvcRequestBuilders.post(url)
                        .param("email", userExisted.getEmail()))
                .andExpect(MockMvcResultMatchers.model().attributeExists(MsgAndContext.MODEL_ATTRIBUTES_USER))
                .andExpect(MockMvcResultMatchers.view().name(Views.USER_SHOW))
                .andExpect(MockMvcResultMatchers.status().isOk());

        //log.info("Search test finished successfully!");
    }

    @Test
    public void createTest() throws Exception{
        String url = URLs.USERS + URLs.CREATE;

        mockMvc.perform(MockMvcRequestBuilders.get(url))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name(Views.USER_CREATE));

        // Confirmed password error
        mockMvc.perform(MockMvcRequestBuilders.post(url)
                        .param("username", userNotExisted.getUsername())
                        .param("email",    userNotExisted.getEmail())
                        .param("password", userNotExisted.getPassword())
                        .param("confirmedPassword", userNotExisted.getPassword() + "confirmed error")
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists(MsgAndContext.MODEL_ATTRIBUTES_ERR_MSG))
                .andExpect(MockMvcResultMatchers.view().name(Views.USER_CREATE));

        // User already existed. Conflict
        mockMvc.perform(MockMvcRequestBuilders.post(url)
                        .param("username", userNotExisted.getUsername())
                        .param("email",    userExisted.getEmail()) // Look here, ID already exist.
                        .param("password", userNotExisted.getPassword())
                        .param("confirmedPassword", userNotExisted.getPassword() + "confirmed error")
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists(MsgAndContext.MODEL_ATTRIBUTES_ERR_MSG))
                .andExpect(MockMvcResultMatchers.view().name(Views.USER_CREATE));

        // Create user correctly
        mockMvc.perform(MockMvcRequestBuilders.post(url)
                            .param("username",          userNotExisted.getUsername())
                            .param("email", userNotExisted.getEmail())
                            .param("password",          userNotExisted.getPassword())
                            .param("confirmedPassword", userNotExisted.getPassword())
                        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name(Views.USER_SHOW));
    }

    @Test
    public void modifyTest() throws Exception{
        String url = URLs.USERS + URLs.MODIFY + "/" + userExisted.getEmail();

        mockMvc.perform(MockMvcRequestBuilders.get(url))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists(MsgAndContext.MODEL_ATTRIBUTES_USER))
                .andExpect(MockMvcResultMatchers.view().name(Views.USER_MODIFY));


        url = URLs.USERS + URLs.MODIFY + URLs.UPDATE;

        String newValue = "NewName";
        mockMvc.perform(MockMvcRequestBuilders.post(url)
                        .param("username",          newValue)
                        .param("email",             userExisted.getEmail())
                        .param("password",          userExisted.getPassword())
                        .param("confirmedPassword", userExisted.getPassword())
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists(MsgAndContext.MODEL_ATTRIBUTES_USER))
                .andExpect(MockMvcResultMatchers.view().name(Views.USER_SHOW));

        User userAfterModify = userService.getById(userExisted.getEmail());
        Assert.assertTrue(userAfterModify.getUsername().equals(newValue));

    }

    @Test
    public void deleteTest() throws Exception{

        String url = URLs.USERS + URLs.DELETE + "/" + userExisted.getEmail();

        mockMvc.perform(MockMvcRequestBuilders.post(url))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeDoesNotExist(MsgAndContext.MODEL_ATTRIBUTES_ERR_MSG));

        Assert.assertTrue( userService.getById(userExisted.getEmail()) == null);
    }
}
