package org.sims.domain;

import io.mybatis.provider.Entity;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity.Table("users")
public class User {

    @Entity.Column(value = "email", remark = "邮箱")
    private String email;

    @Entity.Column(value = "username", remark = "用户名")
    private String username;

    @Entity.Column(value = "password", remark = "密码")
    private String password;

    @Entity.Column(value = "userType", remark = "用户类型")
    private Integer userType;

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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
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