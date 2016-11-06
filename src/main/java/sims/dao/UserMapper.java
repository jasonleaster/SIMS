package sims.dao;

import sims.model.User;

import java.util.List;
import java.util.Map;

@SuppressWarnings("rawtypes")
public interface UserMapper {

    int insert(User user);

    int deleteByPrimaryKey(String email);

    int updateByPrimaryKey(User user);

    /*
    * All fields in class User can NOT be null when the system
    * is going to create a new user or modify it.
    *
    * This character is determined by the script(resources/SQL/schema.sql)
    * which created the database.
    * Please use UserMapper#insert but not this method or try to implement it.
    * It will violate the constraint of the database.
    * */
    // int insertSelective(User user);

    User selectByPrimaryKey(String email);

    List<User> selectFuzzy(Map map);

    int selectItemCount(Map map);

    int countAll();
}