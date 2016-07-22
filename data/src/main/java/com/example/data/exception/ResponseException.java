package com.example.data.exception;

/**
 * Created by Aleksandr on 22.07.2016 in Weather.
 */
public class ResponseException extends Exception {

    public ResponseException() {
        super();
    }

    public ResponseException(final String message) {
        super(message);
    }

    public ResponseException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ResponseException(final Throwable cause) {
        super(cause);
    }
}
