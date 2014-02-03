package com.naphaso.cbor.type;

import com.naphaso.cbor.exception.CborException;

import java.math.BigInteger;

/**
 * Created by wolong on 1/12/14.
 */
public abstract class CborNumber extends CborObject {

    @Override
    public boolean isNumber() {
        return true;
    }

    @Override
    public CborNumber asNumber() {
        return this;
    }

    // subtypes

    public abstract byte byteValue();
    public abstract short shortValue();
    public abstract int intValue();
    public abstract long longValue();
    public abstract float floatValue();
    public abstract double doubleValue();
    public abstract BigInteger bigIntegerValue();

    public abstract boolean equals(int value);
}
