package sims.service;

import sims.exception.DuplicatedPrimaryKeyException;
import sims.model.User;
import sims.util.PageInfo;

import java.util.List;

public interface UserService {
    public void init();

    public User getById(String id);

    public List<User> pagedFuzzyQuery(User user, PageInfo pageInfo) throws Exception;

    public void add(User user) throws DuplicatedPrimaryKeyException;

    public void delete(String id);

    public void modify(User user);

    public long totalCountInDB();
}
