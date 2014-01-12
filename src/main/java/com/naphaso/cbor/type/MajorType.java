package com.naphaso.cbor.type;

import com.naphaso.cbor.exception.CborTypeException;

/**
 * Created by wolong on 1/12/14.
 */
public enum MajorType {
    POSITIVE_INTEGER(0),
    NEGATIVE_INTEGER(1),
    BINARY(2),
    STRING(3),
    LIST(4),
    MAP(5),
    TAG(6),
    SPECIAL(7);

    private final int value;

    MajorType(final int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static MajorType forValue(int value) {
        value = (value & 0xff) >> 5;
        switch (value) {
            case 0: return POSITIVE_INTEGER;
            case 1: return NEGATIVE_INTEGER;
            case 2: return BINARY;
            case 3: return STRING;
            case 4: return LIST;
            case 5: return MAP;
            case 6: return TAG;
            case 7: return SPECIAL;
            default: throw new CborTypeException("unknown major type " + value);
        }
    }
}
