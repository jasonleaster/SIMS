package org.sims.service;

import org.sims.model.Record;
import org.sims.util.PageInfo;

import java.util.List;

public interface RecordService {
    public void init();

    public Record getById(int id);

    public List<Record> pagedFuzzyQuery(Record record, PageInfo pageInfo) throws Exception;

    public void add(Record record);

    public void delete(int id);

    public void modify(Record record);

    public long totalCountInDB();
}
