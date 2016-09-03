package sims.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sims.dao.RecordMapper;
import sims.model.Record;
import sims.service.RecordService;


@Service("recordService")
public class RecordServiceImpl implements RecordService {

    @Autowired
    RecordMapper mapper;
    
    @Override
    public Record getById(int id) {
        Record record = mapper.selectByPrimaryKey(id);
        return record;
    }

    @Override
    public void add(Record record) {
        mapper.insert(record);
    }

    @Override
    public void delete(int id) {
        mapper.deleteByPrimaryKey(id);
    }

    @Override
    public void modify(Record record) {
        mapper.updateByPrimaryKey(record);
    }

    @Override
    public int countRecords() {
        return -1;
    }
}
