package com.basaki.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * {@code ServerApplication} represents the entry point for the Spring
 * boot application example on server tier.
 * <p/>
 *
 * @author Indra Basak
 * @since 12/27/17
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.basaki.server"})
public class ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }
}
