package org.sims.dao;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.sims.exception.DaoExceptionChecker;
import org.sims.model.Book;
import org.sims.model.Record;
import org.sims.model.User;
import org.sims.util.SupplementaryDataFactory;
import org.sims.web.config.DataConfig;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;

@SuppressWarnings({"rawtypes", "unchecked"})
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:spring.xml", "classpath:spring-mybatis-test.xml"})
public class RecordMapperTest extends DaoExceptionChecker {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private RecordMapper recordMapper;

    /*
     * We need the help from #UserMapper and #BookMapper to finish the
     * test task of RecordMapper
     * */
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BookMapper bookMapper;

    private Record[] records;
    private Record recordExisted;
    private Record recordNotExisted;

    private int recordsCountInDB;

    @Before
    public void before(){

        Assert.assertTrue(dataSource != null);
        Assert.assertTrue(recordMapper != null);

        DataConfig.initDB(dataSource);

        records = SupplementaryDataFactory.getRecords();

        recordNotExisted = records[0];
        recordExisted    = records[1];

        /*
        * Initialize the others tables in database.
        * */
        User[] users = SupplementaryDataFactory.getUsers();
        for(int i = 0; i < users.length; i++){
            userMapper.insert(users[i]);
        }

        Book[] books = SupplementaryDataFactory.getBooks();
        for(int i = 0; i < books.length; i++){
            bookMapper.insert(books[i]);
        }


        for(int i = 1; i < records.length; i++){
            recordMapper.insert(records[i]);
        }
        recordsCountInDB = records.length - 1;
    }

    @Test
    public void insertTest(){

        // Can't insert null
        try{
            recordMapper.insert(null);
        }catch (Exception ignore){
            this.setExceptionHappened(true);
        }
        exceptionHappenedChecker();

        /*
         * Because of the primary key of records table will increase automatically.
         * So user can insert a same record which have the same value.
         * The database will give them different identity number.
         * */
        recordMapper.insert(recordExisted);

        recordMapper.insert(recordNotExisted);
    }

    @Test
    public void deleteByPrimaryKeyTest(){
        HashMap map = new HashMap();
        map.put("recordtype", Record.Type.DOWNLOAD);
        List<Record> records = recordMapper.selectFuzzy(map);

        for(Record record : records){
            recordMapper.deleteByPrimaryKey(record.getId());
        }

        records = recordMapper.selectFuzzy(map);

        Assert.assertTrue(records == null || records.size() == 0);
    }

    @Test
    public void selectFuzzyTest(){
        HashMap map = new HashMap();
        map.put("recordtype", Record.Type.DOWNLOAD);
        List<Record> records = recordMapper.selectFuzzy(map);

        for (Record record: records){
            Assert.assertTrue(record.getRecordtype().equals(Record.Type.DOWNLOAD));
        }
    }

    @Test
    public void selectItemCountTest(){
        int counts = recordMapper.selectItemCount(null);

        Assert.assertTrue(counts == recordsCountInDB);

        HashMap map = new HashMap();
        map.put("recordtype", Record.Type.DOWNLOAD);
        List<Record> records = recordMapper.selectFuzzy(map);

        counts = recordMapper.selectItemCount(map);

        Assert.assertTrue(records.size() == counts);
    }

    @Test
    public void countAllTest(){
        int counts =  recordMapper.countAll();

        Assert.assertTrue(counts == recordsCountInDB);
    }
}
