package com.basaki.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * {@code ClientApplication} represents the entry point for the Spring
 * boot application example on client tier.
 * <p/>
 *
 * @author Indra Basak
 * @since 02/19/18
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.basaki.client"})
public class ClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
    }
}
