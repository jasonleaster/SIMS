package sims.service;

import sims.model.Book;

public interface BookService {
    public Book getById(String id);

    public void add(Book book);

    public void delete(String id);

    public void modify(Book book);

    public int countBook();
}
