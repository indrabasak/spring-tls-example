package com.basaki.client.error;

import com.basaki.client.error.exception.DataNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;

/**
 * {@code CustomErrorHandler} handles HTTP client error response.
 * <p/>
 *
 * @author Indra Basak
 * @since 02/20/18
 */
public class CustomErrorHandler extends DefaultResponseErrorHandler {

    private final ObjectMapper objectMapper;

    public CustomErrorHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    protected void handleError(ClientHttpResponse response,
            HttpStatus statusCode) throws IOException {
        if (statusCode == HttpStatus.NOT_FOUND) {
            String message = new String(getResponseBody(response));
            ErrorInfo info = objectMapper.readValue(message, ErrorInfo.class);
            throw new DataNotFoundException(info);
        }
    }
}
