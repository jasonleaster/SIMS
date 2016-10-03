package sims.service;

import sims.form.BookSearchForm;
import sims.model.Book;
import sims.util.PageInfo;

import java.util.List;

public interface BookService {
    public Book getById(String id);

    public List<Book> pagedFuzzyQuery(BookSearchForm book, PageInfo pageInfo) throws Exception;

    public List<Book> getPopularBook(long bookNum);

    public void add(Book book);

    public void delete(String id);

    public void modify(Book book);

    public long totalCountInDB();
}
