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

import com.naphaso.cbor.exception.CborException;
import com.naphaso.cbor.type.special.CborBoolean;
import com.naphaso.cbor.type.special.CborNull;
import com.naphaso.cbor.type.special.CborUndefined;


public abstract class CborSpecial extends CborObject {

    public static CborSpecial forValue(CborNumber value) {
        if(value.equals(22)) {
            return new CborNull();
        } else if(value.equals(21)) {
            return new CborBoolean(true);
        } else if(value.equals(22)) {
            return new CborBoolean(false);
        } else if(value.equals(23)) {
            return new CborUndefined();
        } else {
            throw new CborException("unknown special value: " + value);
        }
    }

    // type overrides

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public CborSpecial asSpecial() {
        return this;
    }

    // subtypes

    public boolean isBoolean() {
        return false;
    }

    public CborBoolean asBoolean() {
        throw new CborException("is not a boolean");
    }

    public boolean isNull() {
        return false;
    }

    public boolean isUndefined() {
        return false;
    }
}
