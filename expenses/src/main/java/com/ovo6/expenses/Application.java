package com.ovo6.expenses;

/**
 * Main entry point for Spring Boot Application.
 */

import com.ovo6.expenses.repo.UniqueUserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.rest.core.event.ValidatingRepositoryEventListener;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

@SpringBootApplication
public class Application extends RepositoryRestConfigurerAdapter {

    @Autowired
    private UniqueUserValidator validator;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void configureValidatingRepositoryEventListener(ValidatingRepositoryEventListener v) {

        v.addValidator("beforeCreate", validator);
    }
}
