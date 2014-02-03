//
// Cbor for Java
//
// Copyright (C) 2014 Stanislav Ovsyannikov (Wolong Naphaso)
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//

package com.naphaso.cbor;

import com.naphaso.cbor.exception.CborException;
import com.naphaso.cbor.io.Output;

import java.io.IOException;
import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class CborWriter {
    private Output output;

    public CborWriter(Output output) {
        this.output = output;
    }

    private void writeTypeAndValue(int majorType, int value) throws IOException {
        majorType <<= 5;
        if (value < 24) {
            output.write(majorType | value);
        } else if (value <= 255) {
            output.write(majorType | 24);
            output.write(value);
        } else if (value <= 65535) {
            output.write(majorType | 25);
            output.write(value >> 8);
            output.write(value & 0xFF);
        } else {
            output.write(majorType | 26);
            output.write((value >> 24) & 0xFF);
            output.write((value >> 16) & 0xFF);
            output.write((value >> 8) & 0xFF);
            output.write(value & 0xFF);
        }
    }

    protected void writeTypeAndValue(int majorType, long value) throws IOException {
        majorType <<= 5;
        if (value < 24L) {
            output.write(majorType | (int)value);
        } else if (value <= 255L) {
            output.write(majorType | 24);
            output.write((int)value);
        } else if (value <= 65535L) {
            output.write(majorType | 25);
            output.write((int)value >> 8);
            output.write((int)value & 0xFF);
        } else if (value <= 4294967295L) {
            output.write(majorType | 26);
            output.write((int)(value >> 24) & 0xFF);
            output.write((int)(value >> 16) & 0xFF);
            output.write((int)(value >> 8) & 0xFF);
            output.write((int) value & 0xFF);
        } else {
            output.write(majorType | 27);
            output.write((int)(value >> 56) & 0xFF);
            output.write((int)(value >> 48) & 0xFF);
            output.write((int)(value >> 40) & 0xFF);
            output.write((int)(value >> 32) & 0xFF);
            output.write((int)(value >> 24) & 0xFF);
            output.write((int)(value >> 16) & 0xFF);
            output.write((int)(value >> 8) & 0xFF);
            output.write((int)value & 0xFF);
        }
    }

    protected void writeTypeAndValue(int majorType, BigInteger value) throws IOException {
        majorType <<= 5;
        if (value.compareTo(BigInteger.valueOf(24)) == -1) {
            output.write(majorType | value.intValue());
        } else if (value.compareTo(BigInteger.valueOf(256)) == -1) {
            output.write(majorType | 24);
            output.write(value.intValue());
        } else if (value.compareTo(BigInteger.valueOf(65536L)) == -1) {
            output.write(majorType | 25);
            int twoByteValue = value.intValue();
            output.write(twoByteValue >> 8);
            output.write(twoByteValue & 0xFF);
        } else if (value.compareTo(BigInteger.valueOf(4294967296L)) == -1) {
            output.write(majorType | 26);
            long fourByteValue = value.longValue();
            output.write((int) ((fourByteValue >> 24) & 0xFF));
            output.write((int) ((fourByteValue >> 16) & 0xFF));
            output.write((int) ((fourByteValue >> 8) & 0xFF));
            output.write((int) (fourByteValue & 0xFF));
        } else if (value.compareTo(new BigInteger("18446744073709551616")) == -1) {
            output.write(majorType | 27);
            BigInteger mask = BigInteger.valueOf(0xFF);
            output.write(value.shiftRight(56).and(mask).intValue());
            output.write(value.shiftRight(48).and(mask).intValue());
            output.write(value.shiftRight(40).and(mask).intValue());
            output.write(value.shiftRight(32).and(mask).intValue());
            output.write(value.shiftRight(24).and(mask).intValue());
            output.write(value.shiftRight(16).and(mask).intValue());
            output.write(value.shiftRight(8).and(mask).intValue());
            output.write(value.and(mask).intValue());
        } else {
            if (majorType == 0) { // negative
                writeTypeAndValue(6, 3);
            } else if(majorType == 1) {
                writeTypeAndValue(6, 4);
            } else throw new CborException("unknown bignumber major type");

            byte[] data = value.toByteArray();
            if(data[0] == 0) {
                writeBytes(data, 1, data.length - 1);
            } else {
                writeBytes(data);
            }
        }
    }

    // int

    public void writeInt(int value) throws IOException {
        if(value < 0) {
            writeTypeAndValue(1, -value);
        } else {
            writeTypeAndValue(0, value);
        }
    }

    public void writeInt(long value) throws IOException {
        if(value < 0) {
            writeTypeAndValue(1, -value);
        } else {
            writeTypeAndValue(0, value);
        }
    }

    public void writeInt(BigInteger value) throws IOException {
        if(value.signum() == -1) {
            writeTypeAndValue(1, value.negate());
        } else {
            writeTypeAndValue(0, value);
        }
    }

    // bytes

    public void writeBytes(byte[] bytes, int offset, int length) throws IOException {
        writeTypeAndValue(2, length);
        output.write(bytes, offset, length);
    }

    public void writeBytes(byte[] bytes) throws IOException {
        writeTypeAndValue(2, bytes.length);
        output.write(bytes, 0, bytes.length);
    }

    // string

    public void writeString(String str) throws IOException {
        byte[] bytes = str.getBytes();
        writeTypeAndValue(3, bytes.length);
        output.write(bytes, 0, bytes.length);
    }

    // array

    public void writeArray(int size) throws IOException {
        writeTypeAndValue(4, size);
    }

    public void writeArray(long size) throws IOException {
        writeTypeAndValue(4, size);
    }

    public void writeArray(BigInteger size) throws IOException {
        writeTypeAndValue(4, size);
    }

    // map

    public void writeMap(int size) throws IOException {
        writeTypeAndValue(5, size);
    }

    public void writeMap(long size) throws IOException {
        writeTypeAndValue(5, size);
    }

    public void writeMap(BigInteger size) throws IOException {
        writeTypeAndValue(5, size);
    }

    // tag

    public void writeTag(int tag) throws IOException {
        writeTypeAndValue(6, tag);
    }

    public void writeTag(long tag) throws IOException {
        writeTypeAndValue(6, tag);
    }

    public void writeTag(BigInteger tag) throws IOException {
        writeTypeAndValue(6, tag);
    }

    // special

    public void writeSpecial(int code) throws IOException {
        writeTypeAndValue(6, code);
    }

    public void writeSpecial(long code) throws IOException {
        writeTypeAndValue(6, code);
    }

    public void writeSpecial(BigInteger code) throws IOException {
        writeTypeAndValue(6, code);
    }

    public void write(Object object) throws IOException {
        if(object == null) {
            writeSpecial(22);
        } if(object instanceof Integer) {
            writeInt((Integer) object);
        } else if(object instanceof Long) {
            writeInt((Long) object);
        } else if(object instanceof String) {
            writeString((String) object);
        } else if(object instanceof byte[]) {
            writeBytes((byte[]) object);
        } else if(object instanceof BigInteger) {
            writeInt((BigInteger) object);
        } else if(object.getClass().isArray()) {
            int length = Array.getLength(object);
            writeArray(length);
            for(int i = 0; i < length; i++) {
                Object element = Array.get(object, i);
                write(element);
            }
        } else if(object instanceof List) {
            final List list = (List) object;
            writeArray(list.size());
            for(Object element : list) {
                write(element);
            }
        } else if(object instanceof Map) {
            final Map map = (Map) object;
            writeMap(map.size());
            for(Map.Entry entry : (Set<Map.Entry>) map.entrySet()) {
                write(entry.getKey());
                write(entry.getValue());
            }
        } else if(object instanceof Boolean) {
            writeSpecial((Boolean) object ? 21 : 20);
        }


    }
}
