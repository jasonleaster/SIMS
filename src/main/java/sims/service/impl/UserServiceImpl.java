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

    public UserMapper getUserMapper() {
        return userMapper;
    }

    @Override
    public User getById(String id) {
        User user = userMapper.selectByPrimaryKey(id);
        return user;
    }

    @Override
    public void add(User user) {
        userMapper.insert(user);
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
}
