package com.basaki.client.service;

import javax.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("client.book")
@Getter
@Setter
public class ClientBookProperties {

    private String url;

    private int connectionTimeout = -1;

    private int readTimeout = -1;

    @PostConstruct
    public void init() {
        if (connectionTimeout == -1) {
            connectionTimeout = 1000;
        }

        if (readTimeout == -1) {
            readTimeout = 1000;
        }
    }
}
