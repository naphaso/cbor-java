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

package com.naphaso.cbor.type;

import com.naphaso.cbor.CborWriter;

import java.io.IOException;

public class CborBytes extends CborObject {
    private byte[] value;

    public CborBytes(byte[] value) {
        this.value = value;
    }

    public byte[] getValue() {
        return value;
    }

    @Override
    public void write(CborWriter writer) throws IOException {
        super.write(writer);

        writer.writeBytes(value);
    }

    // type overrides

    @Override
    public boolean isBytes() {
        return false;
    }

    @Override
    public CborBytes asBytes() {
        return this;
    }

    @Override
    public String toString() {
        return toStringWithTag(encodeHexString(value));
    }

    private static final char[] HEX_DIGITS =
            {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    public static String encodeHexString(byte[] data) {
        return encodeHexString(data, 0, data.length);
    }

    public static String encodeHexString(byte[] data, int offset, int length) {
        char[] out = new char[length << 1];
        for (int i = 0, j = 0; i < length; i++) {
            out[j++] = HEX_DIGITS[(0xF0 & data[offset + i]) >>> 4];
            out[j++] = HEX_DIGITS[0x0F & data[offset + i]];
        }
        return new String(out);
    }
}
