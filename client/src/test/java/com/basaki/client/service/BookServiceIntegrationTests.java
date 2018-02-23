package com.basaki.client.service;

import com.basaki.client.ClientApplication;
import com.basaki.client.controller.CustomHttpServerFactory;
import com.basaki.client.model.Book;
import com.basaki.client.model.BookRequest;
import com.github.tomakehurst.wiremock.common.ClasspathFileSource;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import java.util.UUID;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.junit.Assert.assertNotNull;

/**
 * {@code BookServiceIntegrationTests} represents integration tests for
 * {@code BookService}.
 * <p/>
 *
 * @author Indra Basak
 * @since 02/22/18
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ClientApplication.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class BookServiceIntegrationTests {

    @ClassRule
    public static WireMockRule wireMockRule = new WireMockRule(wireMockConfig()
            .usingFilesUnderClasspath("wiremock/server")
            .httpsPort(8443)
            .keystorePath(new ClasspathFileSource(
                    "config/client-keystore.p12").getUri().getPath())
            .keystorePassword("client")
            .keystoreType("PKCS12")
            .trustStorePath(new ClasspathFileSource(
                    "config/client-keystore.p12").getUri().getPath())
            .trustStorePassword("client")
            .trustStoreType("PKCS12")
            .httpServerFactory(new CustomHttpServerFactory()));

    @Autowired
    private BookService service;

    @Test
    public void testCreate() {
        BookRequest request =
                new BookRequest("Indra's Chronicle", "Indra");

        Book response = service.create(request);
        assertNotNull(response);
    }

    @Test
    public void testRead() {
        Book response = service.read(
                UUID.fromString("1d826450-4160-4aed-ad73-fa9d1cc2e611"));
        assertNotNull(response);
    }
}
