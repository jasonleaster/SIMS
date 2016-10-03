package sims.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sims.dao.UserMapper;
import sims.model.User;
import sims.service.UserService;

/*
 * This annotation will help to sign a bussiness logic component
 */

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    static private long usersNumInDB;

    public UserMapper getUserMapper() {
        return userMapper;
    }

    @Override
    public User getById(String id) {
        if(id == null){
            return null;
        }

        User user = null;
        try{
            user = userMapper.selectByPrimaryKey(id);
        }catch (Exception ignore){}

        return user;
    }

    @Override
    public void add(User user) {
        if(getById(user.getEmail()) == null){
            userMapper.insert(user);
        }
    }

    @Override
    public void delete(String id) {
        userMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void modify(User user) {
        userMapper.updateByPrimaryKey(user);
    }

    @Override
    public int countUser() {
        return -1;
    }

    public static long getUsersNumInDB() {
        return usersNumInDB;
    }

    public static void setUsersNumInDB(long usersNumInDB) {
        UserServiceImpl.usersNumInDB = usersNumInDB;
    }
}
