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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CborMap extends CborObject {
    private HashMap<CborObject, CborObject> value;

    public CborMap(HashMap<CborObject, CborObject> value) {
        this.value = value;
    }

    public CborMap() {
        this.value = new HashMap<CborObject, CborObject>();
    }

    public Map<CborObject, CborObject> getMap() {
        return value;
    }

    @Override
    public void write(CborWriter writer) throws IOException {
        super.write(writer);

        writer.writeMap(value.size());
        for(Map.Entry<CborObject, CborObject> entry : value.entrySet()) {
            entry.getKey().write(writer);
            entry.getValue().write(writer);
        }
    }

    // type overrides

    @Override
    public boolean isMap() {
        return true;
    }

    @Override
    public CborMap asMap() {
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("{");

        final Iterator<Map.Entry<CborObject, CborObject>> iterator = value.entrySet().iterator();
        while(iterator.hasNext()) {
            final Map.Entry<CborObject, CborObject> entry = iterator.next();
            builder.append(entry.getKey());
            builder.append(": ");
            builder.append(entry.getValue());

            if(iterator.hasNext()) {
                builder.append(", ");
            }
        }
        builder.append("}");

        return toStringWithTag(builder.toString());
    }

    @Override
    public Object toObject() {
        final HashMap object = new HashMap();

        for(Map.Entry<CborObject, CborObject> entry : value.entrySet()) {
            object.put(entry.getKey().toObject(), entry.getValue().toObject());
        }

        return object;
    }
}
