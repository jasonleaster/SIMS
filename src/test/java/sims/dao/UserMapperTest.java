package sims.dao;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import sims.exception.DaoExceptionChecker;
import sims.model.User;
import sims.util.SupplementaryDataFactory;
import sims.web.config.DataConfig;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:spring.xml", "classpath:spring-mybatis-test.xml"})
public class UserMapperTest extends DaoExceptionChecker {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserMapper userMapper;

    private User userExisted;
    private User userNotExisted;

    private int usersCountInDB;

    private User[] users;
    private String duplicatedName = "jack";

    @Before
    public void before(){

        Assert.assertTrue(dataSource != null);
        Assert.assertTrue(userMapper != null);

        DataConfig.initDB(dataSource);

        users          = SupplementaryDataFactory.getUsers();
        userNotExisted = users[0];
        userExisted    = users[1];

        /*
        * Make sure there are only two same name in the user database table.
        * */

        users[1].setUsername(duplicatedName);
        users[2].setUsername(duplicatedName);

        users[3].setUsername(duplicatedName + "A");
        users[4].setUsername(duplicatedName + "B");


        for (int i = 1; i < users.length; i++){
            userMapper.insert(users[i]);
        }
        usersCountInDB = users.length - 1;
    }

    @Test
    public void insertTest() throws Exception{
        // Can't insert null
        try{
            userMapper.insert(null);
        }catch (Exception ignore){
            this.setExceptionHappened(true);
        }

        exceptionHappenedChecker();

        // Can't insert an user with duplicated primary key
        try{
            userMapper.insert(userExisted);
        }catch (Exception e){
            this.setExceptionHappened(true);
        }
        exceptionHappenedChecker();

        // Should work correctly.
        userMapper.insert(userNotExisted);
    }

    @Test
    public void deleteByPrimaryKeyTest(){
        userMapper.deleteByPrimaryKey(userNotExisted.getEmail());

        // Should work correctly.
        userMapper.deleteByPrimaryKey(userExisted.getEmail());
    }

    @Test
    public void updateByPrimaryKeyTest(){
        String newname = "Martin";
        String newPassword = "newPassword";
        userExisted.setUsername(newname);
        userExisted.setPassword(newPassword);

        userMapper.updateByPrimaryKey(userExisted);

        User userInDB = userMapper.selectByPrimaryKey(userExisted.getEmail());

        Assert.assertTrue( userInDB.getUsername().equals(newname) );
        Assert.assertTrue(userInDB.getPassword().equals(newPassword));

        userMapper.updateByPrimaryKey(null);
    }

    @Test
    public void selectByPrimaryKeyTest(){

        User userInDB = userMapper.selectByPrimaryKey(userExisted.getEmail());

        Assert.assertTrue(userInDB != null);

        userInDB      = userMapper.selectByPrimaryKey(userNotExisted.getEmail());

        Assert.assertTrue(userInDB == null);
    }

    @Test
    public void selectFuzzyTest(){
        Map selectKeyWords = new HashMap();

        List<User> users = userMapper.selectFuzzy(null);

        Assert.assertTrue(users.size() == usersCountInDB);

        selectKeyWords.put("userType", User.UserType.NORMAL_USER.ordinal());

        users = userMapper.selectFuzzy(selectKeyWords);

        for (User user: users){
            Assert.assertTrue(user.getUserType() == User.UserType.NORMAL_USER.ordinal());
        }

        int counts = userMapper.selectItemCount(selectKeyWords);
        Assert.assertTrue( counts == usersCountInDB);
    }

    @Test
    public void selectItemCountTest(){
        int counts = userMapper.selectItemCount(null);

        Assert.assertTrue( counts == this.usersCountInDB);

        Map selectKeyWords = new HashMap();

        selectKeyWords.put("email", userExisted.getEmail());

        counts = userMapper.selectItemCount(selectKeyWords);

        Assert.assertTrue(counts == 1);

        selectKeyWords.clear();

        selectKeyWords.put("username", this.duplicatedName);

        counts = userMapper.selectItemCount(selectKeyWords);

        Assert.assertTrue(counts == 2);
    }

    @Test
    public void countAllTest(){
        int counts = userMapper.countAll();

        Assert.assertTrue( counts == usersCountInDB);
    }
}
