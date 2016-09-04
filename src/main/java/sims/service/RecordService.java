package sims.service;

import sims.model.Record;
import sims.model.User;

import java.util.List;

public interface RecordService {
    public Record getById(int id);

    public List<Record> getByUserId(String userId);

    public List<Record> getByBookId(String isbn);

    public void add(Record record);

    public void delete(int id);

    public void modify(Record record);

    public int countRecords();
}
