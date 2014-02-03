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

import com.naphaso.cbor.io.Input;
import com.naphaso.cbor.io.Output;
import com.naphaso.cbor.io.StreamInput;
import com.naphaso.cbor.io.StreamOutput;
import com.naphaso.cbor.type.CborMap;
import com.naphaso.cbor.type.CborObject;
import com.naphaso.cbor.type.CborString;
import com.naphaso.cbor.type.number.CborShort;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class App 
{
    public void testWrite() throws IOException {
        FileOutputStream fos = new FileOutputStream("test.cbor");
        Output output = new StreamOutput(fos);
        CborWriter writer = new CborWriter(output);

        writer.writeMap(2);

        // key 1
        writer.writeString("hello");
        // value 1
        writer.writeInt(new BigInteger("5126378562348346534857632482374652582763453486542735654325645832478457363787456436263574"));

        // key 2
        writer.writeString("world");
        // value 2
        writer.writeArray(1);
        writer.writeString("hihihi");

        output.close();
    }

    public void testWrite2() throws IOException {
        FileOutputStream fos = new FileOutputStream("test.cbor");
        Output output = new StreamOutput(fos);
        CborWriter writer = new CborWriter(output);

        Map<String, String> someMap = new HashMap<String, String>();
        someMap.put("hello", "world");
        someMap.put("one", "two");
        someMap.put("test", "catch");

        writer.write(someMap);

        output.close();
    }

    public void testRead() throws IOException {
        FileInputStream fis = new FileInputStream("test.cbor");
        Input input = new StreamInput(fis);
        CborReader reader = new CborReader(input);
        CborDebugListener listener = new CborDebugListener();

        reader.setListener(listener);

        reader.run();
    }

    public void test2() throws IOException {
        CborObject a = new CborShort(123);
        CborObject b = new CborString("hello");

        HashMap<CborObject, CborObject> m = new HashMap<CborObject, CborObject>();
        m.put(b, a);

        CborMap map = new CborMap(m);
        map.setTag(new CborShort(123));

        System.out.println(map);

        FileOutputStream fos = new FileOutputStream("test.cbor");
        Output output = new StreamOutput(fos);
        CborWriter writer = new CborWriter(output);
        map.write(writer);
        output.close();


        // read
        FileInputStream fis = new FileInputStream("test.cbor");
        Input input = new StreamInput(fis);
        CborReader reader = new CborReader(input);
        CborDebugListener listener = new CborDebugListener();
        reader.setListener(listener);
        reader.run();

        // parse
        fis.close();
        fis = new FileInputStream("test.cbor");
        input = new StreamInput(fis);
        CborParser parser = new CborParser();
        parser.parse(input);
    }


    public static void main( String[] args ) throws IOException {
        //new App().testWrite2();
        //new App().testRead();

        new App().test2();
    }
}
