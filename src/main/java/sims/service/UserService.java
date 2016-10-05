package sims.service;

import sims.form.BookSearchForm;
import sims.model.Book;
import sims.model.User;
import sims.util.PageInfo;

import java.util.List;

public interface UserService {
    public User getById(String id);

    public List<User> pagedFuzzyQuery(User user, PageInfo pageInfo) throws Exception;

    public void add(User user);

    public void delete(String id);

    public void modify(User user);

    public int countUser();
}
