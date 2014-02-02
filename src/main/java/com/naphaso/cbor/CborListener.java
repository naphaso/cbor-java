package com.naphaso.cbor;

import com.naphaso.cbor.type.CborNumber;

/**
 * Created by wolong on 02/02/14.
 */
public interface CborListener {
    void onNumber(CborNumber number);
    void onBytes(byte[] bytes);
    void onString(String s);
    void onArray(CborNumber size);
    void onMap(CborNumber size);
    void onTag(CborNumber tag);
    void onSpecial(CborNumber code);
}
