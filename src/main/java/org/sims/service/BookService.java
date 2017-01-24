package org.sims.service;

import org.sims.form.BookSearchForm;
import org.sims.model.Book;
import org.sims.util.PageInfo;

import java.util.List;

public interface BookService {
    public void init();

    public Book getById(String id);

    public List<Book> pagedFuzzyQuery(BookSearchForm book, PageInfo pageInfo) throws Exception;

    public List<Book> getPopularBook(long bookNum);

    public void add(Book book) throws Exception;

    public void delete(String id);

    public void modify(Book book);

    public long totalCountInDB();
}
