package org.sims.service.impl;

import io.mybatis.service.AbstractService;
import org.sims.dao.UserMapper;
import org.sims.model.User;
import org.springframework.stereotype.Service;


@Service("userService")
public class UserServiceImpl extends AbstractService<User, Long, UserMapper> {
}
