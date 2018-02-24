package com.basaki.client.model;

import java.util.UUID;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * {@code Book} represents a client side book entity.
 * <p/>
 *
 * @author Indra Basak
 * @since 02/19/18
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class Book extends BookRequest {

    private UUID id;
}
