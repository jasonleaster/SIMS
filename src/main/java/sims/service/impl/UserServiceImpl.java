package sims.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sims.dao.UserMapper;
import sims.model.Book;
import sims.model.User;
import sims.service.UserService;
import sims.util.PageInfo;
import sims.web.BaseDomain;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*
 * This annotation will help to sign a business logic component
 */

@Service("userService")
public class UserServiceImpl extends BaseDomain implements UserService {

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
    public List<User> pagedFuzzyQuery(User user, PageInfo pageInfo) throws Exception {
        List<User> users = new ArrayList<>();

        if(user == null){
            return users;
        }

        if(user.getEmail() != null){
            users.add(this.getById(user.getEmail()));
            return users;
        }

        HashMap map = new HashMap();

        Field[] fields = user.getClass().getDeclaredFields();
        for(Field field: fields){
            field.setAccessible(true);
            map.put(field.getName(), field.get(user));
        }

        fields = pageInfo.getClass().getDeclaredFields();
        for(Field field: fields){
            field.setAccessible(true);
            map.put(field.getName(), field.get(pageInfo));
        }

        users = userMapper.selectFuzzy(map);

        int totalCount = userMapper.selectItemCount(map);
        pageInfo.setTotalCount(totalCount);

        return users;
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
