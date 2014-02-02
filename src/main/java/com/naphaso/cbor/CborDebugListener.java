package com.naphaso.cbor;

import com.naphaso.cbor.type.CborNumber;

/**
 * Created by wolong on 02/02/14.
 */
public class CborDebugListener implements CborListener {
    @Override
    public void onNumber(CborNumber number) {
        System.out.println("number: " + number);
    }

    @Override
    public void onBytes(byte[] bytes) {
        System.out.println("bytes len " + bytes.length);
    }

    @Override
    public void onString(String s) {
        System.out.println("string: " + s);
    }

    @Override
    public void onArray(CborNumber size) {
        System.out.println("array: " + size);
    }

    @Override
    public void onMap(CborNumber size) {
        System.out.println("map: " + size);
    }

    @Override
    public void onTag(CborNumber tag) {
        System.out.println("tag: " + tag);
    }

    @Override
    public void onSpecial(CborNumber code) {
        System.out.println("special: " + code);
    }
}
