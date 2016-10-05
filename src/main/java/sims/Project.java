package sims;

import sims.model.User;

public class Project {
    public static final boolean WEB_INTEGRATED_DEBUG = false;
    public static final boolean UNIT_TEST = !WEB_INTEGRATED_DEBUG;

    public static final boolean RELEASE = false;

    public static final User ADMIN = new User("administrator", "root@gmail.com", "root123", User.UserType.ADMINISTRATOR.ordinal());
}
