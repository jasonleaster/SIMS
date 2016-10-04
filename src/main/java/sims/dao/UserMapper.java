package sims.dao;

import sims.model.Record;
import sims.model.User;

import java.util.List;
import java.util.Map;

public interface UserMapper {

    int insert(User user);

    int deleteByPrimaryKey(String email);

    int updateByPrimaryKey(User user);

    int insertSelective(User user);

    User selectByPrimaryKey(String email);

    List<Record> selectFuzzy(Map map);

    int selectItemCount(Map map);

    int countAll();
}