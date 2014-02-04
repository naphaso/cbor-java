package com.naphaso.cbor.serializers;

import com.naphaso.cbor.type.CborObject;

import java.math.BigInteger;

/**
 * Created by wolong on 03/02/14.
 */
public abstract class Serializer<T> {
    public abstract CborObject write(T object);
    public abstract T read(CborObject object);


}
