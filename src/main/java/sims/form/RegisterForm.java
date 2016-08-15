package sims.form;

import sims.model.User;

public class RegisterForm extends User {
    private String confirmedPassword;

    public RegisterForm(){}

    public RegisterForm(String username, String email, String password, String confirmedPassword) {
        super(username, email, password);
        this.confirmedPassword = confirmedPassword;
    }

    public void setConfirmedPassword(String confirmedPassword) {
        this.confirmedPassword = confirmedPassword;
    }

    public String getConfirmedPassword() {
        return confirmedPassword;
    }

    public User toUser(){
        return new User(this.getUsername(), this.getEmail(), this.getPassword());
    }
}
