package sims.dao;

import sims.model.Book;

import java.util.List;
import java.util.Map;

@SuppressWarnings("rawtypes")
public interface BookMapper {

    int insert(Book book);

    int insertSelective(Book book);

    int deleteByPrimaryKey(String isbn);

    int updateByPrimaryKeySelective(Book book);

    Book selectByPrimaryKey(String isbn);

    List<Book> selectPopularBooks(int topNum);

    /**
     * Implement the fuzzy query for books.
     * @Param: Map map.
     *      The caller of #selectFuzzy will put constraint attributes into the map.
     * * */
	List<Book> selectFuzzy(Map map);

    /**
     * With the constraints, return the total count of the query result.
     * @Param: Map map.
     *      The caller of #selectFuzzy will put constraint attributes into the map.
     * * */
    int selectItemCount(Map map);

    /**
     * Return the count of all items in the table.
     * */
    int countAll();

    /**
     * Return all the items in the table.
     * */
    List<Book> selectAll();
}