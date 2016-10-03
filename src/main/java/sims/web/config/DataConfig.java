package sims.web.config;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;


@Component
public class DataConfig {

    public static void initDB(DataSource dataSource){
        Resource resource = new ClassPathResource("SQL/schema.sql");
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
        databasePopulator.addScript(resource);
        DatabasePopulatorUtils.execute(databasePopulator, dataSource);
    }
}
