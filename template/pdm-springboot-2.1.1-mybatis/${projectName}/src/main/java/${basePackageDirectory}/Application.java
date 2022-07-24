package ${basePackage}.${projectName};

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

<#--
@EnableScheduling
@EnableTransactionManagement
@PropertySources(@PropertySource(value = "file:config/application.properties", ignoreResourceNotFound = true, encoding = "UTF-8"))
-->
@SpringBootApplication
@EnableScheduling
public class Application {

<#if appType == 'web'>
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
<#else>
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Application.class);
        app.setWebApplicationType(WebApplicationType.NONE);
        app.run(args);
    }
</#if>
}