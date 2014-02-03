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

package com.naphaso.cbor.io;

import java.io.IOException;
import java.io.InputStream;

public class StreamInput implements Input {
    private InputStream in;

    public StreamInput(InputStream in) {
        this.in = in;
    }


    @Override
    public boolean hasBytes(int len) throws IOException {
        return in.available() >= len;
    }

    @Override
    public int getByte() throws IOException {
        return in.read();
    }

    @Override
    public byte[] getBytes(int size) throws IOException {
        byte[] data = new byte[size];
        in.read(data);
        return data;
    }

    @Override
    public void setListener(Runnable listener) {

    }
}
