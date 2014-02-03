package com.naphaso.cbor.type.number;

import com.naphaso.cbor.CborWriter;
import com.naphaso.cbor.type.CborNumber;

import java.io.IOException;
import java.math.BigInteger;

/**
 * Created by wolong on 1/12/14.
 */
public class CborInteger extends CborNumber {
    private long value;

    public CborInteger(long value) {
        this.value = value;
    }

    @Override
    public void write(CborWriter writer) throws IOException {
        super.write(writer);

        writer.writeInt(value);
    }

    @Override
    public byte byteValue() {
        if(value >= Byte.MIN_VALUE && value <= Byte.MAX_VALUE) {
            return (byte) value;
        } else {
            throw new NumberFormatException("Value is out of range : " + value);
        }
    }

    @Override
    public short shortValue() {
        if(value >= Short.MIN_VALUE && value <= Short.MAX_VALUE) {
            return (short) value;
        } else {
            throw new NumberFormatException("Value is out of range : " + value);
        }
    }

    @Override
    public int intValue() {
        if(value >= Integer.MIN_VALUE && value <= Integer.MAX_VALUE) {
            return (int) value;
        } else {
            throw new NumberFormatException("Value is out of range : " + value);
        }
    }

    @Override
    public long longValue() {
        return value;
    }

    @Override
    public float floatValue() {
        if(value >= Float.MIN_VALUE && value <= Float.MAX_VALUE) {
            return (float) value;
        } else {
            throw new NumberFormatException("Value is out of range : " + value);
        }
    }

    @Override
    public double doubleValue() {
        return (double) value;
    }

    @Override
    public BigInteger bigIntegerValue() {
        return BigInteger.valueOf(value);
    }

    @Override
    public boolean equals(int value) {
        return this.value == value;
    }

    @Override
    public String toString() {
        return toStringWithTag(Long.toString(value));
    }
}
