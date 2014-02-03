package com.naphaso.cbor.type;

import com.naphaso.cbor.CborWriter;
import com.naphaso.cbor.exception.CborException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by wolong on 1/12/14.
 */
public class CborMap extends CborObject {
    private HashMap<CborObject, CborObject> value;

    public CborMap(HashMap<CborObject, CborObject> value) {
        this.value = value;
    }

    public CborMap() {
        this.value = new HashMap<CborObject, CborObject>();
    }

    public Map<CborObject, CborObject> getMap() {
        return value;
    }

    @Override
    public void write(CborWriter writer) throws IOException {
        super.write(writer);

        writer.writeMap(value.size());
        for(Map.Entry<CborObject, CborObject> entry : value.entrySet()) {
            entry.getKey().write(writer);
            entry.getValue().write(writer);
        }
    }

    // type overrides

    @Override
    public boolean isMap() {
        return true;
    }

    @Override
    public CborMap asMap() {
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("{");

        final Iterator<Map.Entry<CborObject, CborObject>> iterator = value.entrySet().iterator();
        while(iterator.hasNext()) {
            final Map.Entry<CborObject, CborObject> entry = iterator.next();
            builder.append(entry.getKey());
            builder.append(": ");
            builder.append(entry.getValue());

            if(iterator.hasNext()) {
                builder.append(", ");
            }
        }
        builder.append("}");

        return toStringWithTag(builder.toString());
    }
}
