package com.naphaso.cbor.type;

import java.math.BigInteger;

/**
 * Created by wolong on 1/12/14.
 */
public interface CborNumber extends CborObject {
    byte byteValue();
    short shortValue();
    int intValue();
    long longValue();
    float floatValue();
    double doubleValue();
    BigInteger bigIntegerValue();
}
