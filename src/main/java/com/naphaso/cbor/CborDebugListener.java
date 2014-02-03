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

package com.naphaso.cbor;

import com.naphaso.cbor.type.CborNumber;

public class CborDebugListener implements CborListener {
    @Override
    public void onNumber(CborNumber number) {
        System.out.println("number: " + number);
    }

    @Override
    public void onBytes(byte[] bytes) {
        System.out.println("bytes len " + bytes.length);
    }

    @Override
    public void onString(String s) {
        System.out.println("string: " + s);
    }

    @Override
    public void onArray(CborNumber size) {
        System.out.println("array: " + size);
    }

    @Override
    public void onMap(CborNumber size) {
        System.out.println("map: " + size);
    }

    @Override
    public void onTag(CborNumber tag) {
        System.out.println("tag: " + tag);
    }

    @Override
    public void onSpecial(CborNumber code) {
        System.out.println("special: " + code);
    }
}
