package com.naphaso.cbor;

import com.naphaso.cbor.exception.CborException;
import com.naphaso.cbor.exception.CborTypeException;
import com.naphaso.cbor.io.Input;
import com.naphaso.cbor.type.*;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.Charset;

/**
 * Created by wolong on 1/12/14.
 */
public class CborReader {
    private final static Charset stringCharset = Charset.forName("UTF-8");

    private final Input input;

    private static enum ScannerTypeState {
        TYPE,
        PINT,
        NINT,
        BYTES_SIZE,
        BYTES_DATA,
        STRING_SIZE,
        STRING_DATA,
        ARRAY,
        MAP,
        TAG,
        SPECIAL
    }

    private int currentSize;

    private ScannerTypeState state = ScannerTypeState.TYPE;
    private CborListener listener;

    public CborReader(Input input) {
        this.input = input;
    }

    public void setListener(CborListener listener) {
        this.listener = listener;
    }

    public void run() throws IOException {
        while(true) {
            if(state == ScannerTypeState.TYPE) {
                if(input.hasBytes(1)) {
                    int type = input.getByte();

                    int majorType = (type & 0xff) >> 5;
                    int minorType = type & 31;

                    switch(majorType) {
                        case 0: // positive integer
                            if(minorType < 24) {
                                listener.onNumber(new CborShort(minorType));
                            } else if(minorType == 24) {
                                state = ScannerTypeState.PINT;
                                currentSize = 1;
                            } else if(minorType == 25) {
                                state = ScannerTypeState.PINT;
                                currentSize = 2;
                            } else if(minorType == 26) {
                                state = ScannerTypeState.PINT;
                                currentSize = 4;
                            } else if(minorType == 27) {
                                state = ScannerTypeState.PINT;
                                currentSize = 8;
                            } else throw new CborTypeException("invalid type");
                            break;
                        case 1: // negative integer
                            if(minorType < 24) {
                                listener.onNumber(new CborShort(-minorType));
                            } else if(minorType == 24) {
                                state = ScannerTypeState.NINT;
                                currentSize = 1;
                            } else if(minorType == 25) {
                                state = ScannerTypeState.NINT;
                                currentSize = 2;
                            } else if(minorType == 26) {
                                state = ScannerTypeState.NINT;
                                currentSize = 4;
                            } else if(minorType == 27) {
                                state = ScannerTypeState.NINT;
                                currentSize = 8;
                            } else throw new CborTypeException("invalid type");
                            break;
                        case 2: // bytes
                            if(minorType < 24) {
                                state = ScannerTypeState.BYTES_DATA;
                                currentSize = minorType;
                            } else if(minorType == 24) {
                                state = ScannerTypeState.BYTES_SIZE;
                                currentSize = 1;
                            } else if(minorType == 25) {
                                state = ScannerTypeState.BYTES_SIZE;
                                currentSize = 2;
                            } else if(minorType == 26) {
                                state = ScannerTypeState.BYTES_SIZE;
                                currentSize = 4;
                            } else if(minorType == 27) {
                                state = ScannerTypeState.BYTES_SIZE;
                                currentSize = 8;
                            } else throw new CborTypeException("invalid type");
                            break;
                        case 3: // string
                            if(minorType < 24) {
                                state = ScannerTypeState.STRING_DATA;
                                currentSize = minorType;
                            } else if(minorType == 24) {
                                state = ScannerTypeState.STRING_SIZE;
                                currentSize = 1;
                            } else if(minorType == 25) {
                                state = ScannerTypeState.STRING_SIZE;
                                currentSize = 2;
                            } else if(minorType == 26) {
                                state = ScannerTypeState.STRING_SIZE;
                                currentSize = 4;
                            } else if(minorType == 27) {
                                state = ScannerTypeState.STRING_SIZE;
                                currentSize = 8;
                            } else throw new CborTypeException("invalid type");
                            break;
                        case 4: // array
                            if(minorType < 24) {
                                listener.onArray(new CborShort(minorType));
                            } else if(minorType == 24) {
                                state = ScannerTypeState.ARRAY;
                                currentSize = 1;
                            } else if(minorType == 25) {
                                state = ScannerTypeState.ARRAY;
                                currentSize = 2;
                            } else if(minorType == 26) {
                                state = ScannerTypeState.ARRAY;
                                currentSize = 4;
                            } else if(minorType == 27) {
                                state = ScannerTypeState.ARRAY;
                                currentSize = 8;
                            } else throw new CborTypeException("invalid type");
                            break;
                        case 5: // map
                            if(minorType < 24) {
                                listener.onMap(new CborShort(minorType));
                            } else if(minorType == 24) {
                                state = ScannerTypeState.MAP;
                                currentSize = 1;
                            } else if(minorType == 25) {
                                state = ScannerTypeState.MAP;
                                currentSize = 2;
                            } else if(minorType == 26) {
                                state = ScannerTypeState.MAP;
                                currentSize = 4;
                            } else if(minorType == 27) {
                                state = ScannerTypeState.MAP;
                                currentSize = 8;
                            } else throw new CborTypeException("invalid type");
                            break;
                        case 6: // tag
                            if(minorType < 24) {
                                listener.onTag(new CborShort(minorType));
                            } else if(minorType == 24) {
                                state = ScannerTypeState.TAG;
                                currentSize = 1;
                            } else if(minorType == 25) {
                                state = ScannerTypeState.TAG;
                                currentSize = 2;
                            } else if(minorType == 26) {
                                state = ScannerTypeState.TAG;
                                currentSize = 4;
                            } else if(minorType == 27) {
                                state = ScannerTypeState.TAG;
                                currentSize = 8;
                            } else throw new CborTypeException("invalid type");
                            break;
                        case 7: // special
                            if(minorType < 24) {
                                listener.onSpecial(new CborShort(minorType));
                            } else if(minorType == 24) {
                                state = ScannerTypeState.SPECIAL;
                                currentSize = 1;
                            } else if(minorType == 25) {
                                state = ScannerTypeState.SPECIAL;
                                currentSize = 2;
                            } else if(minorType == 26) {
                                state = ScannerTypeState.SPECIAL;
                                currentSize = 4;
                            } else if(minorType == 27) {
                                state = ScannerTypeState.SPECIAL;
                                currentSize = 8;
                            } else throw new CborTypeException("invalid type");
                            break;
                    }
                } else break;
            } else if(state == ScannerTypeState.PINT) {
                if(input.hasBytes(currentSize)) {
                    listener.onNumber(parsePositive(currentSize));
                    state = ScannerTypeState.TYPE;
                } else break;
            } else if(state == ScannerTypeState.NINT) {
                if(input.hasBytes(currentSize)) {
                    listener.onNumber(parseNegative(currentSize));
                    state = ScannerTypeState.TYPE;
                } else break;
            } else if(state == ScannerTypeState.BYTES_SIZE) {
                if(input.hasBytes(currentSize)) {
                    currentSize = parsePositive(currentSize).intValue();
                    state = ScannerTypeState.BYTES_DATA;
                } else break;
            } else if(state == ScannerTypeState.BYTES_DATA) {
                if(input.hasBytes(currentSize)) {
                    listener.onBytes(input.getBytes(currentSize));
                    state = ScannerTypeState.TYPE;
                } else break;
            } else if(state == ScannerTypeState.STRING_SIZE) {
                if(input.hasBytes(currentSize)) {
                    currentSize = parsePositive(currentSize).intValue();
                    state = ScannerTypeState.STRING_DATA;
                } else break;
            } else if(state == ScannerTypeState.STRING_DATA) {
                if(input.hasBytes(currentSize)) {
                    listener.onString(new String(input.getBytes(currentSize), stringCharset));
                    state = ScannerTypeState.TYPE;
                } else break;
            } else if(state == ScannerTypeState.ARRAY) {
                if(input.hasBytes(currentSize)) {
                    listener.onArray(parsePositive(currentSize));
                    state = ScannerTypeState.TYPE;
                } else break;
            } else if(state == ScannerTypeState.MAP) {
                if(input.hasBytes(currentSize)) {
                    listener.onMap(parsePositive(currentSize));
                    state = ScannerTypeState.TYPE;
                } else break;
            } else if(state == ScannerTypeState.TAG) {
                if(input.hasBytes(currentSize)) {
                    listener.onTag(parsePositive(currentSize));
                    state = ScannerTypeState.TYPE;
                } else break;
            } else if(state == ScannerTypeState.SPECIAL) {
                if(input.hasBytes(currentSize)) {
                    listener.onSpecial(parsePositive(currentSize));
                    state = ScannerTypeState.TYPE;
                } else break;
            }
        }
    }

