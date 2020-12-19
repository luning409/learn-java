package org.cyg.thinking.in.spring.annotation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * HelloWorld  Configuration
 */
@Configuration
public class HelloWorldConfiguration {

    @Bean
    public String helloWorld() {
        return "Hello,World";
    }
}
