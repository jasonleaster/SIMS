package sims.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import sims.Project;
import sims.dao.BookMapper;
import sims.dao.RecordMapper;
import sims.dao.UserMapper;
import sims.model.Book;
import sims.model.Record;
import sims.model.User;
import sims.service.BookService;
import sims.service.RecordService;
import sims.service.UserService;
import sims.service.impl.BookServiceImpl;
import sims.service.impl.RecordServiceImpl;
import sims.service.impl.UserServiceImpl;
import sims.util.SupplementaryDataFactory;
import sims.web.config.DataConfig;

import javax.sql.DataSource;


@Component
public class ContextRefreshedListener implements ApplicationListener<ContextRefreshedEvent>{

    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    @Autowired
    private RecordService recordService;

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RecordMapper recordMapper;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ApplicationContext context = event.getApplicationContext();

        if(Project.UNIT_TEST){
            return;
        }

        if(Project.WEB_INTEGRATED_DEBUG){
            DataConfig.initDB(dataSource);
            Book[] books = SupplementaryDataFactory.getBooks();
            User[] users = SupplementaryDataFactory.getUsers();
            Record[] records = SupplementaryDataFactory.getRecords();

            for (int i = 0; i < books.length; i++){
                bookService.add(books[i]);
            }

            for (int i = 0; i < users.length; i++){
                userService.add(users[i]);
            }

            for (int i = 0; i < records.length; i++){
                recordService.add(records[i]);
            }
        }

        if(Project.RELEASE){
            /*
             * Just image that, what if the website crashed for some reasons.
             * You fix the bug quickly and restart the server to run this project.
             * Some useful information about database are cached in memory.
             * In this situation, the in-memory data lost and the variable @booksNumInDB
             * reinitialized into zero.
             *
             * It's a good trick to put the database related meta-information code
             * into the Spring Bean setter function. And the value of @booksNumInDB
             * is correct.
             * */
            BookServiceImpl.setBooksNumInDB(bookMapper.countAll());
            UserServiceImpl.setUsersNumInDB(userMapper.countAll());
            RecordServiceImpl.setRecordsNumInDB(recordMapper.countAll());
        }

        userService.add(Project.ADMIN);

        if(context.getParent() == null){
            System.out.println("Spring容器初始化完毕================================================");
            System.out.println("ContextRefreshedListener is invoked");
        }
    }

}