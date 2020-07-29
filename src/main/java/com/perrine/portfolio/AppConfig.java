package com.perrine.portfolio;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
//@ConfigurationProperties
public class AppConfig {
    private String hello;
    public PostgresConfig postgres;

    public String getHello() {
        return hello;
    }

    public void setHello(String hello) {
        this.hello = hello;
    }

    public PostgresConfig getPostgres() {
        return postgres;
    }

    public void setPostgres(PostgresConfig postgres) {
        this.postgres = postgres;
    }
}
