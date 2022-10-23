package org.sims.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sims.model.User;
import org.sims.util.SupplementaryDataFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


@SpringBootTest
public class UserMapperTest {

    @Autowired
    private TestUserMapper testUserMapper;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserMapper userMapper;

    private User userExisted;
    private User userNotExisted;

    private int usersCountInDB;

    private User[] users;
    private String duplicatedName = "jack";

    @BeforeEach
    public void before(){

        Assertions.assertTrue(dataSource != null);
        Assertions.assertTrue(userMapper != null);
    }

    @Test
    public void insertTest() throws Exception{
        // Can't insert null
        try{
            userMapper.insert(null);
        }catch (Exception ignore){
//            this.setExceptionHappened(true);
        }

//        exceptionHappenedChecker();

        // Can't insert an user with duplicated primary key
        try{
            userMapper.insert(userExisted);
        }catch (Exception e){
//            this.setExceptionHappened(true);
        }
//        exceptionHappenedChecker();

        // Should work correctly.
        userMapper.insert(userNotExisted);

        List<User> usersList = new LinkedList<>();
        for(User user : users){
            usersList.add(user);
        }
        userMapper.insertUsers(usersList);
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

        Assertions.assertTrue( userInDB.getUsername().equals(newname) );
        Assertions.assertTrue(userInDB.getPassword().equals(newPassword));

        userMapper.updateByPrimaryKey(null);
    }

    @Test
    public void selectByPrimaryKeyTest(){

        User userInDB = userMapper.selectByPrimaryKey(userExisted.getEmail());

        Assertions.assertTrue(userInDB != null);

        userInDB      = userMapper.selectByPrimaryKey(userNotExisted.getEmail());

        Assertions.assertTrue(userInDB == null);
    }

    @Test
    public void selectFuzzyTest(){
        Map selectKeyWords = new HashMap();

        List<User> users = userMapper.selectFuzzy(null);

        Assertions.assertTrue(users.size() == usersCountInDB);

        selectKeyWords.put("userType", User.UserType.NORMAL_USER.ordinal());

        users = userMapper.selectFuzzy(selectKeyWords);

        for (User user: users){
            Assertions.assertTrue(user.getUserType() == User.UserType.NORMAL_USER.ordinal());
        }

        int counts = userMapper.selectItemCount(selectKeyWords);
        Assertions.assertTrue( counts == usersCountInDB);
    }

    @Test
    public void selectItemCountTest(){
        int counts = userMapper.selectItemCount(null);

        Assertions.assertTrue( counts == this.usersCountInDB);

        Map selectKeyWords = new HashMap();

        selectKeyWords.put("email", userExisted.getEmail());

        counts = userMapper.selectItemCount(selectKeyWords);

        Assertions.assertTrue(counts == 1);

        selectKeyWords.clear();

        selectKeyWords.put("username", this.duplicatedName);

        counts = userMapper.selectItemCount(selectKeyWords);

        Assertions.assertTrue(counts == 2);
    }

    @Test
    public void countAllTest(){
        int counts = userMapper.countAll();

        Assertions.assertTrue( counts == usersCountInDB);
    }

    @Test
    public void performanceCompare() {
//        DataConfig.initDB(dataSource);

        List<User> users = SupplementaryDataFactory.getHugeNumberOfUsersForTesting(100);

        long start = System.currentTimeMillis();
        long end   = start;
        /*
         * Proposal Solution 1:
         *      It's a bad practice to insert huge number
         *      of items into table one by one.
         * */
        for(User user: users) {
            userMapper.insert(user);
        }
        end = System.currentTimeMillis();

        System.out.println("Solution One cost time: " + (end - start));

//        DataConfig.initDB(dataSource);

        start = System.currentTimeMillis();
        userMapper.insertUsers(users);
        end = System.currentTimeMillis();

        System.out.println("Solution Two cost time: " + (end - start));

    }

    @Test
    public void testMyBatisMapper() {
        User user = new User();
        user.setUsername("张三");
        user.setUserType(1);
        user.setPassword("123");
        user.setEmail("jasonleaster@gmail.com");

        testUserMapper.insert(user);
    }
}
