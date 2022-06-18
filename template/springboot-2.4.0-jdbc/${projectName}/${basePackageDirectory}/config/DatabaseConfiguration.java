package io.github.ludongrong.dbcoder.common.config;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.pool.DruidDataSource;

@Configuration
public class DatabaseConfiguration {

    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean(name = "dataSource")
    public DataSource druidDataSource() {
        return new DruidDataSource();
    }
}
