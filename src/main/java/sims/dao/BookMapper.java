package sims.dao;

import sims.model.Book;

import java.util.List;

public interface BookMapper {

    int deleteByPrimaryKey(String isbn);

    int insert(Book book);

    int insertSelective(Book book);

    Book selectByPrimaryKey(String isbn);

    int updateByPrimaryKeySelective(Book book);

    int updateByPrimaryKey(Book book);

    List<Book> selectAll();
}