package com.naphaso.cbor.type;

/**
 * Created by wolong on 1/12/14.
 */
public class CborLong extends CborNumber {
    private long value;

    public CborLong(long value) {
        this.value = value;
    }
}
