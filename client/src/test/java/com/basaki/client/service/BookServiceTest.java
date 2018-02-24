package com.basaki.client.service;

import com.basaki.client.error.ErrorInfo;
import com.basaki.client.error.exception.DataNotFoundException;
import com.basaki.client.model.Book;
import com.basaki.client.model.BookRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.UUID;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.web.client.MockServerRestTemplateCustomizer;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
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
    }

    @Test
    public void testCreate() throws Exception {
        BookRequest request =
                new BookRequest(book.getTitle(), book.getAuthor());
        String requestJson = objectMapper.writeValueAsString(request);

        String bookJson = objectMapper.writeValueAsString(book);

        server.expect(requestTo("/books"))
                .andExpect(method(HttpMethod.POST))
                .andExpect(content().contentType(
                        MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().string(requestJson))
                .andRespond(withSuccess(bookJson, MediaType.APPLICATION_JSON));

        Book result = service.create(request);
        assertNotNull(result);
        assertThat(result.getTitle()).isEqualTo(book.getTitle());
        assertThat(result.getAuthor()).isEqualTo(book.getAuthor());
    }

    @Test
    public void testRead() throws Exception {
        String bookJson = objectMapper.writeValueAsString(book);

        server.expect(requestTo("/books/" + book.getId().toString()))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(bookJson, MediaType.APPLICATION_JSON));

        Book result = service.read(book.getId());
        assertNotNull(result);
        assertThat(result.getTitle()).isEqualTo(book.getTitle());
        assertThat(result.getAuthor()).isEqualTo(book.getAuthor());
        assertEquals(book, result);
    }

    @Test(expected = DataNotFoundException.class)
    public void testReadNotFound() throws Exception {
        ErrorInfo info = new ErrorInfo();
        info.setCode(HttpStatus.NOT_FOUND.value());
        info.setMessage("Book not found!");
        info.setType(HttpStatus.NOT_FOUND.getReasonPhrase());
        String infoJson = objectMapper.writeValueAsString(info);

        server.expect(requestTo("/books/" + book.getId().toString()))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.NOT_FOUND).body(infoJson));

        service.read(book.getId());
    }
}
