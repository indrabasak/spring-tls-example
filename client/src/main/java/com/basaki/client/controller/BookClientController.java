package com.basaki.client.controller;

import com.basaki.client.model.Book;
import com.basaki.client.model.BookRequest;
import com.basaki.client.service.BookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * {@code BookClientController} exposes server side book service API.
 * <p/>
 *
 * @author Indra Basak
 * @since 02/19/18
 */
@RestController
@Slf4j
@Api(value = "Client Book Service", produces = "application/json", tags = {"1"})
public class BookClientController {

    private final BookService service;

    @Autowired
    public BookClientController(BookService service) {
        this.service = service;
    }

    @ApiOperation(value = "Creates a book.", response = Book.class)
    @RequestMapping(method = RequestMethod.POST, value = "/client/books")
    @ResponseStatus(HttpStatus.CREATED)
    public Book create(@RequestBody BookRequest request) {
        return service.create(request);
    }

    @ApiOperation(value = "Retrieves a book.", notes = "Requires book identifier",
            response = Book.class)
    @RequestMapping(method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_JSON_VALUE}, value = "/client/books/{id}")
    public Book read(@PathVariable("id") UUID id) {
        return service.read(id);
    }
}
