package dao;

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
import sims.dao.BookMapper;
import sims.model.Book;
import sims.util.SupplementaryDataFactory;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:spring.xml", "classpath:spring-mybatis-test.xml"})
public class BookMapperTest {

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private DriverManagerDataSource dataSource;

    private static int TOP;

    private void initDB(){
        Resource resource = new ClassPathResource("SQL/schema.sql");
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
        databasePopulator.addScript(resource);
        DatabasePopulatorUtils.execute(databasePopulator, dataSource);
    }

    @Before
    public void before()throws Exception{
        if(bookMapper == null){
            throw new Exception("Failed to autowire @bookMapper");
        }

        initDB();

        Book[] books = SupplementaryDataFactory.getBooks();

        TOP = books.length/2;

        for(int i = 0; i < books.length; i++){

            // For testing purpose, we initialize the download times and view times of the book
            books[i].setDownloadTimes(i);
            books[i].setViewTimes(i * 10);

            bookMapper.insert(books[i]);
        }
    }

    @Test
    public void selectPopularBooksTest(){
        List<Book> books = bookMapper.selectPopularBooks(TOP);

        Assert.assertTrue(books.size() <= TOP);

        for (int i = 0; i < books.size() - 1; i++){
            Assert.assertTrue(
                    books.get(i).getDownloadTimes() >= books.get(i + 1).getDownloadTimes()
            );
        }

    }

//    int deleteByPrimaryKey(String isbn);
//
//    int insert(Book book);
//
//    int insertSelective(Book book);
//
//    Book selectByPrimaryKey(String isbn);
//
//    List<Book> selectByBookType(int bookType);
//
//    int updateByPrimaryKeySelective(Book book);
//
//    int updateByPrimaryKey(Book book);
//
//    List<Book> selectAll();
}
