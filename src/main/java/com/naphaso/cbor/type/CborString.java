package com.naphaso.cbor.type;

import com.naphaso.cbor.CborWriter;
import com.naphaso.cbor.exception.CborException;

import java.io.IOException;

/**
 * Created by wolong on 03/02/14.
 */
public class CborString extends CborObject {
    private String value;

    public CborString(String value) {
        this.value = value;
    }

    @Override
    public void write(CborWriter writer) throws IOException {
        super.write(writer);

        writer.writeString(value);
    }

    public String getValue() {
        return value;
    }

    // type overrides

    @Override
    public boolean isString() {
        return true;
    }

    @Override
    public CborString asString() {
        return this;
    }

    @Override
    public String toString() {
        return toStringWithTag("\"" + value + "\"");
    }
}
