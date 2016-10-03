package sims;

import sims.model.User;

public class Project {
    public static final boolean DEBUG = true;

    public static final User ADMIN = new User("administrator", "root@gmail.com", "root123", User.UserType.ADMINISTRATOR.ordinal());
}
