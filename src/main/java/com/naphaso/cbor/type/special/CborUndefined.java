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

package com.naphaso.cbor.type.special;

import com.naphaso.cbor.CborWriter;
import com.naphaso.cbor.type.CborSpecial;

import java.io.IOException;

/**
 * Created by wolong on 03/02/14.
 */
public class CborUndefined extends CborSpecial {
    @Override
    public boolean isUndefined() {
        return true;
    }

    @Override
    public void write(CborWriter writer) throws IOException {
        super.write(writer);

        writer.writeSpecial(23);
    }

    @Override
    public String toString() {
        return toStringWithTag("undefined");
    }
}
