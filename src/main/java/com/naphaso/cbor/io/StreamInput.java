package com.naphaso.cbor.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by wolong on 1/12/14.
 */
public class StreamInput implements Input {
    private InputStream in;

    public StreamInput(InputStream in) {
        this.in = in;
    }


    @Override
    public boolean hasBytes(int len) throws IOException {
        return in.available() >= len;
    }

    @Override
    public int getByte() throws IOException {
        return in.read();
    }

    @Override
    public byte[] getBytes(int size) throws IOException {
        byte[] data = new byte[size];
        in.read(data);
        return data;
    }

    @Override
    public void setListener(Runnable listener) {

    }
}
