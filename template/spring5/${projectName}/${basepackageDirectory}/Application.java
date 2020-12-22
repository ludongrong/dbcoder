package io.github.ludongrong.dbcoder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableScheduling
@EnableTransactionManagement
@PropertySources(@PropertySource(value = "file:config/application.properties", ignoreResourceNotFound = true, encoding = "UTF-8"))
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}