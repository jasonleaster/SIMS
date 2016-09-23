package sims.dao;

import sims.model.Record;

import java.util.List;

public interface RecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Record record);

    int insertSelective(Record record);

    Record selectByPrimaryKey(Integer id);

    List<Record> selectByUserId(String userId);

    List<Record> selectByBookId(String bookId);

    List<Record> selectByRecordType(String type);

    int updateByPrimaryKeySelective(Record record);

    int updateByPrimaryKey(Record record);
}