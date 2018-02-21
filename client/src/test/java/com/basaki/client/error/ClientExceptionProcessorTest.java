package com.basaki.client.error;

import com.basaki.client.error.exception.DataNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.junit.Assert.assertEquals;

/**
 * {@code ClientExceptionProcessorTest} represents unit test for
 * {@code ClientExceptionProcessor}.
 * <p/>
 *
 * @author Indra Basak
 * @since 02/20/18
 */
public class ClientExceptionProcessorTest {

    private ClientExceptionProcessor processor;

    private MockHttpServletRequest request;

    @Before
    public void setUp() throws Exception {
        processor = new ClientExceptionProcessor();
        request = new MockHttpServletRequest();
    }

    @Test
    public void testHandleDataNotFoundException() throws Exception {
        ErrorInfo info = new ErrorInfo();
        info.setMessage("Some message");
        info.setCode(HttpStatus.NOT_FOUND.value());
        DataNotFoundException exception = new DataNotFoundException(info);
        ErrorInfo result =
                processor.handleDataNotFoundException(request, exception);
        assertEquals(info, result);
    }
}
