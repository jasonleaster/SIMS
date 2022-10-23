package org.sims.service.impl;

import io.mybatis.service.AbstractService;
import org.springframework.stereotype.Service;
import org.sims.infrastructure.mapper.BookMapper;
import org.sims.domain.Book;

@Service("bookService")
public class BookServiceImpl extends AbstractService<Book, Long, BookMapper>  {
}
