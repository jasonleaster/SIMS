package sims.model;

public class User {
    private String email;

    private String username;

    private String password;

    private int userType;

    public enum UserType{
        ADMINISTRATOR, NORMAL_USER
    }

    public User(){
    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email    = email;
        this.password = password;
        this.userType = UserType.NORMAL_USER.ordinal();
    }

    public User(String username, String email, String password, int userType) {
        this.username = username;
        this.email    = email;
        this.password = password;
        this.userType = userType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public boolean isAdministrator(){
        if (getUserType() == UserType.ADMINISTRATOR.ordinal()){
            return true;
        }else{
            return false;
        }
    }
}