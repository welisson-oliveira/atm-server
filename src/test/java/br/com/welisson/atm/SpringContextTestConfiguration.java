package br.com.welisson.atm;

import org.apache.catalina.security.SecurityConfig;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan(basePackages = {"br.com.welisson.atm"})
@PropertySource({"classpath:application.properties"})
@EnableJpaRepositories(basePackages = "br.com.welisson.atm")
public class SpringContextTestConfiguration {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

}
