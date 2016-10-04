package sims.dao;

import sims.model.Book;

import java.util.List;
import java.util.Map;

public interface BookMapper {

    int insert(Book book);

    int deleteByPrimaryKey(String isbn);

    int updateByPrimaryKeySelective(Book book);

    Book selectByPrimaryKey(String isbn);

    List<Book> selectPopularBooks(int topNum);

    List<Book> selectFuzzy(Map map);

    int selectItemCount(Map map);

    int countAll();

    List<Book> selectAll();
}