package com.naphaso.cbor.io;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Created by wolong on 04/02/14.
 */
public class DummyOutput implements Output {
    @Override
    public void write(byte[] buffer, int offset, int length) throws IOException {

    }

    @Override
    public void write(ByteBuffer buffer) throws IOException {

    }

    @Override
    public void write(int value) throws IOException {

    }

    @Override
    public void close() throws IOException {

    }

    @Override
    public void flush() throws IOException {

    }
}
