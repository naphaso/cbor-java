package com.naphaso.cbor.type;

import com.sun.corba.se.spi.activation._ServerImplBase;

import java.math.BigInteger;
import java.util.Random;

/**
 * Created by wolong on 1/12/14.
 */
public class CborBigInteger implements CborNumber {
    private final BigInteger value;

    public CborBigInteger(BigInteger value) {
        this.value = value;
    }

    @Override
    public byte byteValue() {
        return value.byteValue();
    }

    @Override
    public short shortValue() {
        return value.shortValue();
    }

    @Override
    public int intValue() {
        return value.intValue();
    }

    @Override
    public long longValue() {
        return value.longValue();
    }

    @Override
    public float floatValue() {
        return value.floatValue();
    }

    @Override
    public double doubleValue() {
        return value.doubleValue();
    }

    @Override
    public BigInteger bigIntegerValue() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
