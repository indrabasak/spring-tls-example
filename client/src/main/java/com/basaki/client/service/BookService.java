package com.basaki.client.service;

import com.basaki.client.model.Book;
import com.basaki.client.model.BookRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * {@code BookService} provides CRUD functionality on book by calling Book
 * server REST service.
 * <p/>
 *
 * @author Indra Basak
 * @since 02/19/18
 */
@Service
public class BookService {

    private RestTemplate restTemplate;

    @Autowired
    public BookService(ObjectMapper objectMapper,
            ClientBookProperties properties) {
        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();

        restTemplate = restTemplateBuilder
                .rootUri(properties.getUrl())
                .setConnectTimeout(properties.getConnectionTimeout())
                .setReadTimeout(properties.getReadTimeout())
                .messageConverters(
                        new MappingJackson2HttpMessageConverter(objectMapper),
                        new StringHttpMessageConverter())
                .build();
    }

    public Book create(BookRequest request) {
        return restTemplate.postForObject("/books", request, Book.class);
    }

    public Book read(UUID id) {
        return restTemplate.getForObject("/books/{0}", Book.class, id);
    }
}
