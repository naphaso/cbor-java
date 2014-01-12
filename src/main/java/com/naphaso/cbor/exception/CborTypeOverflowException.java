package com.naphaso.cbor.exception;

/**
 * Created by wolong on 1/12/14.
 */
public class CborTypeOverflowException extends CborTypeException {

    public CborTypeOverflowException(String message) {
        super(message);
    }

    public CborTypeOverflowException(String message, Throwable cause) {
        super(message, cause);
    }
}
