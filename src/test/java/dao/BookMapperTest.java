package dao;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import sims.dao.BookMapper;
import sims.model.Book;
import sims.util.SupplementaryDataFactory;
import sims.web.config.DataConfig;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:spring.xml", "classpath:spring-mybatis-test.xml"})
public class BookMapperTest extends DaoExceptionChecker{

    @Autowired
    private DataSource dataSource;

    @Autowired
    private BookMapper bookMapper;

    private Book[] books;
    private Book   bookExisted;
    private Book   bookNotExisted;

    private int booksCountInDB;

    private static int TOP;

    @Before
    public void before() throws Exception{

        Assert.assertTrue(dataSource != null);
        Assert.assertTrue(bookMapper != null);

        DataConfig.initDB(dataSource);

        books          = SupplementaryDataFactory.getBooks();
        bookNotExisted = books[0];
        bookExisted    = books[1];

        TOP = books.length/2;

        for(int i = 1; i < books.length; i++){

            /*
             * For testing purpose, we initialize the download
             * times and view times of the book.
             */
            books[i].setDownloadTimes(i);
            books[i].setViewTimes(i * 10);

            bookMapper.insert(books[i]);
        }
        booksCountInDB = books.length - 1;
    }

    @Test
    public void insertTest(){
        // Can't insert null
        try{
            bookMapper.insert(null);
        }catch (Exception ignore){
            exceptionHappened = true;
        }

        exceptionHappenedChecker();

        // Can't insert an user with duplicated primary key
        try{
            bookMapper.insert(bookExisted);
        }catch (Exception e){
            exceptionHappened = true;
        }
        exceptionHappenedChecker();

        // Should work correctly.
        bookMapper.insert(bookNotExisted);
    }

    @Test
    public void insertSelectiveTest(){
        // Can't insert null
        try{
            bookMapper.insertSelective(null);
        }catch (Exception ignore){
            exceptionHappened = true;
        }

        exceptionHappenedChecker();

        bookMapper.insertSelective(bookNotExisted);

        Book bookInDB = bookMapper.selectByPrimaryKey(bookNotExisted.getIsbn());

        Assert.assertTrue(bookInDB.equals(bookNotExisted));
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

    @Test
    public void deleteByPrimaryKeyTest(){

        bookMapper.deleteByPrimaryKey(null);

        bookMapper.deleteByPrimaryKey(bookNotExisted.getIsbn());

        bookMapper.deleteByPrimaryKey(bookExisted.getIsbn());

        Book bookInDB = bookMapper.selectByPrimaryKey(bookExisted.getIsbn());

        Assert.assertTrue(bookInDB == null);
    }

    @Test
    public void selectByPrimaryKeyTest(){

        Book book = bookMapper.selectByPrimaryKey(null);

        book = bookMapper.selectByPrimaryKey(bookNotExisted.getIsbn());

        Assert.assertTrue(book == null);

        book = bookMapper.selectByPrimaryKey(bookExisted.getIsbn());

        Assert.assertTrue(book != null);
    }

    @Test
    public void updateByPrimaryKeySelectiveTest(){
        String author = "NewAuthor";
        bookExisted.setAuthor(author);

        bookMapper.updateByPrimaryKeySelective(bookExisted);

        Book bookInDB = bookMapper.selectByPrimaryKey(bookExisted.getIsbn());

        Assert.assertTrue(bookInDB.equals(bookExisted));
        Assert.assertTrue(author.equals(bookInDB.getAuthor()));
    }

    @Test
    public void selectAllTest(){

        List<Book> booksInDB = bookMapper.selectAll();

        ArrayList expectedBooks = new ArrayList();

        for(Book book: books){
            expectedBooks.add(book);
        }

        for (Book book : booksInDB){
            Assert.assertTrue(expectedBooks.contains(book));
        }

        Assert.assertTrue(booksInDB.size() == booksCountInDB);
    }

    @Test
    public void selectFuzzyTest(){
        HashMap map = new HashMap();

        map.put("booktype", Book.BookType.CS.ordinal());

        List<Book> books = bookMapper.selectFuzzy(map);

        for(Book book: books){
            Assert.assertTrue( book.getBooktype() == Book.BookType.CS.ordinal() );
        }

        books = bookMapper.selectFuzzy(null);

        Assert.assertTrue(books.size() == booksCountInDB);
    }

    @Test
    public void selectItemCountTest(){
        HashMap map = new HashMap();

        map.put("booktype", Book.BookType.CS.ordinal());

        List<Book> books = bookMapper.selectFuzzy(map);

        int counts = bookMapper.selectItemCount(map);

        Assert.assertTrue(  books.size() == counts);
    }

    @Test
    public void countAllTest(){
        int counts = bookMapper.countAll();

        Assert.assertTrue(counts == booksCountInDB);
    }
}
