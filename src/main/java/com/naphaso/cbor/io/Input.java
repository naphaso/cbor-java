package com.naphaso.cbor.io;

import java.io.IOException;

/**
 * Created by wolong on 1/12/14.
 */
public interface Input {
    public boolean hasBytes(int len) throws IOException;
    public int getByte() throws IOException;
    public byte[] getBytes(int size) throws IOException;
    public void setListener(Runnable listener);
}
