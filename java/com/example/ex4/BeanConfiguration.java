package com.example.ex4;

import com.example.ex4.beans.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.SessionScope;


/**
 * create some beans with various scopes using QUALIFIERS (method names)
 */
@Configuration
public class BeanConfiguration {
    @Bean
    @SessionScope
    public Logger sessionBean() {
        Logger l = new Logger();
        return l;
    }
}