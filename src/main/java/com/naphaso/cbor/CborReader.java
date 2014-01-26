package com.naphaso.cbor;

import com.naphaso.cbor.exception.CborTypeException;
import com.naphaso.cbor.io.Input;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Created by wolong on 1/12/14.
 */
public class CborReader {
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

    private int currentNumberLength;

    private ScannerTypeState state = ScannerTypeState.TYPE;

    public CborReader(Input input) {
        this.input = input;
    }

    private void process() {
        byte type = 123;

    }

    public void run() throws IOException {
        while(true) {
            if(state == ScannerTypeState.TYPE) {
                if(input.hasBytes(1)) {
                    int type = input.getByte();

                    int majorType = (type & 0xff) >> 5;
                    int minorType = type & 31;
                    //System.out.println("[DEBUG] major  " + majorType + ", minor " + minorType);
                    switch(majorType) {
                        case 0: // positive integer
                            if(minorType < 24) {
                                onInteger(minorType);
                            } else if(minorType == 24) {
                                state = ScannerTypeState.PINT;
                                currentNumberLength = 1;
                            } else if(minorType == 25) {
                                state = ScannerTypeState.PINT;
                                currentNumberLength = 2;
                            } else if(minorType == 26) {
                                state = ScannerTypeState.PINT;
                                currentNumberLength = 4;
                            } else if(minorType == 27) {
                                state = ScannerTypeState.PINT;
                                currentNumberLength = 8;
                            } else throw new CborTypeException("invalid type");
                            break;
                        case 1: // negative integer
                            if(minorType < 24) {
                                onInteger(minorType);
                            } else if(minorType == 24) {
                                state = ScannerTypeState.NINT;
                                currentNumberLength = 1;
                            } else if(minorType == 25) {
                                state = ScannerTypeState.NINT;
                                currentNumberLength = 2;
                            } else if(minorType == 26) {
                                state = ScannerTypeState.NINT;
                                currentNumberLength = 4;
                            } else if(minorType == 27) {
                                state = ScannerTypeState.NINT;
                                currentNumberLength = 8;
                            } else throw new CborTypeException("invalid type");
                            break;
                        case 2: // bytes
                            if(minorType < 24) {
                                state = ScannerTypeState.BYTES_DATA;
                                currentNumberLength = minorType;
                            } else if(minorType == 24) {
                                state = ScannerTypeState.BYTES_SIZE;
                                currentNumberLength = 1;
                            } else if(minorType == 25) {
                                state = ScannerTypeState.BYTES_SIZE;
                                currentNumberLength = 2;
                            } else if(minorType == 26) {
                                state = ScannerTypeState.BYTES_SIZE;
                                currentNumberLength = 4;
                            } else if(minorType == 27) {
                                state = ScannerTypeState.BYTES_SIZE;
                                currentNumberLength = 8;
                            } else throw new CborTypeException("invalid type");
                            break;
                        case 3: // string
                            if(minorType < 24) {
                                state = ScannerTypeState.STRING_DATA;
                                currentNumberLength = minorType;
                            } else if(minorType == 24) {
                                state = ScannerTypeState.STRING_SIZE;
                                currentNumberLength = 1;
                            } else if(minorType == 25) {
                                state = ScannerTypeState.STRING_SIZE;
                                currentNumberLength = 2;
                            } else if(minorType == 26) {
                                state = ScannerTypeState.STRING_SIZE;
                                currentNumberLength = 4;
                            } else if(minorType == 27) {
                                state = ScannerTypeState.STRING_SIZE;
                                currentNumberLength = 8;
                            } else throw new CborTypeException("invalid type");
                            break;
                        case 4: // array
                            if(minorType < 24) {
                                onArray(minorType);
                            } else if(minorType == 24) {
                                state = ScannerTypeState.ARRAY;
                                currentNumberLength = 1;
                            } else if(minorType == 25) {
                                state = ScannerTypeState.ARRAY;
                                currentNumberLength = 2;
                            } else if(minorType == 26) {
                                state = ScannerTypeState.ARRAY;
                                currentNumberLength = 4;
                            } else if(minorType == 27) {
                                state = ScannerTypeState.ARRAY;
                                currentNumberLength = 8;
                            } else throw new CborTypeException("invalid type");
                            break;
                        case 5: // map
                            if(minorType < 24) {
                                onMap(minorType);
                            } else if(minorType == 24) {
                                state = ScannerTypeState.MAP;
                                currentNumberLength = 1;
                            } else if(minorType == 25) {
                                state = ScannerTypeState.MAP;
                                currentNumberLength = 2;
                            } else if(minorType == 26) {
                                state = ScannerTypeState.MAP;
                                currentNumberLength = 4;
                            } else if(minorType == 27) {
                                state = ScannerTypeState.MAP;
                                currentNumberLength = 8;
                            } else throw new CborTypeException("invalid type");
                            break;
                        case 6: // tag
                            if(minorType < 24) {
                                onTag(minorType);
                            } else if(minorType == 24) {
                                state = ScannerTypeState.TAG;
                                currentNumberLength = 1;
                            } else if(minorType == 25) {
                                state = ScannerTypeState.TAG;
                                currentNumberLength = 2;
                            } else if(minorType == 26) {
                                state = ScannerTypeState.TAG;
                                currentNumberLength = 4;
                            } else if(minorType == 27) {
                                state = ScannerTypeState.TAG;
                                currentNumberLength = 8;
                            } else throw new CborTypeException("invalid type");
                            break;
                        case 7: // special
                            if(minorType < 24) {
                                onSpecial(minorType);
                            } else if(minorType == 24) {
                                state = ScannerTypeState.SPECIAL;
                                currentNumberLength = 1;
                            } else if(minorType == 25) {
                                state = ScannerTypeState.SPECIAL;
                                currentNumberLength = 2;
                            } else if(minorType == 26) {
                                state = ScannerTypeState.SPECIAL;
                                currentNumberLength = 4;
                            } else if(minorType == 27) {
                                state = ScannerTypeState.SPECIAL;
                                currentNumberLength = 8;
                            } else throw new CborTypeException("invalid type");
                            break;
                    }
                } else break;
            } else if(state == ScannerTypeState.PINT) {
                if(input.hasBytes(currentNumberLength)) {
                    switch (currentNumberLength) {
                        case 1:
                            onInteger(input.getByte() & 0xff);
                            state = ScannerTypeState.TYPE;
                            break;
                        case 2:
                            onInteger(((input.getByte() & 0xff) << 8) | (input.getByte() & 0xff));
                            state = ScannerTypeState.TYPE;
                            break;
                        case 4:
                            parsePositiveInt32();
                            state = ScannerTypeState.TYPE;
                            break;
                        case 8:
                            onInteger(123); // TODO: implement it
                            state = ScannerTypeState.TYPE;
                            break;
                    }
                } else break;
            } else if(state == ScannerTypeState.NINT) {
                if(input.hasBytes(currentNumberLength)) {
                    switch (currentNumberLength) {
                        case 1:
                            onInteger(-(input.getByte() & 0xff));
                            state = ScannerTypeState.TYPE;
                            break;
                        case 2:
                            onInteger(-(((input.getByte() & 0xff) << 8) | (input.getByte() & 0xff)));
                            state = ScannerTypeState.TYPE;
                            break;
                        case 4:
                            parseNegativeInt32();
                            state = ScannerTypeState.TYPE;
                            break;
                        case 8:
                            onInteger(-123); // TODO: implement it
                            state = ScannerTypeState.TYPE;
                            break;
                    }
                } else break;
            } else if(state == ScannerTypeState.BYTES_SIZE) {
                if(input.hasBytes(currentNumberLength)) {
                    switch (currentNumberLength) {
                        case 1:
                            currentNumberLength = input.getByte() & 0xff;
                            state = ScannerTypeState.BYTES_DATA;
                            break;
                        case 2:
                            currentNumberLength = ((input.getByte() & 0xff) << 8) | (input.getByte() & 0xff);
                            state = ScannerTypeState.BYTES_DATA;
                            break;
                        case 4:
                            // TODO: implement large bytes
                            state = ScannerTypeState.TYPE;
                            break;
                        case 8:
                            // TODO: implement very large bytes
                            state = ScannerTypeState.TYPE;
                            break;
                    }
                } else break;
            } else if(state == ScannerTypeState.BYTES_DATA) {
                if(input.hasBytes(currentNumberLength)) {
                    onBytes(input.getBytes(currentNumberLength));
                    state = ScannerTypeState.TYPE;
                } else break;
            } else if(state == ScannerTypeState.STRING_SIZE) {
                if(input.hasBytes(currentNumberLength)) {
                    switch (currentNumberLength) {
                        case 1:
                            currentNumberLength = input.getByte() & 0xff;
                            state = ScannerTypeState.STRING_DATA;
                            break;
                        case 2:
                            currentNumberLength = ((input.getByte() & 0xff) << 8) | (input.getByte() & 0xff);
                            state = ScannerTypeState.STRING_DATA;
                            break;
                        case 4:
                            // TODO: implement large strings
                            state = ScannerTypeState.TYPE;
                            break;
                        case 8:
                            // TODO: implement very strings
                            state = ScannerTypeState.TYPE;
                            break;
                    }
                } else break;
            } else if(state == ScannerTypeState.STRING_DATA) {
                if(input.hasBytes(currentNumberLength)) {
                    onString(new String(input.getBytes(currentNumberLength),Charset.forName("UTF-8")));
                    state = ScannerTypeState.TYPE;
                } else break;
            } else if(state == ScannerTypeState.ARRAY) {
                if(input.hasBytes(currentNumberLength)) {
                    switch (currentNumberLength) {
                        case 1:
                            onArray(input.getByte() & 0xff);
                            state = ScannerTypeState.TYPE;
                            break;
                        case 2:
                            onArray(((input.getByte() & 0xff) << 8) | (input.getByte() & 0xff));
                            state = ScannerTypeState.TYPE;
                            break;
                        case 4:
                            onArray(123); // TODO: implement it
                            state = ScannerTypeState.TYPE;
                            break;
                        case 8:
                            onArray(123); // TODO: implement it
                            state = ScannerTypeState.TYPE;
                            break;
                    }
                } else break;
            } else if(state == ScannerTypeState.MAP) {
                if(input.hasBytes(currentNumberLength)) {
                    switch (currentNumberLength) {
                        case 1:
                            onMap(input.getByte() & 0xff);
                            state = ScannerTypeState.TYPE;
                            break;
                        case 2:
                            onMap(((input.getByte() & 0xff) << 8) | (input.getByte() & 0xff));
                            state = ScannerTypeState.TYPE;
                            break;
                        case 4:
                            onMap(123); // TODO: implement large maps
                            state = ScannerTypeState.TYPE;
                            break;
                        case 8:
                            onMap(123); // TODO: implement very large maps
                            state = ScannerTypeState.TYPE;
                            break;
                    }
                } else break;
            } else if(state == ScannerTypeState.TAG) {
                if(input.hasBytes(currentNumberLength)) {
                    switch (currentNumberLength) {
                        case 1:
                            onTag(input.getByte() & 0xff);
                            state = ScannerTypeState.TYPE;
                            break;
                        case 2:
                            onTag(((input.getByte() & 0xff) << 8) | (input.getByte() & 0xff));
                            state = ScannerTypeState.TYPE;
                            break;
                        case 4:
                            onTag(123); // TODO: implement large tags
                            state = ScannerTypeState.TYPE;
                            break;
                        case 8:
                            onTag(123); // TODO: implement very large tags
                            state = ScannerTypeState.TYPE;
                            break;
                    }
                } else break;
            } else if(state == ScannerTypeState.SPECIAL) {
                if(input.hasBytes(currentNumberLength)) {
                    switch (currentNumberLength) {
                        case 1:
                            onSpecial(input.getByte() & 0xff);
                            state = ScannerTypeState.TYPE;
                            break;
                        case 2:
                            onSpecial(((input.getByte() & 0xff) << 8) | (input.getByte() & 0xff));
                            state = ScannerTypeState.TYPE;
                            break;
                        case 4:
                            onSpecial(123); // TODO: implement large maps
                            state = ScannerTypeState.TYPE;
                            break;
                        case 8:
                            onSpecial(123); // TODO: implement very large maps
                            state = ScannerTypeState.TYPE;
                            break;
                    }
                } else break;
            }
        }
    }



