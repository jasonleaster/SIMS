package org.sims.infrastructure.config;

import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * 数据库相关配置
 */
@Configuration
public class DatabaseConfig {

    @Value("${jdbc_url}")
    private String url;

    @Value("${jdbc_username}")
    private String userName;

    @Value("${jdbc_password}")
    private String password;

    @Value("${driverClassName}")
    private String driver;

    @Value("${poolMaximumActiveConnections}")
    private int poolMaximumActiveConnections;

    @Value("${poolMaximumIdleConnections}")
    private int poolMaximumIdleConnections;


    /**
     * 数据源
     */
    @Bean("dataSource")
    public DataSource getDataSource() {
        PooledDataSource dataSource = new PooledDataSource();

        dataSource.setUrl(url);
        dataSource.setUsername(userName);
        dataSource.setPassword(password);
        dataSource.setDriver(driver);
        dataSource.setPoolMaximumActiveConnections(poolMaximumActiveConnections);
        dataSource.setPoolMaximumIdleConnections(poolMaximumIdleConnections);

        return  dataSource;
    }

    /**
     * 事务管理器
     */
    @Bean
    public DataSourceTransactionManager getDataSourceTransactionManager(@Autowired DataSource dataSource) {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dataSource);
        return dataSourceTransactionManager;
    }
}
