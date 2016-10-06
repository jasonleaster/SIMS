package sims.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sims.dao.BookMapper;
import sims.exception.DuplicatedPrimaryKeyException;
import sims.form.BookSearchForm;
import sims.model.Book;
import sims.service.BookService;
import sims.util.PageInfo;
import sims.web.BaseDomain;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service("bookService")
public class BookServiceImpl extends BaseDomain implements BookService {

    static private BookMapper bookMapper;

    static private long booksNumInDB;

    public BookMapper getBookMapper() {
        return bookMapper;
    }

    @Autowired
    public void setBookMapper(BookMapper bookMapper) {
        this.bookMapper = bookMapper;
    }

    @Override
    public void init() {
        booksNumInDB = bookMapper.countAll();
    }

    @Override
    public Book getById(String id) {
        Book book = null;
        try {
            book = bookMapper.selectByPrimaryKey(id);
        }catch (Exception ignore){}

        return book;
    }

    @Override
    public List<Book> pagedFuzzyQuery(BookSearchForm form, PageInfo pageInfo) throws Exception{
        List<Book> books = new ArrayList<>();

        if(form == null){
            form = new BookSearchForm(); // empty constraints with book.
        }

        if(pageInfo == null){
            pageInfo = new PageInfo();
        }

        if(form.getIsbn() != null){
            books.add(this.getById(form.getIsbn()));
            return books;
        }
        HashMap map = new HashMap();

        Field[] fields = form.getClass().getDeclaredFields();
        for(Field field: fields){
            field.setAccessible(true);
            map.put(field.getName(), field.get(form));
        }

        fields = pageInfo.getClass().getDeclaredFields();
        for(Field field: fields){
            field.setAccessible(true);
            map.put(field.getName(), field.get(pageInfo));
        }

        books = bookMapper.selectFuzzy(map);

        int totalCount = bookMapper.selectItemCount(map);
        pageInfo.setTotalCount(totalCount);

        return books;
    }

    @Override
    public void add(Book book) throws Exception{
        if(book == null){
            return;
        }

        if(getById(book.getIsbn()) != null){
            throw new DuplicatedPrimaryKeyException();
        }

        bookMapper.insert(book);
        booksNumInDB++;
    }

    @Override
    public void delete(String id) {
        if(id == null){
            return;
        }

        if(getById(id) != null){
            bookMapper.deleteByPrimaryKey(id);
            booksNumInDB--;
        }
    }

    @Override
    public void modify(Book book) {
        if(book == null){
            return;
        }

        if(getById(book.getIsbn()) != null) {
            bookMapper.updateByPrimaryKeySelective(book);
        }
    }

    @Override
    public List<Book> getPopularBook(long bookNum) {
        if(bookNum <= 0){
            return new ArrayList<>();
        }

        if(bookNum > booksNumInDB){
            bookNum = booksNumInDB;
        }

        List<Book> books = bookMapper.selectPopularBooks((int) bookNum);
        return books;
    }

    public static long getBooksNumInDB() {
        return booksNumInDB;
    }

    public static void setBooksNumInDB(long booksNumInDB) {
        BookServiceImpl.booksNumInDB = booksNumInDB;
    }

    @Override
    public long totalCountInDB() {
        return getBooksNumInDB();
    }
}
