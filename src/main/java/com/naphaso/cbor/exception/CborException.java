package com.naphaso.cbor.exception;

/**
 * Created by wolong on 1/12/14.
 */
public class CborException extends RuntimeException {
    public CborException(String message) {
        super(message);
    }

    public CborException(String message, Throwable cause) {
        super(message, cause);
    }
}
