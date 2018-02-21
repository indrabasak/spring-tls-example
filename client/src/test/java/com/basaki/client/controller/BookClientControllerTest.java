package com.basaki.client.controller;

import com.basaki.client.error.ErrorInfo;
import com.basaki.client.error.exception.DataNotFoundException;
import com.basaki.client.model.Book;
import com.basaki.client.model.BookRequest;
import com.basaki.client.service.BookService;
import java.util.UUID;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * {@code BookClientControllerTest} represents unit test for
 * {@code BookClientController}.
 * <p/>
 *
 * @author Indra Basak
 * @since 02/20/18
 */
public class BookClientControllerTest {

    @Mock
    private BookService service;

    @InjectMocks
    private BookClientController controller;

    private Book book;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        book = new Book();
        book.setId(UUID.randomUUID());
        book.setTitle("Ethan Frome");
        book.setAuthor("Edith Wharton");
    }

    @Test
    public void testCreate() {
        when(service.create(any(BookRequest.class))).thenReturn(book);

        BookRequest request = new BookRequest("Ethan Frome", "Edith Wharton");
        Book result = controller.create(request);
        assertNotNull(result);
        assertEquals(book, result);
    }

    @Test
    public void testRead() {
        when(service.read(any(UUID.class))).thenReturn(book);

        Book result = controller.read(UUID.randomUUID());
        assertNotNull(result);
        assertEquals(book, result);
    }

    @Test(expected = DataNotFoundException.class)
    public void testDataNotFoundRead() {
        ErrorInfo info = new ErrorInfo();
        info.setCode(404);
        info.setMessage("Just a test!");

        when(service.read(any(UUID.class))).thenThrow(
                new DataNotFoundException(info));

        controller.read(UUID.randomUUID());
    }
}
