package org.sims.util;

import org.sims.domain.Book;
import org.sims.domain.Record;
import org.sims.domain.User;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 *  This class will only be used in test unit.
 * */
public class SupplementaryDataFactory {

    private static final int BOOK_NUM = 5;
    private static final int USER_NUM = 5;
    private static final int RECORD_NUM = 5;

    private static Book[] books     = new Book[BOOK_NUM];

    private static User[] users     = new User[USER_NUM];

    private static Record[] records = new Record[RECORD_NUM];

    static {
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

        for(int i = 0; i < books.length; i++){

            // For testing purpose, we initialize the download times and view times of the book
            books[i].setDownloadTimes(i);
            books[i].setViewTimes(i * 10);
        }
        //---------------------------------------------------------

        users[0] = new User("Eric", "eric@gmail.com",   "ericpassword");
        users[1] = new User("Jack", "jack@gmail.com",   "jackpassword");
        users[2] = new User("Maria", "maria@gmail.com", "mariapassword");
        users[3] = new User("Bruce", "bruce@gmail.com", "brucepassword");
        users[4] = new User("Jason", "jason@gmail.com", "jasonpassword");
        //---------------------------------------------------------

        records[0] = new Record(new Date(), books[0].getIsbn(), users[0].getEmail(), Record.Type.UPLOAD);
        records[1] = new Record(new Date(), books[0].getIsbn(), users[1].getEmail(), Record.Type.DOWNLOAD);
        records[2] = new Record(new Date(), books[0].getIsbn(), users[2].getEmail(), Record.Type.DOWNLOAD);
        records[3] = new Record(new Date(), books[3].getIsbn(), users[0].getEmail(), Record.Type.UPLOAD);
        records[4] = new Record(new Date(), books[3].getIsbn(), users[3].getEmail(), Record.Type.DOWNLOAD);


    }


    public static Book[] getBooks(){
        return books;
    }

    public static User[] getUsers(){
        return users;
    }

    public static Record[] getRecords(){
        return records;
    }

    public static List<User> getHugeNumberOfUsersForTesting(int limit){
        LinkedList<User> users = new LinkedList<>();

        for(int i = 0; i < limit; i++){
            users.add(new User("UserName" + i, "email" + i, "password" + i));
        }

        return users;
    }
}
