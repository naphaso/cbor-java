package com.naphaso.cbor.type;

import com.naphaso.cbor.CborWriter;
import com.naphaso.cbor.exception.CborException;

import java.io.IOException;

/**
 * Created by wolong on 1/12/14.
 */
public abstract class CborObject {
    private CborNumber tag;

    public void setTag(CborNumber tag) {
        this.tag = tag;
    }


    public void write(CborWriter writer) throws IOException {
        if(tag != null) {
            writer.writeTag(tag.intValue()); // TODO: fix for more big tags
        }
    }

    public CborNumber getTag() {
        return tag;
    }

    public boolean isString() {
        return false;
    }

    public CborString asString() {
        throw new CborException("is not a string");
    }

    public boolean isBytes() {
        return false;
    }

    public CborBytes asBytes() {
        throw new CborException("is not a bytes");
    }

    public boolean isNumber() {
        return false;
    }

    public CborNumber asNumber() {
        throw new CborException("is not a number");
    }

    public boolean isMap() {
        return false;
    }

    public CborMap asMap() {
        throw new CborException("is not a map");
    }

    public boolean isArray() {
        return false;
    }

    public CborArray asArray() {
        throw new CborException("is not a array");
    }

    public boolean isSpecial() {
        return false;
    }

    public CborSpecial asSpecial() {
        throw new CborException("is not a special");
    }

    protected String toStringWithTag(String value) {
        if(tag == null) {
            return value;
        } else {
            return tag.toString() + "#" + value;
        }
    }
}
