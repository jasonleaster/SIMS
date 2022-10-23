package org.sims.service.impl;

import io.mybatis.service.AbstractService;
import org.sims.infrastructure.mapper.UserMapper;
import org.sims.domain.User;
import org.springframework.stereotype.Service;


@Service("userService")
public class UserServiceImpl extends AbstractService<User, Long, UserMapper> {
}
