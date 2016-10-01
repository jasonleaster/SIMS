package util;

import sims.model.Book;

import java.util.UUID;

/**
 *  This class will only be used in test unit.
 * */
public class BooksGenerator {

    private static final int BOOK_NUM = 5;

    private static Book[] books = new Book[BOOK_NUM];

    public static Book[] getBooks(){
        books[0] =  new Book();
        books[0].setIsbn(UUID.randomUUID().toString());
        books[0].setAuthor("小林章");
        books[0].setBookname("西文字体");
        books[0].setBooktype(Book.BookType.OTHERS.ordinal());

        books[1] = new Book();
        books[1].setIsbn(UUID.randomUUID().toString());
        books[1].setAuthor("Jason");
        books[1].setBookname("Java Cook Book");
        books[1].setBooktype(Book.BookType.CS.ordinal());

        books[2] =  new Book();
        books[2].setIsbn(UUID.randomUUID().toString());
        books[2].setAuthor("Jason");
        books[2].setBookname("Machine Learning for Hackers");
        books[2].setBooktype(Book.BookType.MACHINELEARING.ordinal());

        books[3] = new Book();
        books[3].setIsbn(UUID.randomUUID().toString());
        books[3].setAuthor("unknown");
        books[3].setBookname("MySQL 必知必会");
        books[3].setBooktype(Book.BookType.CS.ordinal());

        books[4] =  new Book();
        books[4].setIsbn(UUID.randomUUID().toString());
        books[4].setAuthor("unknown");
        books[4].setBookname("Spring in Action");
        books[3].setBooktype(Book.BookType.CS.ordinal());

        return books;
    }
}
