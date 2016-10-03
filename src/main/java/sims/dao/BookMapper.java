package sims.dao;

import sims.model.Book;

import java.util.List;
import java.util.Map;

public interface BookMapper {

    int deleteByPrimaryKey(String isbn);

    int insert(Book book);

    int insertSelective(Book book);

    Book selectByPrimaryKey(String isbn);

    List<Book> selectFuzzy(Map map);

    int        selectItemCount(Map map);

    int        countAll();

    List<Book> selectPopularBooks(int topNum);

    int updateByPrimaryKeySelective(Book book);

    int updateByPrimaryKey(Book book);

    List<Book> selectAll();
}