package com.basaki.server.error.exception;

/**
 * {@code SecurityConfigurationException} exception is thrown if any problem
 * is encountered during security setup.
 * <p/>
 *
 * @author Indra Basak
 * @since 12/28/16
 */
public class SecurityConfigurationException extends RuntimeException {

    public SecurityConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
}