    private CborNumber parsePositive(int size) throws IOException {
        switch (size) {
            case 1:
                return parsePositiveInt8();
            case 2:
                return parsePositiveInt16();
            case 4:
                return parsePositiveInt32();
            case 8:
                return parsePositiveInt64();
        }

        throw new CborException("unknown number size: " + size);
    }

    private CborNumber parseNegative(int size) throws IOException {
        switch (size) {
            case 1:
                return parserNegativeInt8();
            case 2:
                return parseNegativeInt16();
            case 4:
                return parseNegativeInt32();
            case 8:
                return parseNegativeInt64();
        }

        throw new CborException("unknown number size: " + size);
    }

    private CborNumber parsePositiveInt8() throws IOException {
        final int value = input.getByte() & 0xff;
        return new CborShort(value);
    }

    private CborNumber parserNegativeInt8() throws IOException {
        final int value = input.getByte() & 0xff;
        return new CborShort(-value);
    }

    private CborNumber parsePositiveInt16() throws IOException {
        final int value = ((input.getByte() & 0xff) << 8) | (input.getByte() & 0xff);
        return new CborShort(value);
    }

    private CborNumber parseNegativeInt16() throws IOException {
        final int value = ((input.getByte() & 0xff) << 8) | (input.getByte() & 0xff);
        return new CborShort(-value);
    }

    private CborNumber parsePositiveInt32() throws IOException {
        final int value = ((input.getByte() & 0xff) << 24) | ((input.getByte() & 0xff) << 16) | ((input.getByte() & 0xff) << 8) | (input.getByte() & 0xff);
        return value < 0 ? new CborInteger(value & 0xffffffffL) : new CborShort(value);
    }

    private CborNumber parseNegativeInt32() throws IOException {
        final int value = ((input.getByte() & 0xff) << 24) | ((input.getByte() & 0xff) << 16) | ((input.getByte() & 0xff) << 8) | (input.getByte() & 0xff);
        return value < 0 ? new CborInteger(-(value & 0xffffffffL)) : new CborShort(-value);
    }

    private CborNumber parsePositiveInt64() throws IOException {
        BigInteger value = BigInteger.ZERO;
        for(int i = 0; i < 8; i++) {
            value = value.shiftLeft(8).or(BigInteger.valueOf(input.getByte() & 0xff));
        }

        return new CborBigInteger(value);
    }

    private CborNumber parseNegativeInt64() throws IOException {
        BigInteger value = BigInteger.ZERO;
        for(int i = 0; i < 8; i++) {
            value = value.shiftLeft(8).or(BigInteger.valueOf(input.getByte() & 0xff));
        }

        return new CborBigInteger(value.negate());
    }



}
