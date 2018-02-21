package com.basaki.client.service;

import com.basaki.client.model.Book;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.UUID;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.web.client.MockServerRestTemplateCustomizer;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

/**
 * {@code BookServiceTest} represents unit test for {@code BookServiceTest}.
 * <p/>
 *
 * @author Indra Basak
 * @since 02/20/21
 */
@RunWith(SpringRunner.class)
@RestClientTest({BookService.class})
@ActiveProfiles("test")
public class BookServiceTest {

    @Autowired
    private MockRestServiceServer server;

    @Autowired
    private MockServerRestTemplateCustomizer customizer;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private BookService service;

    @Autowired
    private ObjectMapper objectMapper;

    private Book book;

    @Before
    public void setUp() throws Exception {
        customizer.customize(restTemplate);

        book = new Book();
        book.setId(UUID.randomUUID());
        book.setTitle("Ethan Frome");
        book.setAuthor("Edith Wharton");

        String bookJson =
                objectMapper.writeValueAsString(book);

        server.expect(requestTo("/books/" + book.getId().toString()))
                .andRespond(withSuccess(bookJson, MediaType.APPLICATION_JSON));
    }

    @Test
    public void testRead()
            throws Exception {
        Book result = service.read(book.getId());
        assertNotNull(result);
        assertThat(result.getTitle()).isEqualTo(book.getTitle());
        assertThat(result.getAuthor()).isEqualTo(book.getAuthor());
    }
}
