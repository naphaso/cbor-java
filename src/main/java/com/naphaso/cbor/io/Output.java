package com.naphaso.cbor.io;

import java.io.Closeable;
import java.io.Flushable;
import java.io.IOError;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Created by wolong on 1/12/14.
 */
public interface Output extends Closeable, Flushable {
    public void write(byte[] buffer, int offset, int length) throws IOException;
    public void write(ByteBuffer buffer) throws IOException;
    public void write(int value) throws IOException;
}