    private void parsePositiveInt32() throws IOException {
        final int value = ((input.getByte() & 0xff) << 24) | ((input.getByte() & 0xff) << 16) | ((input.getByte() & 0xff) << 8) | (input.getByte() & 0xff);
        if(value < 0) {
            onLong(value & 0xffffffffl);
        } else {
            onInteger(value);
        }
    }

    private void parseNegativeInt32() throws IOException {
        final int value = ((input.getByte() & 0xff) << 24) | ((input.getByte() & 0xff) << 16) | ((input.getByte() & 0xff) << 8) | (input.getByte() & 0xff);
        if(value < 0) {
            onLong(-(value & 0xffffffffl));
        } else {
            onInteger(-value);
        }
    }

    private void onLong(long l) {
        System.out.println("long: " + l);
    }

    private void onSpecial(int minorType) {
        System.out.println("special: " + minorType);
    }

    private void onTag(int minorType) {
        System.out.println("tag: " + minorType);
    }

    private void onMap(int minorType) {
        System.out.println("map: " + minorType);
    }

    private void onBytes(byte[] bytes) {
        System.out.println("bytes...");
    }

    private void onString(String s) {
        System.out.println("string: " + s);
    }

    public void onInteger(int value) {
        System.out.println("integer: " + value);
    }

    public void onArray(int length) {
        System.out.println("array: " + length);
    }
}
