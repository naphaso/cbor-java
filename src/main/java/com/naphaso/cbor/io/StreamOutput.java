package com.naphaso.cbor.io;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

/**
 * Created by wolong on 1/12/14.
 */
public class StreamOutput implements Output {
    private final DataOutputStream output;

    public StreamOutput(OutputStream output) {
        this.output = new DataOutputStream(output);
    }

    @Override
    public void write(byte[] buffer, int offset, int length) throws IOException {
        output.write(buffer, offset, length);
    }

    @Override
    public void write(ByteBuffer buffer) throws IOException {
        if(buffer.hasArray()) {
            output.write(buffer.array(), buffer.arrayOffset(), buffer.remaining());
            buffer.position(buffer.limit());
        } else {
            final byte[] buf = new byte[buffer.remaining()];
            buffer.get(buf);
            output.write(buf);
        }
    }

    @Override
    public void write(int value) throws IOException {
        output.writeByte(value);
    }

    /*
    @Override
    public void write(short value) throws IOException {
        output.writeShort(value);
    }

    @Override
    public void write(int value) throws IOException {
        output.writeInt(value);
    }

    @Override
    public void write(long value) throws IOException {
        output.writeLong(value);
    }

    @Override
    public void write(float value) throws IOException {
        output.writeFloat(value);
    }

    @Override
    public void write(double value) throws IOException {
        output.writeDouble(value);
    }
*/
    @Override
    public void close() throws IOException {
        output.close();
    }

    @Override
    public void flush() throws IOException {
        output.flush();
    }
}
