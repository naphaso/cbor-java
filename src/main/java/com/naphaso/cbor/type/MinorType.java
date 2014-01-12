package com.naphaso.cbor.type;

import com.naphaso.cbor.exception.CborTypeException;

/**
 * Created by wolong on 1/12/14.
 */
public enum MinorType {
    DIRECT(0),
    BYTES_1(24),
    BYTES_2(25),
    BYTES_4(26),
    BYTES_8(27),
    RESERVED_28(28),
    RESERVED_29(29),
    RESERVED_30(30),
    INDEFINITE(31);

    private final int value;

    MinorType(final int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static MinorType forValue(int value) {
        value &= 31;
        if(value < 24) return DIRECT;
        switch (value) {
            case 24: return BYTES_1;
            case 25: return BYTES_2;
            case 26: return BYTES_4;
            case 27: return BYTES_8;
            case 28: return RESERVED_28;
            case 29: return RESERVED_29;
            case 30: return RESERVED_30;
            case 31: return INDEFINITE;
            default: throw new CborTypeException("unknown major type " + value);
        }
    }
}
