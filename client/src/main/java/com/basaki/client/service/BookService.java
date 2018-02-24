package com.basaki.client.service;

import com.basaki.client.model.Book;
import com.basaki.client.model.BookRequest;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final RestTemplate restTemplate;

    @Autowired
    public BookService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Book create(BookRequest request) {
        return restTemplate.postForObject("/books", request, Book.class);
    }

    public Book read(UUID id) {
        return restTemplate.getForObject("/books/{0}", Book.class, id);
    }
}
