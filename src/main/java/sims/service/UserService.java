package sims.service;

import sims.model.User;

public interface UserService {
    public User getById(String id);

    public void add(User user);

    public void delete(String id);

    public void modify(User user);

    public int countUser();
}
