package org.sims.service;

import org.sims.exception.DaoExceptionChecker;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.sims.model.Book;
import org.sims.model.Record;
import org.sims.model.User;
import org.sims.util.SupplementaryDataFactory;
import org.sims.web.config.DataConfig;

import javax.sql.DataSource;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:spring.xml", "classpath:spring-mybatis-test.xml"})
public class RecordServiceTest extends DaoExceptionChecker{
    @Autowired
    private DataSource dataSource;

    @Autowired
    private RecordService recordService;

    @Autowired
    private UserService  userService;

    @Autowired
    private BookService bookService;

    private Record[] records;
    private Record   recordExisted;
    private Record   recordNotExisted;

    private Book[] books;
    private User[] users;

    private int recordsCountInDB;

    @Before
    public void before() throws Exception{
        DataConfig.initDB(dataSource);

        records = SupplementaryDataFactory.getRecords();
        users   = SupplementaryDataFactory.getUsers();
        books   = SupplementaryDataFactory.getBooks();

        recordNotExisted = records[0];
        recordExisted    = records[1];

        for(Book book : books){
            bookService.add(book);
        }

        for (User user : users){
            userService.add(user);
        }

        for(int i = 1; i < records.length; i++){
            recordService.add(records[i]);
        }

        recordsCountInDB = records.length - 1;

        recordService.init(); // Re-initialize the org.sims.service of book.
    }

    @Test
    public void getByIdTest() throws Exception{

        Record recordInDB = recordService.getById(-1);

        Assert.assertTrue(recordInDB == null);

        List<Record> allRecordsInDB = recordService.pagedFuzzyQuery(null, null);

        Record recordExpected = allRecordsInDB.get(0);
        recordInDB = recordService.getById(recordExpected.getId());

        Assert.assertTrue(recordInDB.equals(recordExpected));

    }

    @Test
    public void addTest(){
        recordService.add(null);

        recordService.add(recordExisted); // This will add a same record except the id.

        recordService.add(recordNotExisted);

        Assert.assertTrue(recordService.totalCountInDB() == (recordsCountInDB + 2));
    }
    @Test
    public void deleteTest() throws Exception{
        recordService.delete(-1);

        List<Record> records = recordService.pagedFuzzyQuery(null, null);

        for(Record record: records){
            recordService.delete(record.getId());
        }

        for(Record record: records){
            Assert.assertTrue( recordService.getById(record.getId()) == null);
        }
    }

    @Test
    public void modifyTest(){
        // Once the record generated, it can't be modified.
        //public void modify(Record record);
    }

    @Test
    public void totalCountInDBTest(){
        long counts = recordService.totalCountInDB();

        Assert.assertTrue(counts == recordsCountInDB);
    }

    @Test
    public void pagedFuzzyQueryTest(){
        List<Record> records = null;
        try {
            records = recordService.pagedFuzzyQuery(null, null);
        }catch (Exception e){
            this.setExceptionHappened(true);
        }
        this.exceptionNotHappenedChecker();

        Assert.assertTrue(records != null && records.size() == recordsCountInDB);
    }
}
