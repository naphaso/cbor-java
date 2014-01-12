package com.naphaso.cbor.type;

/**
 * Created by wolong on 1/12/14.
 */
public class CborInteger extends CborNumber {
    private int value;

    public CborInteger(int value) {
        this.value = value;
    }

    public CborInteger(int value, boolean negate) {
        this.value = negate ? -value : value;
    }
}
