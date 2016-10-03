package sims.dao;

import sims.model.Record;

import java.util.List;
import java.util.Map;

public interface RecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Record record);

    int insertSelective(Record record);

    Record selectByPrimaryKey(Integer id);

    List<Record> selectFuzzy(Map map);

    int selectItemCount(Map map);

    int countAll();

    int updateByPrimaryKeySelective(Record record);

    int updateByPrimaryKey(Record record);
}