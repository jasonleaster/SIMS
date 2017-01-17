package sims.dao;

import sims.model.Record;

import java.util.List;
import java.util.Map;

@SuppressWarnings("rawtypes")
public interface RecordMapper {

    int insert(Record record);

    int deleteByPrimaryKey(Integer id);

    /**
     * Upper Layyer will never use these method.
     *
     * int updateByPrimaryKeySelective(Record record);
     *
     *  Unimplemented method
     *
     *  int deleteFuzzy(Map map);
     */

    Record selectByPrimaryKey(Integer id);

    List<Record> selectFuzzy(Map map);

    int selectItemCount(Map map);

    int countAll();
}