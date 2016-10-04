package sims.dao;

import sims.model.Record;

import java.util.List;
import java.util.Map;

public interface RecordMapper {

    int insert(Record record);

    int deleteByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Record record);

    Record selectByPrimaryKey(Integer id);

    List<Record> selectFuzzy(Map map);

    int selectItemCount(Map map);

    int countAll();
}