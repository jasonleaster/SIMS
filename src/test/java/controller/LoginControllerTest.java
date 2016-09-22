/*****************************************************
 * File name    : Book.java
 * Author       : Jason Leaster
 * Date         : 2016.07.25
 *
 * Description  :
 *      1. 用户登录的逻辑
 *          1.1 访问登录界面
 *          1.2 若未注册，提供link跳转到注册页面.
 *          1.3 如果已经注册，填写用户名ID 和 密码，
 *              选择是否短时间内免登录(由Cookie保存用户信息实现)
 *          1.4 后端服务器程序验证用户名和密码是否准确
 *          1.5 如果密码不准确则跳转回用户登录界面，提示账号和密码不匹配
 *          1.5 如果密码正确，将当前登录用户的ID信息保存在Session中，
 *              则跳转到网站主页面 HomePage
 *
 *      2. 用户登出的逻辑
 *          2.1 触发主页面导航栏的登出link
 *          2.2 获取当前Session信息的用户信息，并把当前用户从Session中删除
 *
 *****************************************************/
package controller;

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
import sims.controller.LoginController;
import sims.filter.FormFilter;
import sims.model.User;
import sims.service.UserService;
import sims.util.MsgAndContext;
import sims.util.URLs;
import sims.util.Views;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:spring.xml", "classpath:spring-mybatis.xml"})
public class LoginControllerTest {

    @Autowired
    WebApplicationContext ctx;

    @Autowired
    DriverManagerDataSource dataSource;

    @Autowired
    UserService userService;

    @Autowired
    LoginController controller;

    FormFilter filter = new FormFilter();

    MockMvc mockMvc;

    User userExisted;
    User userNotExisted;

    private void initDB(){
        Resource resource = new ClassPathResource("SQL/schema.sql");
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
        databasePopulator.addScript(resource);
        DatabasePopulatorUtils.execute(databasePopulator, dataSource);
    }

    @Before
    public void before(){
        mockMvc = MockMvcBuilders.webAppContextSetup(ctx).addFilters(filter).build();

        initDB();

        userExisted    = new User("Eric", "eric@gmail.com", "ericpassword");
        userNotExisted = new User("Jack", "jack@gmail.com", "jackpassword");

        userService.add(userExisted);
    }

    @Test
    public void loginTest() throws Exception{

        /**
        * 控制器逻辑:
        *   1.用户通过 url = URLs.LOGIN 向服务器发出GET请求
        *   2.服务端返回对应的视图 view = Views.LOGIN
        * */
        mockMvc.perform(MockMvcRequestBuilders.get(URLs.LOGIN))
                .andExpect(MockMvcResultMatchers.view().name(Views.LOGIN));


        /**
        * 控制器逻辑:
        *   1.用户通过 form 表单的形式向 url = URLs.LOGIN 发起POST请求
        *   2.表单的内容与 sims.form.LoginForm 类的各个field相对应
        *   3.服务端对该用户信息进行必要检查，如果用户不存在于数据库中，
        *   则返回的相应响应状态应该为 200 (Status OK) 并返回相应的视图
        *   提示用户注册后再登陆
        * */
        mockMvc.perform(MockMvcRequestBuilders.post(URLs.LOGIN)
                        .param("username", userExisted.getUsername())
                        .param("email", userExisted.getEmail())
                        .param("password", userExisted.getPassword())
                        .param("rememberMe", "1")
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists(MsgAndContext.MODEL_ATTRIBUTES_USER))
                .andExpect(MockMvcResultMatchers.view().name(Views.HOME));

        // User Not Exist
        mockMvc.perform(MockMvcRequestBuilders.post(URLs.LOGIN)
                        .param("username", userNotExisted.getUsername())
                        .param("email",    userNotExisted.getEmail())
                        .param("password", userNotExisted.getPassword())
                        .param("rememberme", "0") )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists(MsgAndContext.MODEL_ATTRIBUTES_ERR_MSG))
                .andExpect(MockMvcResultMatchers.view().name(Views.LOGIN));

        // User password error
        mockMvc.perform(MockMvcRequestBuilders.post(URLs.LOGIN)
                .param("username",  userExisted.getUsername())
                .param("email",     userExisted.getEmail())
                .param("password",  userNotExisted.getPassword())
                .param("rememberme", "0"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists(MsgAndContext.MODEL_ATTRIBUTES_ERR_MSG))
                .andExpect(MockMvcResultMatchers.view().name(Views.LOGIN));
    }

    @Test
    public void registerTest() throws Exception{
        /**
         * 控制器逻辑:
         *   1.用户通过 url = URLs.REGISTER 向服务器发出GET请求
         *   2.服务端返回对应的视图 view = Views.REGISTER
         * */
        mockMvc.perform( MockMvcRequestBuilders.get(URLs.REGISTER))
                .andExpect(MockMvcResultMatchers.view().name(Views.REGISTER));
        
        // User already exist.
        mockMvc.perform(MockMvcRequestBuilders.post(URLs.REGISTER)
                        .param("username",          userExisted.getUsername())
                        .param("email",             userExisted.getEmail())
                        .param("password",          userExisted.getPassword())
                        .param("confirmedPassword", userExisted.getPassword()) )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists(MsgAndContext.MODEL_ATTRIBUTES_ERR_MSG))
                .andExpect(MockMvcResultMatchers.view().name(Views.REGISTER));

        // Password confirmed error.
        mockMvc.perform(MockMvcRequestBuilders.post(URLs.REGISTER)
                .param("username",  userNotExisted.getUsername())
                .param("email",     userNotExisted.getEmail())
                .param("password",  userNotExisted.getPassword())
                .param("confirmedPassword", userNotExisted.getPassword() + "ErrChar") )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists(MsgAndContext.MODEL_ATTRIBUTES_ERR_MSG))
                .andExpect(MockMvcResultMatchers.view().name(Views.REGISTER));


        // Register correctly
        mockMvc.perform(MockMvcRequestBuilders.post(URLs.REGISTER)
                .param("username",          userNotExisted.getUsername())
                .param("email",             userNotExisted.getEmail())
                .param("password",          userNotExisted.getPassword())
                .param("confirmedPassword", userNotExisted.getPassword()) )
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.model().attributeDoesNotExist(MsgAndContext.MODEL_ATTRIBUTES_ERR_MSG))
                .andExpect(MockMvcResultMatchers.model().attributeExists(MsgAndContext.MODEL_ATTRIBUTES_USER))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/" + Views.LOGIN));
    }

    @Test
    public void logoutTest() throws Exception{

        mockMvc.perform(MockMvcRequestBuilders.post(URLs.LOGIN)
                        .param("username", userExisted.getUsername())
                        .param("email",    userExisted.getEmail())
                        .param("password", userExisted.getPassword())
                        .param("rememberMe", "1") )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists(MsgAndContext.MODEL_ATTRIBUTES_USER))
                .andExpect(MockMvcResultMatchers.view().name(Views.HOME));

        mockMvc.perform(MockMvcRequestBuilders.get(URLs.LOGOUT))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeDoesNotExist(MsgAndContext.SESSION_CONTEXT_USER));
    }

}
