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
import org.sims.exception.DuplicatedPrimaryKeyException;
import org.sims.form.BookSearchForm;
import org.sims.model.Book;
import org.sims.util.PageInfo;
import org.sims.web.config.DataConfig;
import org.sims.util.SupplementaryDataFactory;

import javax.sql.DataSource;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:spring.xml", "classpath:spring-mybatis-test.xml"})
public class BookServiceTest extends DaoExceptionChecker{

    @Autowired
    private DataSource dataSource;

    @Autowired
    private BookService bookService;

    private Book[] books;
    private Book   bookExisted;
    private Book   bookNotExisted;

    private int booksCountInDB;

    @Before
    public void before() throws Exception{
        DataConfig.initDB(dataSource);

        books = SupplementaryDataFactory.getBooks();

        bookNotExisted = books[0];
        bookExisted    = books[1];

        for(int i = 1; i < books.length; i++){
            bookService.add(books[i]);
        }

        booksCountInDB = books.length - 1;

        bookService.init(); // Re-initialize the org.sims.service of book.
    }

    @Test
    public void getByIdTest(){
        Book book = bookService.getById(bookNotExisted.getIsbn());

        Assert.assertTrue(book == null);

        Book bookInDB = bookService.getById(bookExisted.getIsbn());

        Assert.assertTrue(bookInDB.equals(bookExisted));
    }

    @Test
    public void addTest() throws Exception{

        try {
            bookService.add(null);
        }catch (Exception e){ this.setExceptionHappened(true); }
        exceptionNotHappenedChecker();

        try {
            bookService.add(bookExisted);
        }catch (DuplicatedPrimaryKeyException e){
            this.setExceptionHappened(true);
        }
        exceptionHappenedChecker();

        Book bookInDB = bookService.getById(bookNotExisted.getIsbn());

        Assert.assertTrue(bookInDB == null);

        try {
            bookService.add(bookNotExisted);
        }catch (Exception e){ this.setExceptionHappened(true); }
        exceptionNotHappenedChecker();

        bookInDB = bookService.getById(bookNotExisted.getIsbn());

        Assert.assertTrue(bookInDB.equals(bookNotExisted));
    }

    @Test
    public void deleteTest(){

        bookService.delete(null);
        bookService.delete(bookNotExisted.getIsbn());


        Book bookInDB = bookService.getById(bookNotExisted.getIsbn());
        Assert.assertTrue(bookInDB == null);

        bookInDB = bookService.getById(bookExisted.getIsbn());
        Assert.assertTrue(bookInDB != null && bookInDB.equals(bookExisted));

        bookService.delete(bookExisted.getIsbn());

        bookInDB = bookService.getById(bookExisted.getIsbn());
        Assert.assertTrue(bookInDB == null);
    }

    @Test
    public void modifyTest(){
        bookService.modify(null);
        bookService.modify(bookNotExisted);

        String newname = "NewName";
        bookExisted.setBookname(newname);


        Book bookInDB = bookService.getById(bookExisted.getIsbn());

        Assert.assertFalse(bookInDB.equals(bookExisted)); // before modification

        bookService.modify(bookExisted);

        bookInDB = bookService.getById(bookExisted.getIsbn());

        Assert.assertTrue(bookInDB.equals(bookExisted)); // after modification
    }

    @Test
    public void totalCountInDBTest(){
        long counts = bookService.totalCountInDB();

        Assert.assertTrue(counts == this.booksCountInDB);
    }

    @Test
    public void pagedFuzzyQueryTest() throws Exception{
        BookSearchForm form = new BookSearchForm();

        // Only set bookType and others fields are null
        form.setBooktype(Book.BookType.CS.ordinal());

        PageInfo pageInfo = new PageInfo(0, 1, null);

        List<Book> books = bookService.pagedFuzzyQuery(form, pageInfo);
        Assert.assertTrue(books.size() == 1);

        for(Book book: books){
            Assert.assertTrue(book.getBooktype() == Book.BookType.CS.ordinal());
        }
    }

    @Test
    public void getPopularBookTest(){
        /*
        * Get the most hot(download times) book in the system.
        * */
        int top = 4;

        List<Book> books = bookService.getPopularBook(top);

        for(int i = 0; i < books.size() - 1; i++){
            Assert.assertTrue( books.get(i).getDownloadTimes() > books.get(i + 1).getDownloadTimes());
        }
    }

}
