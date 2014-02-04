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

public class CborArray extends CborObject {
    private CborObject[] value;

    public CborArray(CborObject[] value) {
        this.value = value;
    }

    public CborObject[] getValue() {
        return value;
    }

    public void set(int index, CborObject val) {
        value[index] = val;
    }

    public CborObject get(int index) {
        return value[index];
    }

    @Override
    public void write(CborWriter writer) throws IOException {
        super.write(writer);

        writer.writeArray(value.length);
        for(int i = 0; i < value.length; i++) {
            value[i].write(writer);
        }
    }

    // type overrides

    @Override
    public boolean isArray() {
        return true;
    }

    @Override
    public CborArray asArray() {
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("[");

        for(int i = 0; i < value.length; i++) {
            builder.append(value[i]);
            if(i != value.length - 1) {
                builder.append(", ");
            }
        }

        builder.append("]");

        return toStringWithTag(builder.toString());
    }

    @Override
    public Object toObject() {
        return value;
    }
}
