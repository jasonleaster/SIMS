package org.sims.adapter;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.sims.form.RegisterForm;
import org.sims.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserControllerTest {
    @Autowired
    private UserController userController;

    /**
     * Method under test: {@link UserController#searchGet()}
     */
    @Test
    void testSearchGet() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R027 Missing beans when creating Spring context.
        //   Failed to create Spring context due to missing beans
        //   in the current Spring profile:
        //       io.mybatis.service.AbstractService
        //   See https://diff.blue/R027 to resolve this issue.

        assertEquals("user/search", userController.searchGet());
    }

    /**
     * Method under test: {@link UserController#query(Model)}
     */
    @Test
    void testQuery() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R027 Missing beans when creating Spring context.
        //   Failed to create Spring context due to missing beans
        //   in the current Spring profile:
        //       io.mybatis.service.AbstractService
        //   See https://diff.blue/R027 to resolve this issue.

        UserController userController = new UserController();
        assertEquals("user/create", userController.query(new ConcurrentModel()));
    }

    /**
     * Method under test: {@link UserController#createGet(Model)}
     */
    @Test
    void testCreateGet() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R027 Missing beans when creating Spring context.
        //   Failed to create Spring context due to missing beans
        //   in the current Spring profile:
        //       io.mybatis.service.AbstractService
        //   See https://diff.blue/R027 to resolve this issue.

        UserController userController = new UserController();
        assertEquals("user/create", userController.createGet(new ConcurrentModel()));
    }

    /**
     * Method under test: {@link UserController#createPost(RegisterForm, Model, BindingResult)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testCreatePost() throws Exception {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R027 Missing beans when creating Spring context.
        //   Failed to create Spring context due to missing beans
        //   in the current Spring profile:
        //       io.mybatis.service.AbstractService
        //   See https://diff.blue/R027 to resolve this issue.

        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException
        //       at org.sims.controller.UserController.createPost(UserController.java:77)
        //   See https://diff.blue/R013 to resolve this issue.

        UserController userController = new UserController();
        RegisterForm form = new RegisterForm("janedoe", "jane.doe@example.org", "iloveyou", "iloveyou");

        ConcurrentModel model = new ConcurrentModel();
        userController.createPost(form, model, new BindException("Target", "Object Name"));
    }

    /**
     * Method under test: {@link UserController#createPost(RegisterForm, Model, BindingResult)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testCreatePost2() throws Exception {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R027 Missing beans when creating Spring context.
        //   Failed to create Spring context due to missing beans
        //   in the current Spring profile:
        //       io.mybatis.service.AbstractService
        //   See https://diff.blue/R027 to resolve this issue.

        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException
        //       at org.sims.controller.UserController.createPost(UserController.java:74)
        //   See https://diff.blue/R013 to resolve this issue.

        UserController userController = new UserController();
        ConcurrentModel model = new ConcurrentModel();
        userController.createPost(null, model, new BindException("Target", "Object Name"));
    }

    /**
     * Method under test: {@link UserController#createPost(RegisterForm, Model, BindingResult)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testCreatePost3() throws Exception {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R027 Missing beans when creating Spring context.
        //   Failed to create Spring context due to missing beans
        //   in the current Spring profile:
        //       io.mybatis.service.AbstractService
        //   See https://diff.blue/R027 to resolve this issue.

        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException
        //       at org.sims.controller.UserController.createPost(UserController.java:77)
        //   See https://diff.blue/R013 to resolve this issue.

        UserController userController = new UserController();
        RegisterForm registerForm = mock(RegisterForm.class);
        when(registerForm.toUser()).thenReturn(new User("janedoe", "jane.doe@example.org", "iloveyou"));
        ConcurrentModel model = new ConcurrentModel();
        userController.createPost(registerForm, model, new BindException("Target", "Object Name"));
    }

    /**
     * Method under test: {@link UserController#createPost(RegisterForm, Model, BindingResult)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testCreatePost4() throws Exception {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R027 Missing beans when creating Spring context.
        //   Failed to create Spring context due to missing beans
        //   in the current Spring profile:
        //       io.mybatis.service.AbstractService
        //   See https://diff.blue/R027 to resolve this issue.

        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException
        //       at org.sims.controller.UserController.createPost(UserController.java:75)
        //   See https://diff.blue/R013 to resolve this issue.

        UserController userController = new UserController();
        RegisterForm registerForm = mock(RegisterForm.class);
        when(registerForm.toUser()).thenReturn(null);
        ConcurrentModel model = new ConcurrentModel();
        userController.createPost(registerForm, model, new BindException("Target", "Object Name"));
    }

    /**
     * Method under test: {@link UserController#createPost(RegisterForm, Model, BindingResult)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testCreatePost5() throws Exception {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R027 Missing beans when creating Spring context.
        //   Failed to create Spring context due to missing beans
        //   in the current Spring profile:
        //       io.mybatis.service.AbstractService
        //   See https://diff.blue/R027 to resolve this issue.

        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException
        //       at org.sims.controller.UserController.createPost(UserController.java:70)
        //   See https://diff.blue/R013 to resolve this issue.

        UserController userController = new UserController();
        RegisterForm registerForm = mock(RegisterForm.class);
        when(registerForm.toUser()).thenReturn(new User("janedoe", "jane.doe@example.org", "iloveyou"));
        userController.createPost(registerForm, new ConcurrentModel(), null);
    }

    /**
     * Method under test: {@link UserController#createPost(RegisterForm, Model, BindingResult)}
     */
    @Test
    void testCreatePost6() throws Exception {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R027 Missing beans when creating Spring context.
        //   Failed to create Spring context due to missing beans
        //   in the current Spring profile:
        //       io.mybatis.service.AbstractService
        //   See https://diff.blue/R027 to resolve this issue.

        UserController userController = new UserController();
        RegisterForm registerForm = mock(RegisterForm.class);
        when(registerForm.toUser()).thenReturn(new User("janedoe", "jane.doe@example.org", "iloveyou"));
        ConcurrentModel model = new ConcurrentModel();

        BindException bindException = new BindException("Target", "Object Name");
        bindException.addError(new ObjectError("Object Name", "Default Message"));
        assertEquals("login", userController.createPost(registerForm, model, bindException));
    }

    /**
     * Method under test: {@link UserController#modifyUpdate(User, Model, BindingResult)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testModifyUpdate() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R027 Missing beans when creating Spring context.
        //   Failed to create Spring context due to missing beans
        //   in the current Spring profile:
        //       io.mybatis.service.AbstractService
        //   See https://diff.blue/R027 to resolve this issue.

        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException
        //       at org.sims.controller.UserController.modifyUpdate(UserController.java:109)
        //   See https://diff.blue/R013 to resolve this issue.

        UserController userController = new UserController();
        User user = new User("janedoe", "jane.doe@example.org", "iloveyou");

        ConcurrentModel model = new ConcurrentModel();
        userController.modifyUpdate(user, model, new BindException("Target", "Object Name"));
    }

    /**
     * Method under test: {@link UserController#modifyUpdate(User, Model, BindingResult)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testModifyUpdate2() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R027 Missing beans when creating Spring context.
        //   Failed to create Spring context due to missing beans
        //   in the current Spring profile:
        //       io.mybatis.service.AbstractService
        //   See https://diff.blue/R027 to resolve this issue.

        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException
        //       at org.sims.controller.UserController.modifyUpdate(UserController.java:109)
        //   See https://diff.blue/R013 to resolve this issue.

        UserController userController = new UserController();
        User user = mock(User.class);
        ConcurrentModel model = new ConcurrentModel();
        userController.modifyUpdate(user, model, new BindException("Target", "Object Name"));
    }

    /**
     * Method under test: {@link UserController#modifyUpdate(User, Model, BindingResult)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testModifyUpdate3() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R027 Missing beans when creating Spring context.
        //   Failed to create Spring context due to missing beans
        //   in the current Spring profile:
        //       io.mybatis.service.AbstractService
        //   See https://diff.blue/R027 to resolve this issue.

        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException
        //       at org.sims.controller.UserController.modifyUpdate(UserController.java:106)
        //   See https://diff.blue/R013 to resolve this issue.

        UserController userController = new UserController();
        User user = new User("janedoe", "jane.doe@example.org", "iloveyou");

        userController.modifyUpdate(user, new ConcurrentModel(), null);
    }

    /**
     * Method under test: {@link UserController#modifyUpdate(User, Model, BindingResult)}
     */
    @Test
    void testModifyUpdate4() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R027 Missing beans when creating Spring context.
        //   Failed to create Spring context due to missing beans
        //   in the current Spring profile:
        //       io.mybatis.service.AbstractService
        //   See https://diff.blue/R027 to resolve this issue.

        UserController userController = new UserController();
        User user = new User("janedoe", "jane.doe@example.org", "iloveyou");

        ConcurrentModel model = new ConcurrentModel();
        BeanPropertyBindingResult beanPropertyBindingResult = mock(BeanPropertyBindingResult.class);
        when(beanPropertyBindingResult.hasErrors()).thenReturn(true);
        assertEquals("user/modify", userController.modifyUpdate(user, model, beanPropertyBindingResult));
        verify(beanPropertyBindingResult).hasErrors();
    }

    /**
     * Method under test: {@link UserController#delete(String, Model)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testDelete() throws Exception {
        // TODO: Complete this test.
        //   Reason: R027 Missing beans when creating Spring context.
        //   Failed to create Spring context due to missing beans
        //   in the current Spring profile:
        //       io.mybatis.service.AbstractService
        //   See https://diff.blue/R027 to resolve this issue.

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/users/delete/{id:.+}", "xxx");
        MockMvcBuilders.standaloneSetup(userController).build().perform(requestBuilder);
    }

    /**
     * Method under test: {@link UserController#modify(String, Model)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testModify() throws Exception {
        // TODO: Complete this test.
        //   Reason: R027 Missing beans when creating Spring context.
        //   Failed to create Spring context due to missing beans
        //   in the current Spring profile:
        //       io.mybatis.service.AbstractService
        //   See https://diff.blue/R027 to resolve this issue.

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/users/modify/{userID:.+}", "xxx");
        MockMvcBuilders.standaloneSetup(userController).build().perform(requestBuilder);
    }

    /**
     * Method under test: {@link UserController#searchPost(User, Model)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testSearchPost() throws Exception {
        // TODO: Complete this test.
        //   Reason: R027 Missing beans when creating Spring context.
        //   Failed to create Spring context due to missing beans
        //   in the current Spring profile:
        //       io.mybatis.service.AbstractService
        //   See https://diff.blue/R027 to resolve this issue.

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/users/search");
        MockMvcBuilders.standaloneSetup(userController).build().perform(requestBuilder);
    }
}

