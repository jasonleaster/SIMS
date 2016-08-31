package sims.service;

import sims.model.Record;

public interface RecordService {
    public Record getById(int id);

    public void add(Record record);

    public void delete(int id);

    public void modify(Record record);

    public int countRecords();
}
