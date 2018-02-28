package com.basaki.client.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;

/**
 * {@code SslProperties} contains server SSL properties.
 * <p/>
 *
 * @author Indra Basak
 * @since 02/28/18
 */
@ConfigurationProperties("server.ssl")
@Getter
@Setter
public class SslProperties {

    private Resource trustStore;

    private String trustStorePassword;

    private Resource keyStore;

    private String keyStorePassword;

    private String keyPassword;
}
