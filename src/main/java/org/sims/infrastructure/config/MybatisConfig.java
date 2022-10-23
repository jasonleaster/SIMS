package org.sims.infrastructure.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = {"org.sims.infrastructure.mapper"})
public class MybatisConfig {
}
