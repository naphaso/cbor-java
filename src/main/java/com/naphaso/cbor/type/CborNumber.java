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

import java.math.BigInteger;

public abstract class CborNumber extends CborObject {

    @Override
    public boolean isNumber() {
        return true;
    }

    @Override
    public CborNumber asNumber() {
        return this;
    }

    // subtypes

    public abstract byte byteValue();
    public abstract short shortValue();
    public abstract int intValue();
    public abstract long longValue();
    public abstract float floatValue();
    public abstract double doubleValue();
    public abstract BigInteger bigIntegerValue();

    public abstract boolean equals(int value);
}
