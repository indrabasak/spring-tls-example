package com.basaki.client.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * {@code BookRequest} represents a request during client side book creation.
 * <p/>
 *
 * @author Indra Basak
 * @since 02/19/18
 */
@Data
@NoArgsConstructor
public class BookRequest {

    private String title;

    private String author;

    @JsonCreator
    public BookRequest(@JsonProperty("title") String title,
            @JsonProperty("author") String author) {
        this.title = title;
        this.author = author;
    }
}
