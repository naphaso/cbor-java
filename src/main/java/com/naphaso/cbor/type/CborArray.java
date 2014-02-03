package com.naphaso.cbor.type;

import com.naphaso.cbor.CborWriter;
import com.naphaso.cbor.exception.CborException;

import java.io.IOException;
import java.util.Arrays;

/**
 * Created by wolong on 1/12/14.
 */
public class CborArray extends CborObject {
    private CborObject[] value;

    public CborArray(CborObject[] value) {
        this.value = value;
    }

    public CborObject[] getValue() {
        return value;
    }

    public void set(int index, CborObject val) {
        value[index] = val;
    }

    public CborObject get(int index) {
        return value[index];
    }

    @Override
    public void write(CborWriter writer) throws IOException {
        super.write(writer);

        writer.writeArray(value.length);
        for(int i = 0; i < value.length; i++) {
            value[i].write(writer);
        }
    }

    // type overrides

    @Override
    public boolean isArray() {
        return true;
    }

    @Override
    public CborArray asArray() {
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("[");

        for(int i = 0; i < value.length; i++) {
            builder.append(value[i]);
            if(i != value.length - 1) {
                builder.append(", ");
            }
        }

        builder.append("]");

        return toStringWithTag(builder.toString());
    }
}
