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

package com.naphaso.cbor.type.number;

import com.naphaso.cbor.CborWriter;
import com.naphaso.cbor.type.CborNumber;

import java.io.IOException;
import java.math.BigInteger;

public class CborShort extends CborNumber {
    private final int value;

    public CborShort(int value) {
        this.value = value;
    }

    @Override
    public void write(CborWriter writer) throws IOException {
        super.write(writer);

        writer.writeInt(value);
    }

    @Override
    public byte byteValue() {
        if(value >= Byte.MIN_VALUE && value <= Byte.MAX_VALUE) {
            return (byte) value;
        } else {
            throw new NumberFormatException("Value is out of range : " + value);
        }
    }

    @Override
    public short shortValue() {
        if(value >= Short.MIN_VALUE && value <= Short.MAX_VALUE) {
            return (short) value;
        } else {
            throw new NumberFormatException("Value is out of range : " + value);
        }
    }

    @Override
    public int intValue() {
        return value;
    }

    @Override
    public long longValue() {
        return value;
    }

    @Override
    public float floatValue() {
        return value;
    }

    @Override
    public double doubleValue() {
        return value;
    }

    @Override
    public BigInteger bigIntegerValue() {
        return BigInteger.valueOf(value);
    }

    @Override
    public boolean equals(int value) {
        return this.value == value;
    }

    @Override
    public String toString() {
        return toStringWithTag(Integer.toString(value));
    }

    @Override
    public Object toObject() {
        return value;
    }
}
