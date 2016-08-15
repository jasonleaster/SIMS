package controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import sims.controller.LoginController;
import sims.util.URLs;
import sims.util.Views;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:spring.xml", "classpath:spring-mybatis.xml",
        "classpath:spring-mvc.xml"})
public class LoginControllerTest {

    @Autowired
    LoginController controller; // Have to do this autowiring
    MockMvc mockMvc;

    @Before
    public void before(){
         mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void loginTest() throws Exception{
        /**
        * 控制器逻辑:
        *   1.用户通过 url = URLs.LOGIN 向服务器发出GET请求
        *   2.服务端返回对应的视图 view = Views.LOGIN
        * */
        mockMvc.perform( MockMvcRequestBuilders
                .get(URLs.LOGIN))
                .andExpect(MockMvcResultMatchers.view().name(Views.LOGIN));


        /**
        * 控制器逻辑:
        *   1.用户通过 form 表单的形式向 url = URLs.LOGIN 发起POST请求
        *   2.表单的内容与 sims.model.User 类的各个field相对应
        *   3.服务端对该用户信息进行必要检查，如果用户不存在于数据库中，
        *   则返回的相应响应状态应该为 200 (Status OK) 并返回相应的视图
        *   提示用户注册后再登陆
        * */
        mockMvc.perform(MockMvcRequestBuilders.post(URLs.LOGIN)
                        .param("username", "jackson")
                        .param("email", "jackson@gmail.com")
                        .param("password", "123456")
                                                            )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name(Views.REGISTER));
    }

    @Test
    public void registerTest() throws Exception{
        mockMvc.perform( MockMvcRequestBuilders
                .get(URLs.REGISTER))
                .andExpect(MockMvcResultMatchers.view().name(Views.REGISTER));

        mockMvc.perform(MockMvcRequestBuilders.post(URLs.REGISTER)
                        .param("username", "jackson")
                        .param("email", "jackson@gmail.com")
                        .param("password", "123456")
                        .param("confirmedPassword", "123456")
        )
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }

}
