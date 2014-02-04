package com.naphaso.cbor;

import com.naphaso.cbor.exception.CborException;
import com.naphaso.cbor.io.Input;
import com.naphaso.cbor.io.Output;
import com.naphaso.cbor.io.StreamInput;
import com.naphaso.cbor.io.StreamOutput;
import com.naphaso.cbor.serializers.Serializer;
import com.naphaso.cbor.serializers.SerializerFactory;
import com.naphaso.cbor.type.CborObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by wolong on 03/02/14.
 */
public class Cbor {
    public static byte[] serialize(Object o) {
        final ByteArrayOutputStream bos = new ByteArrayOutputStream();
        final Output output = new StreamOutput(bos);
        final CborWriter writer = new CborWriter(output);
        final Serializer serializer = SerializerFactory.getInstance().getSerializer(o.getClass());
        try {
            serializer.write(o).write(writer);
        } catch (Exception e) {
            throw new CborException("exception durning serialize", e);
        }

        return bos.toByteArray();
    }

    public static Object deserialize(byte[] data) {
        final ByteArrayInputStream bis = new ByteArrayInputStream(data);
        final Input input = new StreamInput(bis);
        final CborParser parser = new CborParser();
        try {
            final CborObject object = parser.parse(input);
            return object.toObject();
        } catch (Exception e) {
            throw new CborException("exception durning deserialize", e);
        }
    }
}
