package org.sims.service.impl;

import io.mybatis.service.AbstractService;
import org.springframework.stereotype.Service;
import org.sims.dao.BookMapper;
import org.sims.model.Book;

@Service("bookService")
public class BookServiceImpl extends AbstractService<Book, Long, BookMapper>  {
}
