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

public class CborBigInteger extends CborNumber {
    private final BigInteger value;

    public CborBigInteger(BigInteger value) {
        this.value = value;
    }

    @Override
    public void write(CborWriter writer) throws IOException {
        super.write(writer);

        writer.writeInt(value);
    }

    @Override
    public byte byteValue() {
        return value.byteValue();
    }

    @Override
    public short shortValue() {
        return value.shortValue();
    }

    @Override
    public int intValue() {
        return value.intValue();
    }

    @Override
    public long longValue() {
        return value.longValue();
    }

    @Override
    public float floatValue() {
        return value.floatValue();
    }

    @Override
    public double doubleValue() {
        return value.doubleValue();
    }

    @Override
    public BigInteger bigIntegerValue() {
        return value;
    }

    @Override
    public boolean equals(int value) {
        return this.value.equals(BigInteger.valueOf(value));
    }

    @Override
    public String toString() {
        return toStringWithTag(value.toString());
    }

    @Override
    public Object toObject() {
        return value;
    }
}
