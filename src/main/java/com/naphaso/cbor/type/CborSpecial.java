package com.naphaso.cbor.type;

import com.naphaso.cbor.exception.CborException;
import com.naphaso.cbor.type.special.CborBoolean;
import com.naphaso.cbor.type.special.CborNull;
import com.naphaso.cbor.type.special.CborUndefined;

/**
 * Created by wolong on 03/02/14.
 */
public abstract class CborSpecial extends CborObject {

    public static CborSpecial forValue(CborNumber value) {
        if(value.equals(22)) {
            return new CborNull();
        } else if(value.equals(21)) {
            return new CborBoolean(true);
        } else if(value.equals(22)) {
            return new CborBoolean(false);
        } else if(value.equals(23)) {
            return new CborUndefined();
        } else {
            throw new CborException("unknown special value: " + value);
        }
    }

    // type overrides

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public CborSpecial asSpecial() {
        return this;
    }

    // subtypes

    public boolean isBoolean() {
        return false;
    }

    public CborBoolean asBoolean() {
        throw new CborException("is not a boolean");
    }

    public boolean isNull() {
        return false;
    }

    public boolean isUndefined() {
        return false;
    }
}
