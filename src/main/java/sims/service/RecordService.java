package sims.service;

import sims.form.BookSearchForm;
import sims.model.Book;
import sims.model.Record;
import sims.model.User;
import sims.util.PageInfo;

import java.util.List;

public interface RecordService {
    public Record getById(int id);

    public List<Record> pagedFuzzyQuery(Record record, PageInfo pageInfo) throws Exception;

    public void add(Record record);

    public void delete(int id);

    public void modify(Record record);

    public int countRecords();
}
