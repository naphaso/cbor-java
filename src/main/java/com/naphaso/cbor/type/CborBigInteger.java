package com.naphaso.cbor.type;

import java.math.BigInteger;

/**
 * Created by wolong on 1/12/14.
 */
public class CborBigInteger extends CborNumber {
    private BigInteger value;

    public CborBigInteger(BigInteger value) {
        this.value = value;
    }
}
