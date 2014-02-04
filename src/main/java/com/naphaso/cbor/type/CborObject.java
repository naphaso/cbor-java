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
import com.naphaso.cbor.exception.CborException;

import java.io.IOException;

public abstract class CborObject {
    private CborNumber tag;

    public void setTag(CborNumber tag) {
        this.tag = tag;
    }


    public void write(CborWriter writer) throws IOException {
        if(tag != null) {
            writer.writeTag(tag.intValue()); // TODO: fix for more big tags
        }
    }

    public Object toObject() {
        throw new CborException("unknown object conversion");
    }

    public CborNumber getTag() {
        return tag;
    }

    public boolean isString() {
        return false;
    }

    public CborString asString() {
        throw new CborException("is not a string");
    }

    public boolean isBytes() {
        return false;
    }

    public CborBytes asBytes() {
        throw new CborException("is not a bytes");
    }

    public boolean isNumber() {
        return false;
    }

    public CborNumber asNumber() {
        throw new CborException("is not a number");
    }

    public boolean isMap() {
        return false;
    }

    public CborMap asMap() {
        throw new CborException("is not a map");
    }

    public boolean isArray() {
        return false;
    }

    public CborArray asArray() {
        throw new CborException("is not a array");
    }

    public boolean isSpecial() {
        return false;
    }

    public CborSpecial asSpecial() {
        throw new CborException("is not a special");
    }

    protected String toStringWithTag(String value) {
        if(tag == null) {
            return value;
        } else {
            return tag.toString() + "#" + value;
        }
    }
}
