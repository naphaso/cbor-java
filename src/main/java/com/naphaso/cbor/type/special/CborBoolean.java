package com.naphaso.cbor.type.special;

import com.naphaso.cbor.CborWriter;
import com.naphaso.cbor.type.CborSpecial;

import java.io.IOException;

/**
 * Created by wolong on 1/12/14.
 */
public class CborBoolean extends CborSpecial {
    private boolean value;

    public CborBoolean(boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }

    @Override
    public void write(CborWriter writer) throws IOException {
        super.write(writer);

        writer.writeSpecial(value ? 21 : 22);
    }

    // type overrides

    @Override
    public boolean isBoolean() {
        return true;
    }

    @Override
    public CborBoolean asBoolean() {
        return this;
    }

    @Override
    public String toString() {
        return toStringWithTag(value ? "true" : "false");
    }
}
