package com.naphaso.cbor;

/**
 * Created by wolong on 02/02/14.
 */
public interface CborSerializable {
    void write(CborWriter writer);
    void read(CborReader reader);
}
