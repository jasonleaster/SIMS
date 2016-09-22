package sims.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sims.dao.BookMapper;
import sims.model.Book;
import sims.service.BookService;

import java.util.List;

@Service("bookService")
public class BookServiceImpl implements BookService {

    private BookMapper bookMapper;

    public BookMapper getBookMapper() {
        return bookMapper;
    }

    @Autowired
    public void setBookMapper(BookMapper bookMapper) {
        this.bookMapper = bookMapper;
    }

    @Override
    public Book getById(String id) {
        return bookMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Book> getByType(int bookType) {
        List<Book> books = bookMapper.selectByBookType(bookType);
        return books;
    }

    @Override
    public void add(Book book) {
        bookMapper.insert(book);
    }

    @Override
    public void delete(String id) {
        bookMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void modify(Book book) {
        bookMapper.updateByPrimaryKey(book);
    }

    @Override
    public int countBook() {
        List<Book> books = bookMapper.selectAll();
        return books.size();
    }
}
