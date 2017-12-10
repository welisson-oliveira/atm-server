package br.com.welisson.atm.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.text.SimpleDateFormat;

@Order(3)
@Configuration
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter {

    @Value("${endpoints.cors.allow-credentials}")
    private String allowCredentials;

    @Value("${endpoints.cors.allowed-origins}")
    private String allowedOrigins;

    @Value("${endpoints.cors.allowed-methods}")
    private String allowedMethods;

    @Value("${endpoints.cors.allowed-headers}")
    private String allowedHeaders;

    @Value("${endpoints.cors.exposed-headers}")
    private String exposedHeaders;

    @Value("${endpoints.cors.max-age}")
    private String maxAge;

    @Bean
    public Jackson2ObjectMapperBuilder jacksonBuilder() {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        builder.indentOutput(true);
        builder.dateFormat(new SimpleDateFormat("dd/MM/yyyy"));
        return builder;
    }

    @Override
    public void configureContentNegotiation(final ContentNegotiationConfigurer configurer) {
        configurer.favorParameter(true)
                .ignoreAcceptHeader(true)
                .parameterName("mediaType")
                .useJaf(false)
                .defaultContentType(MediaType.APPLICATION_JSON)
                .mediaType("xml", MediaType.APPLICATION_XML)
                .mediaType("json", MediaType.APPLICATION_JSON);
    }

}
