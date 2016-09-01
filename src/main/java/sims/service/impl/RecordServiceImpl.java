package sims.service.impl;

import org.springframework.stereotype.Service;
import sims.model.Record;
import sims.service.RecordService;

@Service("recordService")
public class RecordServiceImpl implements RecordService {

    RecordService service;
    @Override
    public Record getById(int id) {
        Record record = service.getById(id);
        return record;
    }

    @Override
    public void add(Record record) {
        service.add(record);
    }

    @Override
    public void delete(int id) {
        service.delete(id);
    }

    @Override
    public void modify(Record record) {
        service.modify(record);
    }

    @Override
    public int countRecords() {
        return service.countRecords();
    }
}
