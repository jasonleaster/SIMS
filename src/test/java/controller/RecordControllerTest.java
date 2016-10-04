package controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import sims.model.Book;
import sims.model.Record;
import sims.model.User;
import sims.service.BookService;
import sims.service.RecordService;
import sims.service.UserService;
import sims.util.MsgAndContext;
import sims.util.URLs;
import sims.util.Views;

import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:spring.xml", "classpath:spring-mybatis-test.xml"})
public class RecordControllerTest {

    @Autowired
    WebApplicationContext ctx;

    @Autowired
    RecordService recordService;

    @Autowired
    UserService userService;

    @Autowired
    BookService bookService;

    @Autowired
    DriverManagerDataSource dataSource;

    MockMvc mockMvc;

    Book bookExisted;
    User userExisted;

    Record records[];

    private static final int    RECORDS_NUM = 5;

    private static final String ISBN_USED   = "978-7-5086-4363-1";
    private static final String ISBN_UNUSED = "978-7-5086-4363-2";

    private void initDB(){
        Resource resource = new ClassPathResource("SQL/schema.sql");
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
        databasePopulator.addScript(resource);
        DatabasePopulatorUtils.execute(databasePopulator, dataSource);
    }

    @Before
    public void before(){
        mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();

        initDB();

        bookExisted = new Book();
        bookExisted.setIsbn(ISBN_USED);
        bookExisted.setAuthor("小林章");
        bookExisted.setBookname("西文字体");
        bookExisted.setBooktype(Book.BookType.OTHERS.ordinal());

        userExisted    = new User("Eric", "eric@gmail.com", "ericpassword");

        bookService.add(bookExisted);
        userService.add(userExisted);

        records = new Record[RECORDS_NUM];

        for (int i = 0; i < RECORDS_NUM; i++){
            records[i] = new Record();
            records[i].setDate(new Date());
            records[i].setUserId(userExisted.getEmail());
            records[i].setBookId(bookExisted.getIsbn());
            if(i % 2 == 0){
                records[i].setRecordtype(Record.Type.DOWNLOAD);
            }else{
                records[i].setRecordtype(Record.Type.UPLOAD);
            }

            recordService.add(records[i]);
        }
    }

    @Test
    public void queryTest() throws Exception{

        String url = URLs.RECORDS + URLs.QUERY + "?userId=" + userExisted.getEmail();

        mockMvc.perform(MockMvcRequestBuilders.get(url))
                .andExpect(MockMvcResultMatchers.model().attributeExists(MsgAndContext.MODEL_ATTRIBUTES_RECORDS))
                .andExpect(MockMvcResultMatchers.model().attributeDoesNotExist(MsgAndContext.MODEL_ATTRIBUTES_ERR_MSG))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name(Views.RECORD_RESULT_TABLE));

        /*
        * There is only one user in the database for testing.
        * All the records belongs to the user and the number of
        * the records is RECORDS_NUM.
        * */
        List records = (List) ctx.getBean(MsgAndContext.MODEL_ATTRIBUTES_RECORDS);
        Assert.assertTrue(records.size() == RECORDS_NUM);
    }
}
