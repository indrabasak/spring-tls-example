package com.basaki.client.controller;

import com.basaki.client.ClientApplication;
import com.basaki.client.model.BookRequest;
import com.basaki.client.service.BookService;
import com.github.tomakehurst.wiremock.common.ClasspathFileSource;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * {@code BookClientControllerFunctionalTests} represents functional tests for
 * {@code BookClientController}.
 * <p/>
 *
 * @author Indra Basak
 * @since 02/22/18
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ClientApplication.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class BookClientControllerFunctionalTests {

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

    @Value("${local.server.port}")
    private Integer port;

    @Value("${server.ssl.key-store}")
    private String keyStore;

    @Value("${server.ssl.key-store-password}")
    String keyStorePassword;

    @Autowired
    private BookService service;

    @Before
    public void startUp() {
        RestAssured.useRelaxedHTTPSValidation();
        RestAssured.config().getSSLConfig()
                .with().keyStore(keyStore, keyStorePassword);
    }

    @Test
    public void testCreate() {
        BookRequest body =
                new BookRequest("Indra's Chronicle", "Indra");

        Response response = given()
                .contentType(ContentType.JSON)
                .baseUri("https://localhost")
                .port(port)
                .contentType(ContentType.JSON)
                .body(body)
                .post("/client/books");
        assertNotNull(response);
        assertEquals(201, response.getStatusCode());
    }

    @Test
    public void testRead() {
        Response response = given()
                .contentType(ContentType.JSON)
                .baseUri("https://localhost")
                .port(port)
                .contentType(ContentType.JSON)
                .get("/client/books/1d826450-4160-4aed-ad73-fa9d1cc2e611");
        assertNotNull(response);
        assertEquals(200, response.getStatusCode());
    }
}
