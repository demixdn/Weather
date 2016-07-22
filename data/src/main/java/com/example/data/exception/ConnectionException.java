package com.example.data.exception;

/**
 * Exception throw by the application when a there is a network connection exception.
 */
public class ConnectionException extends Exception {

  public ConnectionException() {
    super();
  }

  public ConnectionException(final String message) {
    super(message);
  }

  public ConnectionException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public ConnectionException(final Throwable cause) {
    super(cause);
  }
}
