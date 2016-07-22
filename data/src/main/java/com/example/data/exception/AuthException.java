package com.example.data.exception;

/**
 * Created by Aleksandr on 22.07.2016 in Weather.
 */
public class AuthException extends Exception {

    public AuthException() {
        super();
    }

    public AuthException(final String message) {
        super(message);
    }

    public AuthException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public AuthException(final Throwable cause) {
        super(cause);
    }
}
