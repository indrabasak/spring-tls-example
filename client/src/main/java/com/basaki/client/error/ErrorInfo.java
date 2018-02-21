package com.basaki.client.error;

import java.io.Serializable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * {@code ErrorInfo} represents an error response object which is exposed to
 * the external clientin a human readable format.
 * <p/>
 *
 * @author Indra Basak
 * @since 02/20/18
 */
@NoArgsConstructor
@Getter
@Setter
public class ErrorInfo implements Serializable {

    private int code;

    private String type;

    private String message;
}
