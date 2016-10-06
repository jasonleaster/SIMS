package sims.service;

import sims.exception.DaoExceptionChecker;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import sims.exception.DuplicatedPrimaryKeyException;
import sims.model.User;
import sims.util.PageInfo;
import sims.util.SupplementaryDataFactory;
import sims.web.config.DataConfig;

import javax.sql.DataSource;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:spring.xml", "classpath:spring-mybatis-test.xml"})
public class UserServiceTest extends DaoExceptionChecker{
    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserService userService;

    private User[] users;
    private User   userExisted;
    private User   userNotExisted;

    private int usersCountInDB;

    @Before
    public void before() throws Exception{
        DataConfig.initDB(dataSource);

        users = SupplementaryDataFactory.getUsers();

        userNotExisted = users[0];
        userExisted    = users[1];

        for(int i = 1; i < users.length; i++){
            userService.add(users[i]);
        }

        usersCountInDB = users.length - 1;

        userService.init(); // Re-initialize the sims.service of book.
    }

    @Test
    public void addTest() throws Exception{
        userService.add(null);

        try {
            userService.add(userExisted);
        }catch (DuplicatedPrimaryKeyException e){
            this.setExceptionHappened(true);
        }
        exceptionHappenedChecker();

        try {
            userService.add(userNotExisted);
        }catch (DuplicatedPrimaryKeyException e){
            this.setExceptionHappened(true);
        }
        exceptionNotHappenedChecker();

        User userInDB = userService.getById(userNotExisted.getEmail());

        Assert.assertTrue(userInDB != null && userInDB.equals(userNotExisted));
    }

    @Test
    public void deleteTest(){
        userService.delete(null);

        userService.delete(userNotExisted.getEmail());

        userService.delete(userExisted.getEmail());

        User userInDB = userService.getById(userExisted.getEmail());

        Assert.assertTrue(userInDB == null);
    }

    @Test
    public void getByIdTest(){
        User userInDB = userService.getById(null);

        Assert.assertTrue(userInDB == null);

        userInDB = userService.getById(userNotExisted.getEmail());

        Assert.assertTrue(userInDB == null);

        userInDB = userService.getById(userExisted.getEmail());

        Assert.assertTrue(userInDB != null && userInDB.equals(userExisted));

    }

    @Test
    public void pagedFuzzyQueryTest() throws Exception{
        User form = new User();
        form.setUserType(User.UserType.NORMAL_USER.ordinal());

        PageInfo pageInfo = new PageInfo(0, 2, null);

        List<User> users = userService.pagedFuzzyQuery(form, pageInfo);

        Assert.assertTrue(users.size() == 2);

        for(User user:users){
            Assert.assertTrue(user.getUserType() == User.UserType.NORMAL_USER.ordinal());
        }

        // No constraints
        users = userService.pagedFuzzyQuery(null, null);

        Assert.assertTrue(users.size() == usersCountInDB);
    }

    @Test
    public void modifyTest(){
        userService.modify(null);

        userService.modify(userNotExisted);

        userExisted.setUsername("NewName");

        userService.modify(userExisted);

        User userInDB = userService.getById(userExisted.getEmail());

        Assert.assertTrue(userInDB != null && userInDB.equals(userExisted));
    }

    @Test
    public void totalCountInDBTest(){
        long counts = userService.totalCountInDB();

        Assert.assertTrue(counts == usersCountInDB);
    }
}
