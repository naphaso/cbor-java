package com.naphaso.cbor.type.special;

import com.naphaso.cbor.CborWriter;
import com.naphaso.cbor.exception.CborException;
import com.naphaso.cbor.type.CborSpecial;

import java.io.IOException;

/**
 * Created by wolong on 03/02/14.
 */
public class CborNull extends CborSpecial {
    @Override
    public boolean isNull() {
        return true;
    }

    @Override
    public void write(CborWriter writer) throws IOException {
        super.write(writer);

        writer.writeSpecial(22);
    }

    @Override
    public String toString() {
        return toStringWithTag("null");
    }
}
