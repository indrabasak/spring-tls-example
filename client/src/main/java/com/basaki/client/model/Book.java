package com.basaki.client.model;

import java.util.UUID;
import lombok.Data;

/**
 * {@code Book} represents a client side book entity.
 * <p/>
 *
 * @author Indra Basak
 * @since 02/19/18
 */
@Data
public class Book extends BookRequest {

    private UUID id;
}
