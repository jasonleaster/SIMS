package sims.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import sims.Project;
import sims.web.config.DataConfig;

import javax.sql.DataSource;


@Component
public class ContextRefreshedListener implements ApplicationListener<ContextRefreshedEvent>{

    @Autowired
    DataSource dataSource;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ApplicationContext context = event.getApplicationContext();

        if(Project.DEBUG){
            DataConfig.initDB(dataSource);
        }

        if(context.getParent() == null){
            System.out.println("Spring容器初始化完毕================================================");
            System.out.println("ContextRefreshedListener is invoked");
        }
    }

}