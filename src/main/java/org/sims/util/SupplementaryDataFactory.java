package org.sims.util;

import org.sims.domain.User;

import java.util.LinkedList;
import java.util.List;

/**
 *  This class will only be used in test unit.
 * */
public class SupplementaryDataFactory {

    private static final int BOOK_NUM = 5;
    private static final int USER_NUM = 5;
    private static final int RECORD_NUM = 5;

    private static User[] users     = new User[USER_NUM];

    static {

        //---------------------------------------------------------

        users[0] = new User("Eric", "eric@gmail.com",   "ericpassword");
        users[1] = new User("Jack", "jack@gmail.com",   "jackpassword");
        users[2] = new User("Maria", "maria@gmail.com", "mariapassword");
        users[3] = new User("Bruce", "bruce@gmail.com", "brucepassword");
        users[4] = new User("Jason", "jason@gmail.com", "jasonpassword");
        //---------------------------------------------------------

    }

    public static User[] getUsers(){
        return users;
    }

    public static List<User> getHugeNumberOfUsersForTesting(int limit){
        LinkedList<User> users = new LinkedList<>();

        for(int i = 0; i < limit; i++){
            users.add(new User("UserName" + i, "email" + i, "password" + i));
        }

        return users;
    }
}
