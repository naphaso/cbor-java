package com.naphaso.cbor.exception;

/**
 * Created by wolong on 1/12/14.
 */
public class CborTypeException extends CborException {
    public CborTypeException(String message) {
        super(message);
    }

    public CborTypeException(String message, Throwable cause) {
        super(message, cause);
    }
}
