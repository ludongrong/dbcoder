package io.github.ludongrong.common.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@MapperScan("io.github.ludongrong.test.mapper")
public class MybatisPlusConfig {

//    @Autowired
//    void initializeDatabase(JdbcTemplate jdbcTemplate) {
//        ResourceLoader resourceLoader = new DefaultResourceLoader();
////        Resource[] scripts = new Resource[] { resourceLoader.getResource("classpath:schema.sql"),
////                resourceLoader.getResource("classpath:data.sql") };
//        jdbcTemplate.execute("CREATE TABLE TB_USER (\n" +
//                "    ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),\n" +
//                "    USER_NAME VARCHAR(255) DEFAULT NULL,\n" +
//                "    PASSWORD VARCHAR(255) DEFAULT NULL,\n" +
//                "    NAME VARCHAR(255) DEFAULT NULL,\n" +
//                "    AGE INTEGER,\n" +
//                "    EMAIL VARCHAR(255) DEFAULT NULL,\n" +
//                "    PRIMARY KEY (ID)\n" +
//                ")");
//    }

}
