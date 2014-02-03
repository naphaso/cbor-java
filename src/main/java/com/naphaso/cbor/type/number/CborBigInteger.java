package com.naphaso.cbor.type.number;

import com.naphaso.cbor.CborWriter;
import com.naphaso.cbor.type.CborNumber;
import com.sun.corba.se.spi.activation._ServerImplBase;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Random;

/**
 * Created by wolong on 1/12/14.
 */
public class CborBigInteger extends CborNumber {
    private final BigInteger value;

    public CborBigInteger(BigInteger value) {
        this.value = value;
    }

    @Override
    public void write(CborWriter writer) throws IOException {
        super.write(writer);

        writer.writeInt(value);
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
    public boolean equals(int value) {
        return this.value.equals(BigInteger.valueOf(value));
    }

    @Override
    public String toString() {
        return toStringWithTag(value.toString());
    }
}
