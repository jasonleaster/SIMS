package sims.service.impl;

import org.springframework.stereotype.Service;
import sims.model.Record;
import sims.service.RecordService;

@Service("recordService")
public class RecordServiceImpl implements RecordService {
    @Override
    public Record getById(int id) {
        return null;
    }

    @Override
    public void add(Record record) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void modify(Record record) {

    }

    @Override
    public int countRecords() {
        return 0;
    }
}
