package service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import sims.dao.BookMapper;
import sims.form.BookSearchForm;
import sims.model.Book;
import sims.service.BookService;
import sims.util.PageInfo;
import sims.web.config.DataConfig;
import sims.util.SupplementaryDataFactory;

import javax.sql.DataSource;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:spring.xml", "classpath:spring-mybatis-test.xml"})
public class BookServiceTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private BookService bookService;

    @Before
    public void before(){
        DataConfig.initDB(dataSource);
        Book[] books = SupplementaryDataFactory.getBooks();

        for(int i = 0; i < books.length; i++){
            bookService.add(books[i]);
        }
    }

    @Test
    public void pagedFuzzyQueryTest() throws Exception{
        BookSearchForm form = new BookSearchForm();

        // Only set bookType and others fields are null
        form.setBooktype(Book.BookType.CS.ordinal());
        form.setPriceLowBound(10.);
        form.setPriceUpBound(90.);

        PageInfo pageInfo = new PageInfo();

        bookService.pagedFuzzyQuery(form, pageInfo);
    }
}
