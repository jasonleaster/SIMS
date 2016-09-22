package sims.service;

import sims.model.Book;
import java.util.List;

public interface BookService {
    public Book getById(String id);

    public List<Book> getByType(int bookType);

    public void add(Book book);

    public void delete(String id);

    public void modify(Book book);

    public int countBook();
}
