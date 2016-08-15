package sims.dao;

import sims.model.User;

public interface UserMapper {

    int deleteByPrimaryKey(String email);

    int insert(User user);

    int insertSelective(User user);

    User selectByPrimaryKey(String email);

    int updateByPrimaryKeySelective(User user);

    int updateByPrimaryKey(User user);
}