package org.sims.web;

import org.sims.service.BookService;
import org.sims.service.RecordService;
import org.sims.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;


@Component
public class ContextRefreshedListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    @Autowired
    private RecordService recordService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ApplicationContext context = event.getApplicationContext();

        if (context.getParent() == null) {
            System.out.println("Spring容器初始化完毕================================================");
            System.out.println("ContextRefreshedListener is invoked");
        }
    }

}