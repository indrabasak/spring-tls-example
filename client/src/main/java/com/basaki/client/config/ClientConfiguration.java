package com.basaki.client.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.function.Supplier;
import javax.net.ssl.SSLContext;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * {@code ClientConfiguration} configures REST template based on scheme type.
 * <p/>
 *
 * @author Indra Basak
 * @since 02/20/18
 */
@Configuration
@EnableConfigurationProperties({ClientBookProperties.class})
public class ClientConfiguration {

    private ObjectMapper objectMapper;

    private ClientBookProperties properties;

    private Resource trustStore;

    private String trustStorePassword;

    @Autowired
    public ClientConfiguration(ObjectMapper objectMapper,
            ClientBookProperties properties,
            @Value("${server.ssl.trust-store}") Resource trustStore,
            @Value("${server.ssl.trust-store-password}") String trustStorePassword) {
        this.objectMapper = objectMapper;
        this.properties = properties;
        this.trustStore = trustStore;
        this.trustStorePassword = trustStorePassword;
    }

    @Bean
    public RestTemplate restTemplate() throws IOException, URISyntaxException,
            GeneralSecurityException {
        RestTemplateBuilder builder = new RestTemplateBuilder();

        return builder
                .rootUri(clientURI())
                .messageConverters(
                        new MappingJackson2HttpMessageConverter(objectMapper),
                        new StringHttpMessageConverter())
                .requestFactory(requestFactory())
                .build();
    }

    private Supplier<ClientHttpRequestFactory> requestFactory() throws
            IOException, GeneralSecurityException {
        HttpComponentsClientHttpRequestFactory requestFactory
                = new HttpComponentsClientHttpRequestFactory();

        requestFactory.setHttpClient(httpClient());
        requestFactory.setConnectTimeout(properties.getConnectionTimeout());
        requestFactory.setReadTimeout(properties.getReadTimeout());

        return () -> requestFactory;
    }

    private HttpClient httpClient() throws IOException, GeneralSecurityException {
        HttpClientBuilder builder = HttpClients.custom();

        String scheme = properties.getScheme().toUpperCase();
        if (scheme.equals("HTTPS")) {
            //  load truststore containing certificates that are trusted
            SSLContext sslcontext =
                    SSLContexts.custom().loadTrustMaterial(
                            trustStore.getFile(),
                            trustStorePassword.toCharArray()).build();

            //SSLConnectionSocketFactory.getDefaultHostnameVerifier()
            SSLConnectionSocketFactory socketFactory =
                    new SSLConnectionSocketFactory(sslcontext,
                            new NoopHostnameVerifier());
            builder.setSSLSocketFactory(socketFactory);
        } else {
            builder.setSSLHostnameVerifier(new NoopHostnameVerifier());
        }

        return builder.build();
    }

    private String clientURI() throws URISyntaxException, MalformedURLException {
        return new URI(properties.getScheme(), null, properties.getHost(),
                properties.getPort(), null, null, null).toURL().toString();
    }
}
